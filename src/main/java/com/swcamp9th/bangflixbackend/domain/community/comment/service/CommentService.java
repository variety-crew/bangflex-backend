package com.swcamp9th.bangflixbackend.domain.community.comment.service;

import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentUpdateDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(Integer communityPostCode, CommentCreateDTO newComment);

    CommentDTO updateComment(Integer communityPostCode, Integer commentCode, CommentUpdateDTO modifiedComment);

    void deleteComment(Integer communityPostCode, Integer commentCode, CommentDeleteDTO deletedComment);

    List<CommentDTO> getAllCommentsOfPost(Integer communityPostCode);
}
