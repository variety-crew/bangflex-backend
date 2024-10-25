package com.varc.bangflex.domain.user.repository;

import com.varc.bangflex.domain.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    boolean existsById(String id);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<Member> findByIdAndIsAdminTrue(String userId);

//    @Query("SELECT m FROM Member m WHERE m.id = :id")
//    boolean existsByUserId(@Param("id") String id);
}