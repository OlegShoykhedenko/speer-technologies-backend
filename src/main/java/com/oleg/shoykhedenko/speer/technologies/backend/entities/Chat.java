package com.oleg.shoykhedenko.speer.technologies.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "first_user_id", referencedColumnName = "id")
    private User firstUser;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "second_user_id", referencedColumnName = "id")
    private User secondUser;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    private User fromUser;

    @CreationTimestamp
    private Date timestamp;

    private String message;

}
