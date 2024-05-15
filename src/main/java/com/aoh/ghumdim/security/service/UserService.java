package com.aoh.ghumdim.security.service;


import com.aoh.ghumdim.security.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(Integer userId);

    int getUserIdByEmail(String email);
}
