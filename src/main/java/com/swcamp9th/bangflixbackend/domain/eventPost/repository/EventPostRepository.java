package com.swcamp9th.bangflixbackend.domain.eventPost.repository;

import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("eventPostRepository")
public interface EventPostRepository extends JpaRepository<EventPost, Integer> {
}
