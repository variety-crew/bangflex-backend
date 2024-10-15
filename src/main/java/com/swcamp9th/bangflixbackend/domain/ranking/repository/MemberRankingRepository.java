package com.swcamp9th.bangflixbackend.domain.ranking.repository;

import com.swcamp9th.bangflixbackend.domain.ranking.entity.MemberRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRankingRepository extends JpaRepository<MemberRanking, Integer> {

}
