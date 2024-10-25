package com.varc.bangflex.domain.eventPost.repository;

import com.varc.bangflex.domain.eventPost.entity.EventFile;
import com.varc.bangflex.domain.eventPost.entity.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eventFileRepository")
public interface EventFileRepository extends JpaRepository<EventFile, Integer> {

    List<EventFile> findByEventPost(EventPost foundEvent);
}
