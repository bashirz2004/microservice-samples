package com.zamani.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "CORE_USER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Setter
@Getter
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CORE_USER_AUTHORITY",
            joinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns =
            @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
    )
    private Set<MyAuthority> authorities;

    @Transient
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
