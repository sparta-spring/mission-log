package com.sparta.missionreport.domain.user.service;

import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.enums.UserRole;
import com.sparta.missionreport.domain.user.exception.UserCustomException;
import com.sparta.missionreport.domain.user.exception.UserExceptionCode;
import com.sparta.missionreport.domain.user.repository.UserRepository;
import com.sparta.missionreport.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserDto.UserResponse signup(UserDto.SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserCustomException(UserExceptionCode.CONFLICT_USER_EMAIL_IN_USE);
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isDeleted(false)
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return UserDto.UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public void logout(HttpServletRequest request) {

        jwtUtil.deleteRefreshToken(request);

    }

    public void delete(HttpServletRequest request, User user) {

        jwtUtil.deleteRefreshToken(request);

        user.updateIsDeleted();
        userRepository.save(user);

    }

    @Transactional
    public UserDto.UserResponse updatePassword(UserDto.UpdateUserPasswordRequest request,
            User user) {

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_NOT_MATCH_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return UserDto.UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Transactional
    public UserDto.UserResponse updateName(UserDto.UpdateUserNameRequest request, User user) {

        user.updateName(request);
        userRepository.save(user);

        return UserDto.UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public UserDto.UserResponse getUserInfo(User user) {

        return UserDto.UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(()
                -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER));
    }

}
