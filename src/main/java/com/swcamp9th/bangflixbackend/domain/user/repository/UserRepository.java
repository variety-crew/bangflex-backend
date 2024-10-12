package com.swcamp9th.bangflixbackend.domain.user.repository;

import com.swcamp9th.bangflixbackend.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    boolean existsById(String id);
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}