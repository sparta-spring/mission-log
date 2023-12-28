package com.sparta.missionreport.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class SignupRequestDto {

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        private String email;

        @NotBlank
        private String name;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]{8,15}$")
        private String password;

    }

    @Getter
    public static class LoginRequestDto {

        private String email;

        private String password;

    }

    @Getter
    public static class UpdatePasswordDto {

        private String oldPassword;

        @Pattern(regexp = "^[a-zA-Z0-9+-_.]{8,15}$")
        private String newPassword;

    }
}
