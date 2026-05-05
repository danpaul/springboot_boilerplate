package com.example.demo.enums;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER;

    @Override
    public @Nullable String getAuthority() {
        return name();
    }
}
