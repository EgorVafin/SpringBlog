package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


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
    @Column(name = "moderation_status", columnDefinition = "enum('NEW','ACCEPTED','DECLINED') NOT NULL")
    private Status moderationStatus;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = true)
    private User moderator;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", columnDefinition = "TEXT NOT NULL")
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @OneToMany(mappedBy = "post")
    private List<TagToPost> tags;

}
