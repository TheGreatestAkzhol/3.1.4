package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;
    @Column(name="role")
    private String role;
    public Role() {
    }

    public Role(int role_id) {
        this.role_id = role_id;
    }

    public Role(String role) {
        this.role = role;
    }

    public Role(int role_id, String role) {
        this.role_id = role_id;
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return getRole();
    }
}
