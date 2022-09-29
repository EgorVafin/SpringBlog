package com.example.demo.dao;

import com.example.demo.model.PostComment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<PostComment, Integer> {
}
