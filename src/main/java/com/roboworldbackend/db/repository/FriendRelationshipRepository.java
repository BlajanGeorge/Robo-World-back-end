package com.roboworldbackend.db.repository;

import com.roboworldbackend.db.model.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for friend relationship entity operations
 *
 * @author Blajan George
 */
@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Integer> {
    @Query(value = "SELECT * FROM friend_relationship WHERE first_friend_id = :friendId OR other_friend_id = :friendId", nativeQuery = true)
    List<FriendRelationship> getFriendRelationshipByFirstFriendIdOrOtherFriendId(@Param("friendId") Integer friendId);

    @Modifying
    @Query(value = "DELETE FROM friend_relationship WHERE first_friend_id = :friendId OR other_friend_id = :friendId ", nativeQuery = true)
    void removeFriend(@Param("friendId") Integer friendId);

    @Query(value = "SELECT COUNT(id) FROM friend_relationship WHERE first_friend_id = :userId OR other_friend_id = :userId", nativeQuery = true)
    Integer getNumberOfFriends(@Param("userId") Integer userId);
}
