package com.example.demo.dao;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    @Query(value = "SELECT t.name, " +
            "       (SELECT count(*) FROM spring_blog.posts p WHERE p.moderation_status = 'ACCEPTED' " +
            "                                                 AND p.time <= NOW()) / " +
            "       (SELECT count(*) FROM spring_blog.tag2post t2p WHERE t2p.tag_id = t.id) AS weight " +
            "FROM spring_blog.tags t " +
            "WHERE t.name LIKE :searchPhrase", nativeQuery = true)
    public List<TagProjection> searchTags(@Param("searchPhrase") String searchPhrase);


    @Query(value = "select * from tags t where t.name LIKE :name", nativeQuery = true)
    public List<TagProjection> findByName(@Param("name") String name);

    public List<Tag> findByNameIn(List<String> names);
}
