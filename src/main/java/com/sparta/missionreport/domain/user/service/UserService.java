package com.sparta.missionreport.domain.user.service;

import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.exception.UserExceptionCode;
import com.sparta.missionreport.domain.user.repository.UserRepository;
import com.sparta.missionreport.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserDto.SignupRequestDto requestDto) throws Exception {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            UserExceptionCode e = UserExceptionCode.CONFLICT_USER_EMAIL_IN_USE;
            throw new CustomException(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        userRepository.save(user);
    }

}
