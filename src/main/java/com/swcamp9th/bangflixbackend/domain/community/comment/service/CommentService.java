package com.swcamp9th.bangflixbackend.domain.community.comment.service;

import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentRequestDTO;

public interface CommentService {

    CommentDTO createComment(Integer communityPostCode, CommentRequestDTO newComment);
}
