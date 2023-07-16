package com.roboworldbackend.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Friend relationship entity
 *
 * @author Blajan George
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_relationship")
public class FriendRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private Integer firstFriendId;
    @Column(nullable = false)
    private Integer otherFriendId;

    public FriendRelationship(Integer firstFriendId, Integer otherFriendId) {
        this.firstFriendId = firstFriendId;
        this.otherFriendId = otherFriendId;
    }
}
