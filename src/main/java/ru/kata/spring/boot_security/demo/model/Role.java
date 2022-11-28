package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;
    @Column(name="role")
    private String role;
    public Role() {
    }

    public Role(int role_id) {
        this.role_id = role_id;
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
