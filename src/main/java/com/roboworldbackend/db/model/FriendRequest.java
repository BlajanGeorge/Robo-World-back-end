package com.roboworldbackend.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Friend request entity
 *
 * @author Blajan George
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer requesterId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public FriendRequest(Integer requesterId, User user) {
        this.requesterId = requesterId;
        this.user = user;
    }
}
