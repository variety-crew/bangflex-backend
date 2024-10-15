package com.swcamp9th.bangflixbackend.domain.community.comment.service;

import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentRequestDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.entity.Comment;
import com.swcamp9th.bangflixbackend.domain.community.comment.repository.CommentRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityMember;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityMemberRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ModelMapper modelMapper,
                              CommunityMemberRepository communityMemberRepository,
                              CommunityPostRepository communityPostRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.communityMemberRepository = communityMemberRepository;
        this.communityPostRepository = communityPostRepository;
    }

    @Override
    public CommentDTO createComment(Integer communityPostCode, CommentRequestDTO newComment) {
        Comment comment = modelMapper.map(newComment, Comment.class);

        // 회원이 아니면 예외 발생
        CommunityMember author = communityMemberRepository.findById(newComment.getMemberCode())
                .orElseThrow(() -> new InvalidUserException("댓글 작성 권한이 없습니다."));

        CommunityPost post = communityPostRepository.findById(communityPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        comment.setActive(true);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setContent(newComment.getContent());
        comment.setCommunityMember(author);
        comment.setCommunityPost(post);

        // 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        CommentDTO commentResponse = modelMapper.map(savedComment, CommentDTO.class);
        commentResponse.setMemberCode(savedComment.getCommunityMember().getMemberCode());
        commentResponse.setCommunityPostCode(savedComment.getCommunityPost().getCommunityPostCode());

        return commentResponse;
    }
}
