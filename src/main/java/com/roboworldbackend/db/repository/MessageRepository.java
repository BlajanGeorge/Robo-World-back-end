package com.roboworldbackend.db.repository;

import com.roboworldbackend.db.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for message entity operations
 *
 * @author Blajan George
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
