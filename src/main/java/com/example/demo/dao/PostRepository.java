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

    @Query("select p from Post p where p.title =:title")
    public Page<Post> search(@Param("title") String title, Pageable pageable);
}

//    SELECT p.id, p.is_active, p.title, (select count(*) from spring_blog.post_comments pc where pc.post_id = p.id) as commentCount FROM spring_blog.posts p where title = '123';

//    SELECT p.id, unix_timestamp(p.time), u.id AS u_id, u.name AS u_name,
//        p.title, p.text,  (select count(*) from spring_blog.post_comments pc
//        left JOIN  spring_blog.users u ON p.user_id = u.id
//        where pc.post_id = p.id) as commentCount FROM spring_blog.posts p where title = '123';
