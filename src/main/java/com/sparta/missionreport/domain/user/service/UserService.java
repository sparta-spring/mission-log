package com.sparta.missionreport.domain.user.service;

import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.enums.UserRole;
import com.sparta.missionreport.domain.user.exception.UserCustomException;
import com.sparta.missionreport.domain.user.exception.UserExceptionCode;
import com.sparta.missionreport.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserDto.SignupRequestDto requestDto) throws Exception {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserCustomException(UserExceptionCode.CONFLICT_USER_EMAIL_IN_USE);
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(UserDto.UpdatePasswordRequestDto requestDto, User user)
            throws Exception {

        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_NOT_MATCH_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void updateName(UserDto.UpdateNameRequestDto requestDto, User user)
            throws Exception {

        user.setName(requestDto.getName());

        userRepository.save(user);
    }

    public UserDto.GetUserInfoResponseDto getUserInfo(User user)
            throws Exception {

        return UserDto.GetUserInfoResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER));
    }

}
