package com.oleg.shoykhedenko.speer.technologies.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_tweet")
    private String message;

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
