package com.catalog.movies.config;

import com.catalog.movies.core.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class UserDetailsImpl extends User implements UserDetails {

    private ArrayList<String> roles;
    private User user;

    public User getUser() {
        return this.user;
    }

    public UserDetailsImpl(String email, String fullName, String password, ArrayList<String> roles, User user, String photo) {
        super(email, fullName, password, photo);
        this.roles = roles;
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRoles = org.springframework.util.StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
