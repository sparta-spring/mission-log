package com.sparta.missionreport.domain.user.controller;

import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.service.UserService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(
            @Valid @RequestBody UserDto.SignupRequestDto requestDto) throws Exception {

        userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(HttpStatus.CREATED.value(), "회원가입 성공", null));
    }

    @PatchMapping("/password")
    public ResponseEntity<CommonResponseDto> updatePassword(
            @Valid @RequestBody UserDto.UpdatePasswordDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        userService.updatePassword(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "비밀번호 수정 성공", null));
    }

}
