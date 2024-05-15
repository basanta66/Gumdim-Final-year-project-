package com.aoh.ghumdim.security.dto;

import com.aoh.ghumdim.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private int age ;
    private String location;


}
