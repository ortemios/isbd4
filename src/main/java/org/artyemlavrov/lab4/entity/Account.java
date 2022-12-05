package org.artyemlavrov.lab4.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
@javax.persistence.Entity
@Table(name="Account")
public class Account implements UserDetails {

    @GeneratedValue
    @Id
    private int id;

    @Column(name = "email",nullable = false, unique = true)
    @NotNull(message = "Имя пользователя не может быть пустым")
    private String username;

    @Size(min = 8, message = "Пароль слишком короткий")
    @NotNull(message = "Пароль не может быть пустым")
    private String password;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
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
