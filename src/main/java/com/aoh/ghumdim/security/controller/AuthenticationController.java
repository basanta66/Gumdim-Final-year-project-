//package com.aoh.ghumdim.security.controller;
//
//
//
//import com.aoh.ghumdim.security.dto.AuthenticationRequestDto;
//import com.aoh.ghumdim.security.dto.AuthenticationResponseDto;
//import com.aoh.ghumdim.security.dto.RegisterRequestDto;
//import com.aoh.ghumdim.security.service.AuthenticationService;
//import com.aoh.ghumdim.shared.UserResponse;
//import com.aoh.ghumdim.security.service.UserService;
//import com.aoh.ghumdim.security.dto.UserResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//
//    private final AuthenticationService service;
//    private final UserService userService;
//
//    @PostMapping("/register")
//    public UserResponse register(@RequestBody @Validated RegisterRequestDto request){
//        return service.register(request);
//    }
//
//    @PostMapping("/authenticate")
//    public AuthenticationResponseDto authenticate(
//            @RequestBody AuthenticationRequestDto request
//    ){
//        return service.authenticate(request);
//    }
//
//    @GetMapping("/user/{id}")
//    public UserResponseDto getUser(@PathVariable Integer id) {
//        UserResponseDto userResponseDto=userService.getUserById(id);
//        return userResponseDto;
//    }
//    @GetMapping("/getUserByEmail")
//    public int getUserByEmail(@RequestParam String email) {
//        int userId=userService.getUserIdByEmail(email);
//        return userId;
//    }
//
//}
