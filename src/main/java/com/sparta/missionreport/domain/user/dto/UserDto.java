package com.sparta.missionreport.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @Schema(description = "유저 회원가입 요청 dto")
    public static class SignupRequestDto {

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
    @Schema(description = "유저 로그인 요청 dto")
    public static class LoginRequestDto {

        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @Schema(description = "유저 비밀번호", defaultValue = "@qqqq1111")
        private String password;

    }

    @Getter
    @Schema(description = "유저 비밀번호 변경 요청 dto")
    public static class UpdatePasswordRequestDto {

        @Schema(description = "기존 비밀번호", defaultValue = "@qqqq1111")
        private String oldPassword;

        @Pattern(regexp = "^[a-zA-Z0-9+-_.]{8,15}$")
        @Schema(description = "변경할 새 비밀번호", defaultValue = "@qqqq1112")
        private String newPassword;

    }

    @Getter
    @Schema(description = "유저 이름 변경 요청 dto")
    public static class UpdateNameRequestDto {

        @Schema(description = "변경할 이름", defaultValue = "Sparta")
        private String name;

    }

    @Builder
    @Getter
    @Schema(description = "유저 정보 조회 반환 dto")
    public static class GetUserInfoResponseDto {

        @Schema(description = "유저 이메일", defaultValue = "sparta@gmail.com")
        private String email;

        @Schema(description = "유저 이름", defaultValue = "스파르타")
        private String name;

    }
}
