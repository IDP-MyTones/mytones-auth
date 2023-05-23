package com.mytones.auth.controller;

import com.mytones.auth.domain.User;
import com.mytones.auth.dto.UserDto;
import com.mytones.auth.repository.UserRepository;
import com.mytones.auth.security.SecurityUtils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostMapping
    public void signUp(@RequestBody final UserDto dto) {
        final var user = new User();
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        userRepository.save(user);
    }

    @GetMapping("/me")
    @Secured({"ADMIN", "CLIENT", "MODERATOR"})
    public UserDto me() throws LoginException {
        final var username = SecurityUtils.getCurrentUserLogin().orElseThrow(LoginException::new);
        final var user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);

        final var dto = new UserDto();
        dto.setRole(user.getRole());
        dto.setUsername(username);
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
