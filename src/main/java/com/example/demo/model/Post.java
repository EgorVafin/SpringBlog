package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "enum('NEW','ACCEPTED','DECLINED')", nullable = false)
    private Status moderationStatus;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = true)
    private User moderator;

    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
