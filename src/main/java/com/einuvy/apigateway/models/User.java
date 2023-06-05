package com.einuvy.apigateway.models;

import com.einuvy.apigateway.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
public class User {
    @Id @Getter
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100) @Setter @Getter
    private String username;

    @Column(nullable = false) @Setter @Getter
    private String password;

    @Column(nullable = false) @Setter @Getter
    private String name;

    @Column(nullable = false) @Setter @Getter
    private String lastName;

    @Column(nullable = false) @Setter @Getter
    private LocalDateTime creationDate;

    @Enumerated(STRING)
    @Column(nullable = false) @Setter @Getter
    private Role role;

    public User(String username, String password, String name, String lastName, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.creationDate = LocalDateTime.now();
        this.role = role;
    }
}
