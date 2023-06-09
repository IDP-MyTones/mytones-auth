package com.mytones.auth.security;

import com.mytones.auth.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.stream.Stream;

public class SecurityUtils {

    public static Optional<String> getCurrentUserLogin() {
        var securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof final UserDetails springSecurityUser) {
                        return springSecurityUser.getUsername();
                    }
                    if (authentication.getPrincipal() instanceof final String authenticationPrincipal) {
                        return authenticationPrincipal;
                    }
                    return null;
                });
    }

    public static boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static boolean isCurrentUserInRole(final String role) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities(authentication).anyMatch(role::equalsIgnoreCase);
    }

    public static String generateToken(User user) {
        var jwt = new JWT(new UserDetailsServiceImpl.UserDetailsImpl(user));
        return jwt.compact();
    }

    private static Stream<String> getAuthorities(final Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
}
