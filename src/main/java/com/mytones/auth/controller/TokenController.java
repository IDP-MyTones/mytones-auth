package com.mytones.auth.controller;

import com.mytones.auth.dto.LoginDto;
import com.mytones.auth.dto.TokenDto;
import com.mytones.auth.repository.UserRepository;
import com.mytones.auth.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class TokenController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) {
        var user = userRepository.findByUsername(loginDto.username()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Bad credentials");
        }

        return new TokenDto(SecurityUtils.generateToken(user), user.getRole().name());
    }
}
