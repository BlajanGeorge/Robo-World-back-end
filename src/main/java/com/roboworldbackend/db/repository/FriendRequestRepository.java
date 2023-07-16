package com.roboworldbackend.db.repository;

import com.roboworldbackend.db.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for friend request entity operations
 *
 * @author Blajan George
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
}
