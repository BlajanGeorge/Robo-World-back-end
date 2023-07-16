package com.roboworldbackend.db.repository;

import com.roboworldbackend.db.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for conversation entity operations
 *
 * @author Blajan George
 */
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    @Query(value = "SELECT * FROM conversation WHERE ((user1 = :userId1 AND user2 = :userId2) OR (user1 = :userId2 AND user2 = :userId1))", nativeQuery = true)
    Optional<Conversation> getConversation(@Param("userId1") Integer userId1, @Param("userId2") Integer userId2);

    @Query(value = "SELECT * FROM conversation WHERE (user1 = :userId OR user2 = :userId)", nativeQuery = true)
    List<Conversation> getUserConversations(@Param("userId") Integer userId);
}
