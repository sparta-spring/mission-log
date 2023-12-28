package com.sparta.missionreport.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.missionreport.domain.user.dto.UserDto;
import com.sparta.missionreport.domain.user.enums.UserRole;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            UserDto.LoginRequestDto requestDto = new ObjectMapper().readValue(
                    request.getInputStream(),
                    UserDto.LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String success = "로그인 되었습니다.";

        jwtUtil.saveRefreshToken(username, refreshToken);

        response.getWriter().write(success);

        jwtUtil.addJwtToHeader(accessToken, refreshToken, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("로그인 실패");
        response.setStatus(400);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String error = "회원을 찾을 수 없습니다.";

        response.getWriter().write(error);
        response.getWriter().flush();
    }
}