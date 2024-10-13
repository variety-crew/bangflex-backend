package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityLikeDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityLike;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.Member;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityLikeRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.MemberRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("communityLikeService")
public class CommunityLikeServiceImpl implements CommunityLikeService {

    private final ModelMapper modelMapper;
    private final CommunityLikeRepository communityLikeRepository;
    private final MemberRepository memberRepository;
    private final CommunityPostRepository communityPostRepository;

    @Autowired
    public CommunityLikeServiceImpl(ModelMapper modelMapper,
                                    CommunityLikeRepository communityLikeRepository,
                                    MemberRepository memberRepository,
                                    CommunityPostRepository communityPostRepository) {
        this.modelMapper = modelMapper;
        this.communityLikeRepository = communityLikeRepository;
        this.memberRepository = memberRepository;
        this.communityPostRepository = communityPostRepository;
    }

    @Transactional
    @Override
    public void addLike(CommunityLikeDTO newLike) {
        CommunityLike addedLike = modelMapper.map(newLike, CommunityLike.class);

        // 회원이 아니라면 예외 발생
        if (!memberRepository.existsById(newLike.getMemberCode())) {
            throw new InvalidUserException("좋아요 권한이 없습니다.");
        } else if (!communityPostRepository.existsById(newLike.getCommunityPostCode())) {
            throw new EntityNotFoundException("존재하지 않는 게시글입니다.");
        }

        // 이미 좋아요가 존재하는지 체크 후 존재하면 좋아요 취소(비활성화)
        if (communityLikeRepository.existsByMemberCodeAndCommunityPostCode(newLike.getMemberCode(),
                                                                            newLike.getCommunityPostCode())) {
            addedLike.setActive(false);
        } else {
            addedLike.setActive(true);
        }

        communityLikeRepository.save(addedLike);
    }
}
