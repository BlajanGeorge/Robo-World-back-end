package com.roboworldbackend.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Message entity
 *
 * @author Blajan George
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer subject;
    private Integer destination;
    private String content;
    private Date timestamp;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Conversation conversation;

    public Message(Integer subject, Integer destination, String content, Date timestamp, Conversation conversation) {
        this.subject = subject;
        this.destination = destination;
        this.content = content;
        this.timestamp = timestamp;
        this.conversation = conversation;
    }
}
