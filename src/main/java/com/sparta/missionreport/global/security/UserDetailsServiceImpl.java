package com.sparta.missionreport.global.security;

import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 사용자입니다.")
        );

        return new UserDetailsImpl(user);
    }
}
