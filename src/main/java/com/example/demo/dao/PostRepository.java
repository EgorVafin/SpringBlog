package com.example.demo.dao;

import com.example.demo.model.Post;
import com.example.demo.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    public static final String BASE_QUERY = "SELECT p.id, unix_timestamp(p.time) AS postTimestamp, p.title, p.user_id AS userId, p.text AS text, " +
            "p.view_count AS viewCount, " +
            "u.name AS userName," +
            "(SELECT count(*) FROM post_comments pc WHERE pc.post_id = p.id) AS commentsCount, "
            + "(SELECT count(*) FROM post_votes pvlike WHERE pvlike.post_id = p.id AND pvlike.value = 1) AS likesCount, " +
            "(SELECT count(*) FROM post_votes pvdislike WHERE pvdislike.post_id = p.id AND pvdislike.value = -1) AS dislikesCount " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id ";

    public static final String BASE_COUNT_QUERY = "SELECT count(*) FROM posts p JOIN users u ON p.user_id = u.id ";

    public static final String BASE_FILTERS = "p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time <= NOW() ";

    @Query(value = BASE_QUERY + " WHERE " + BASE_FILTERS + "ORDER BY :orderField :orderDirection", nativeQuery = true)
    public Page<PostProjection> allPosts(Pageable pageable, @Param("orderField") String orderField, @Param("orderDirection") String orderDirection);

    @Query(value = BASE_QUERY + " WHERE " + BASE_FILTERS + "AND p.text LIKE :query " +
            "ORDER BY p.time ASC", nativeQuery = true)
    public Page<PostProjection> postsSearch(Pageable pageable, @Param("query") String query);

    @Query(value = BASE_QUERY + " WHERE " + BASE_FILTERS + "AND date_format(p.time,'%Y-%m-%d') = :date " +
            "ORDER BY p.time ASC", nativeQuery = true)
    public Page<PostProjection> postsByDate(Pageable pageable, @Param("date") String date);

    @Query(value = BASE_QUERY + " WHERE " + BASE_FILTERS +
            "AND p.id IN (SELECT t2p.post_id FROM spring_blog.tag2post t2p " +
            "WHERE t2p.tag_id = (select t.id FROM spring_blog.tags t WHERE t.name = :tag)", nativeQuery = true)
    public Page<PostProjection> postsByTag(Pageable pageable, @Param("tag") String tag);

    @Query(value = "SELECT (SELECT count(*) FROM spring_blog.post_votes where post_id = :id AND `value` = 1) AS likeCount, " +
            "(SELECT count(*) FROM spring_blog.post_votes where post_id = :id AND `value` = -1) AS dislikeCount ; ", nativeQuery = true)
    public PostLikesDislikesCount postLikeDislikeCount(@Param("id") Integer id);

    @Query(value = "select count(*) from posts where moderation_status = 'NEW' AND moderator_id = :moderatorId", nativeQuery = true)
    public int findPostCountForModeration(@Param("moderatorId") int moderatorId);

    @Query(value = BASE_QUERY + "WHERE p.is_active = false AND p.user_id = :userId",
            countQuery = BASE_COUNT_QUERY + "WHERE p.is_active = false AND p.user_id = :userId",
            nativeQuery = true)
    public Page<PostProjection> userInactivePosts(Pageable pageable, @Param("userId") int userId);

    @Query(value = BASE_QUERY + "WHERE p.is_active = true AND p.user_id = :userId AND p.moderation_status = :#{#moderationStatus.name()}",
            countQuery = BASE_COUNT_QUERY + "WHERE p.is_active = true AND p.user_id = :userId AND p.moderation_status = :#{#moderationStatus.name()}",
            nativeQuery = true)
    public Page<PostProjection> userActivePostsWithStatus(Pageable pageable,
                                                          @Param("userId") int userId,
                                                          @Param("moderationStatus") Status moderationStatus);

    @Query(value = "FROM Post p join fetch p.comments")
    public List<Post> allPostWithComments();


}

