package com.roboworldbackend.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bot entity
 *
 * @author Blajan George
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bot")
public class Bot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private boolean selected;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public Bot(String name, boolean selected, User user) {
        this.name = name;
        this.selected = selected;
        this.user = user;
    }
}
