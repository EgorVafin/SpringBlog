package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.type.ByteType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "post_votes")
@Getter
@Setter
@NoArgsConstructor
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "time",nullable = false)
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "value", nullable = false)
    @Type(type = "org.hibernate.type.ByteType")
    private short value;
}
