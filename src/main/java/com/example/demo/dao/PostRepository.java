package com.example.demo.dao;

import com.example.demo.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT p.id, unix_timestamp(p.time) AS postTimestamp, p.title, p.user_id AS userId, p.text AS text, " +
            "p.view_count AS viewCount, " +
            "u.name AS userName," +
            "(SELECT count(*) FROM post_comments pc WHERE pc.post_id = p.id) AS commentsCount, "
            + "(SELECT count(*) FROM post_votes pvlike WHERE pvlike.post_id = p.id AND pvlike.value = 1) AS likesCount, " +
            "(SELECT count(*) FROM post_votes pvdislike WHERE pvdislike.post_id = p.id AND pvdislike.value = -1) AS dislikesCount " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time <= NOW() " +
            "ORDER BY :orderField :orderDirection", nativeQuery = true)
    public Page<PostProjection> allPosts(Pageable pageable, @Param("orderField") String orderField, @Param("orderDirection") String orderDirection);

    @Query(value = "SELECT p.id, unix_timestamp(p.time) AS postTimestamp, p.title, p.user_id AS userId, p.text AS text, " +
            "p.view_count AS viewCount, " +
            "u.name AS userName," +
            "(SELECT count(*) FROM post_comments pc WHERE pc.post_id = p.id) AS commentsCount, "
            + "(SELECT count(*) FROM post_votes pvlike WHERE pvlike.post_id = p.id AND pvlike.value = 1) AS likesCount, " +
            "(SELECT count(*) FROM post_votes pvdislike WHERE pvdislike.post_id = p.id AND pvdislike.value = -1) AS dislikesCount " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time <= NOW() AND p.text LIKE :query " +
            "ORDER BY p.time ASC", nativeQuery = true)
    public Page<PostProjection> postsSearch(Pageable pageable, @Param("query") String query);

}

