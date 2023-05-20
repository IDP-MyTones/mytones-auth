package com.mytones.auth.bootstrap;

import com.mytones.auth.domain.User;
import com.mytones.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class InitUsers implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            final var user = new User();
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUsername("admin");
            user.setLastName("admin");
            user.setFirstName("admin");
            user.setRole(User.Role.ADMIN);

            userRepository.save(user);
        }
    }
}
