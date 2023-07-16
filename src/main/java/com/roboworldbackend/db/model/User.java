package com.roboworldbackend.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * User entity
 *
 * @author Blajan George
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(320)")
    private String email;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String lastName;
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Bot> bots;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<FriendRequest> friendRequests;

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.bots = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }
}

