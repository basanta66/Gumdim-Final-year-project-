package com.aoh.ghumdim.security.service.impl;


import com.aoh.ghumdim.security.entity.User;
import com.aoh.ghumdim.security.repo.UserRepository;
import com.aoh.ghumdim.shared.exception.UserNotFoundException;
import com.aoh.ghumdim.security.service.UserService;
import com.aoh.ghumdim.security.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto getUserById(Integer userId) {
        User user=userRepository.findById(userId).get();
        UserResponseDto userResponseDtoFromUser=modelMapper.map(user,UserResponseDto.class);
        return userResponseDtoFromUser;
    }

    @Override
    public int getUserIdByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()
                ->new UserNotFoundException("User not found"));
        return user.getId();
    }
}
