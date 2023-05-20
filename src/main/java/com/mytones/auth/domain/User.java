package com.mytones.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AbstractPersistable<Long> {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated
    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public enum Role {
        CLIENT, ADMIN, MODERATOR;
    }
}
