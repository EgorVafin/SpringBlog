package com.example.demo.dao;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT date_format(p.time,'%Y-%m-%d') AS date, count(*) AS count FROM spring_blog.posts p " +
            "where p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time <= NOW() AND YEAR(p.time) = :year " +
            "group by date_format(p.time,'%Y-%m-%d') order by p.time", nativeQuery = true)
    public List<CalendarProjection> calendar(@Param("year") int year);


    @Query(value = "SELECT DISTINCT YEAR(p.time) AS years FROM Post AS p " +
            "            where p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= current_timestamp() " +
            "            order by YEAR(p.time)")
    public List<Integer> calendarAllYears();
}
