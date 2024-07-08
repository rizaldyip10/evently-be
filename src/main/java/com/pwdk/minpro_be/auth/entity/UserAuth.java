package com.pwdk.minpro_be.auth.entity;

import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.users.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class UserAuth extends User implements UserDetails {

    private final User user;

    public UserAuth(User user){
        this.user = user;
    }

    public String getPassword(){
        return this.user.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Roles> roles = this.user.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
