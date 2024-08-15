package com.ecommerce.id.vn.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Data
public class CustomUserDetails implements UserDetails {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String landmark;
    private String city;
    private String state;
    private String roles;

    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public CustomUserDetails(int id, String email, String password, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] rolesArray = this.roles.split(",");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : rolesArray) {
            authorities.add(new SimpleGrantedAuthority(role.trim()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Trả về email thay vì username
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
