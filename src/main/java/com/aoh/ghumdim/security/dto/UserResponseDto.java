package com.aoh.ghumdim.security.dto;

import com.aoh.ghumdim.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String firstname;
    private String location;
    private String email;
    private Role role;
}
