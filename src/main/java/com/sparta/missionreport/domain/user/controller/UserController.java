package com.sparta.missionreport.domain.user.controller;

import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.service.EmailAuthService;
import com.sparta.missionreport.domain.user.service.UserService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "User Controller")
public class UserController {

    private final UserService userService;
    private final EmailAuthService emailAuthService;

    @Operation(summary = "유저 회원가입 (no auth)")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(
            @Valid @RequestBody UserDto.SignupRequest request) {

        UserDto.UserResponse response = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseDto(HttpStatus.CREATED.value(), "회원가입 성공", response));
    }

    @Operation(summary = "유저 이메일 인증번호 전송 (no auth)")
    @PostMapping("/email/send")
    public ResponseEntity<CommonResponseDto> emailSend(
            @RequestBody UserDto.SendEmailRequest request)
            throws MessagingException {

        emailAuthService.sendEmail(request.getEmail());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "이메일 인증번호 전송 성공", null));
    }

    @Operation(summary = "유저 이메일 인증번호 확인 (no auth)")
    @PostMapping("/email/auth")
    public ResponseEntity<CommonResponseDto> emailAuth(
            @RequestBody UserDto.AuthEmailRequest request) {

        emailAuthService.checkAuthCode(request.getEmail(), request.getAuthCode());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "이메일 인증번호 인증 성공", null));
    }

    @Operation(summary = "유저 로그아웃")
    @PatchMapping("/logout")
    public ResponseEntity<CommonResponseDto> logout(HttpServletRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.logout(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "로그아웃 성공", null));
    }

    @Operation(summary = "유저 탈퇴")
    @PatchMapping("/delete")
    public ResponseEntity<CommonResponseDto> deleteUser(HttpServletRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.delete(request, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "회원 탈퇴 성공", null));
    }

    @Operation(summary = "유저 비밀번호 변경")
    @PatchMapping("/password")
    public ResponseEntity<CommonResponseDto> updatePassword(
            @Valid @RequestBody UserDto.UpdateUserPasswordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserDto.UserResponse response = userService.updatePassword(request,
                userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "비밀번호 수정 성공", response));
    }

    @Operation(summary = "유저 이름 변경")
    @PatchMapping("/name")
    public ResponseEntity<CommonResponseDto> updateName(
            @Valid @RequestBody UserDto.UpdateUserNameRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserDto.UserResponse response = userService.updateName(request,
                userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "이름 수정 성공", response));
    }

    @Operation(summary = "유저 정보 조회")
    @GetMapping("")
    public ResponseEntity<CommonResponseDto> getUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserDto.UserResponse response = userService.getUserInfo(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "유저 정보 조회 성공", response));
    }

}
