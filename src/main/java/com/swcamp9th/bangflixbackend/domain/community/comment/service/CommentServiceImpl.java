package com.swcamp9th.bangflixbackend.domain.community.comment.service;

import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentUpdateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.entity.Comment;
import com.swcamp9th.bangflixbackend.domain.community.comment.repository.CommentRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommunityPostRepository communityPostRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ModelMapper modelMapper,
                              UserRepository userRepository,
                              CommunityPostRepository communityPostRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.communityPostRepository = communityPostRepository;
    }

    @Transactional
    @Override
    public void createComment(String loginId, Integer communityPostCode, CommentCreateDTO newComment) {
        Comment comment = new Comment();

        // 회원이 아니면 예외 발생
        Member author = userRepository.findById(loginId)
                .orElseThrow(() -> new InvalidUserException("댓글 작성 권한이 없습니다."));

        CommunityPost post = communityPostRepository.findById(communityPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        comment.setActive(true);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setContent(newComment.getContent());
        comment.setMember(author);
        comment.setCommunityPost(post);

        // 댓글 저장
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void updateComment(String loginId, Integer communityPostCode,
                              Integer commentCode, CommentUpdateDTO modifiedComment) {

        Comment originalComment = commentRepository.findById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        CommunityPost post = communityPostRepository.findById(communityPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 회원이 아니라면 예외 발생
        Member author = userRepository.findById(loginId).orElseThrow(
                () -> new InvalidUserException("로그인이 필요합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!originalComment.getMember().getMemberCode().equals(author.getMemberCode())) {
            throw new InvalidUserException("댓글 수정 권한이 없습니다.");
        }

        originalComment.setContent(modifiedComment.getContent());
        originalComment.setCommunityPost(post);

        // 수정된 댓글 저장
        commentRepository.save(originalComment);
    }

    @Transactional
    @Override
    public void deleteComment(String loginId, Integer communityPostCode,
                              Integer commentCode, CommentDeleteDTO deletedComment) {

        Comment foundComment = commentRepository.findById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        // 회원이 아니라면 예외 발생
        Member author = userRepository.findById(loginId).orElseThrow(
                () -> new InvalidUserException("로그인이 필요합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!foundComment.getMember().getMemberCode().equals(author.getMemberCode())) {
            throw new InvalidUserException("댓글 삭제 권한이 없습니다.");
        }

        foundComment.setActive(false);
        commentRepository.save(foundComment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> getAllCommentsOfPost(Integer communityPostCode) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        List<Comment> commentList = commentRepository.findByCommunityPostAndActiveTrue(foundPost);
        return commentList.stream().map(comment -> modelMapper.map(comment, CommentDTO.class)).toList();
    }
}
