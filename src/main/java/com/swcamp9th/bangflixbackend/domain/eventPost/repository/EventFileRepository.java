package com.swcamp9th.bangflixbackend.domain.eventPost.repository;

import com.swcamp9th.bangflixbackend.domain.eventPost.entity.EventFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("eventFileRepository")
public interface EventFileRepository extends JpaRepository<EventFile, Integer> {
}
