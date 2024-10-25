package com.varc.bangflex.domain.eventPost.repository;

import com.varc.bangflex.domain.eventPost.entity.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eventPostRepository")
public interface EventPostRepository extends JpaRepository<EventPost, Integer> {

//    @Query("SELECT p FROM EventPost p JOIN FETCH p.theme " +
//            "WHERE p.active = true AND p.category = :category ORDER BY p.createdAt DESC")
    List<EventPost> findTop5ByActiveTrueAndCategoryEqualsOrderByCreatedAtDesc(String category);
}
