package com.aoh.ghumdim.security.service;

import com.aoh.ghumdim.security.entity.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceimpl implements JwtService {

    private static final String SECRET_KEY = "xxC6yc0IJIujgqxvOFT+bQaTDQ5OxgvwZuDdZdwjCHpDKXLby4ubwIdk7NfCMGis\n";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetail userDetail) {
        return generateToken(new HashMap<>(), userDetail);
    }


    public String generateToken(Map<String, Object> extraClaims, UserDetail userDetail) {
//        UserDetail userDetail;
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetail.getUsername())
                .claim("userId",userDetail.getUserIdForJwt())
                .claim("roles",userDetail.getRole())
                .claim("firstName",userDetail.getFirstNameForJwt())
//                .setClaim(userDetail.getRole())
//                .content("Role",userDetail.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
