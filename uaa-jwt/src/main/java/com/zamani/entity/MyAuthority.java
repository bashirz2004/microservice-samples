package com.zamani.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CORE_AUTHORITY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Setter
@Getter
public class MyAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private String title;

    @Override
    public String getAuthority() {
        return this.code;
    }
}
