package com.roboworldbackend.db.repository;

import com.roboworldbackend.db.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for bot entity operations
 *
 * @author Blajan George
 */
@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {
    List<Bot> getBotByUserId(Integer userId);

    @Modifying
    @Query(value = "UPDATE bot SET selected = false WHERE selected = true", nativeQuery = true)
    void updateCurrentSelectedBotToUnselected();

    @Modifying
    @Query(value = "UPDATE bot SET selected = true WHERE id = :botId", nativeQuery = true)
    void selectBot(@Param("botId") Integer botId);
}
