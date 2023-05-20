package com.mytones.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytones.auth.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private User.Role role;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
