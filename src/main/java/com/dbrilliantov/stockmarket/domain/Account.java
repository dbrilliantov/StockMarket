package com.dbrilliantov.stockmarket.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 4, max = 32, message = "Login must be 4 - 32 characters long")
    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Size(min = 4, max = 64, message = "Password must be 4 - 64 characters long")
    @Column(nullable = false, length = 64)
    private String password;

    @Transient
    @Size(min = 4, max = 64, message = "Password must be 4 - 64 characters long")
    private String confirm;

    @Column(nullable = false)
    private float balance = 10000;

    @Transient
    private final Collection<GrantedAuthority> authorities = new HashSet<>();

    public Account() {
        authorities.add(new SimpleGrantedAuthority("USER"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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