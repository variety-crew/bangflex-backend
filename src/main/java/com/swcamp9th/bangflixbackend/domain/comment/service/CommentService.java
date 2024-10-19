package com.swcamp9th.bangflixbackend.domain.comment.service;

import com.swcamp9th.bangflixbackend.domain.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.comment.dto.CommentCreateDTO;
import com.swcamp9th.bangflixbackend.domain.comment.dto.CommentDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.comment.dto.CommentUpdateDTO;

import java.util.List;

public interface CommentService {

    void createComment(String loginId, Integer communityPostCode, CommentCreateDTO newComment);

    void updateComment(String loginId, Integer communityPostCode, Integer commentCode, CommentUpdateDTO modifiedComment);

    void deleteComment(String loginId, Integer communityPostCode, Integer commentCode, CommentDeleteDTO deletedComment);

    List<CommentDTO> getAllCommentsOfPost(Integer communityPostCode);
}
