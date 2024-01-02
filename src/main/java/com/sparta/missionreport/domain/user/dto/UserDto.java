package com.sparta.missionreport.domain.user.dto;

import com.sparta.missionreport.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @Builder
    @Schema(description = "유저 회원가입 요청 dto")
    public static class SignupRequest {

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @NotBlank
        @Schema(description = "유저 이름", defaultValue = "스파르타")
        private String name;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]{8,15}$")
        @Schema(description = "유저 비밀번호", defaultValue = "@qqqq1111")
        private String password;

    }

    @Getter
    @Builder
    @Schema(description = "유저 이메일 인증번호 전송 요청 dto")
    public static class SendEmailRequest {

        @Email
        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

    }

    @Getter
    @Builder
    @Schema(description = "유저 이메일 인증번호 인증 요청 dto")
    public static class AuthEmailRequest {

        @Email
        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @Schema(description = "이메일 인증번호", defaultValue = "324511")
        private String authCode;

    }

    @Getter
    @Builder
    @Schema(description = "유저 로그인 요청 dto")
    public static class LoginRequest {

        @Email
        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @Schema(description = "유저 비밀번호", defaultValue = "@qqqq1111")
        private String password;

    }

    @Getter
    @Builder
    @Schema(description = "유저 비밀번호 변경 요청 dto")
    public static class UpdateUserPasswordRequest {

        @Schema(description = "기존 비밀번호", defaultValue = "@qqqq1111")
        private String oldPassword;

        @Pattern(regexp = "^[a-zA-Z0-9+-_.]{8,15}$")
        @Schema(description = "변경할 새 비밀번호", defaultValue = "@qqqq1112")
        private String newPassword;

    }

    @Getter
    @Builder
    @Schema(description = "유저 이름 변경 요청 dto")
    public static class UpdateUserNameRequest {

        @Schema(description = "변경할 이름", defaultValue = "Sparta")
        private String name;

    }

    @Builder
    @Getter
    @Schema(description = "유저 정보 조회 응답 dto")
    public static class UserResponse {

        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @Schema(description = "유저 이름", defaultValue = "스파르타")
        private String name;

        public static UserResponse of(User user) {
            return UserResponse.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }

    }
}
