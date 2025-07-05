package com.bcl.fitmate.backend.config.security;

import com.bcl.fitmate.backend.entity.Role;
import com.bcl.fitmate.backend.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@ToString(exclude = "password")
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final Role role;
    private final String username;
    private final String password;
    private final String profileImageUrl;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.profileImageUrl = user.getProfileImage() != null
                // 경로 수정 예정
                ? user.getProfileImage().getFilePath()
                : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
