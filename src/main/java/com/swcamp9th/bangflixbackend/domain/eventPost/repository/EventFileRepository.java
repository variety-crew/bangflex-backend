package com.swcamp9th.bangflixbackend.domain.eventPost.repository;

import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventFile;
import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eventFileRepository")
public interface EventFileRepository extends JpaRepository<EventFile, Integer> {

    List<EventFile> findByEventPost(EventPost foundEvent);
}
