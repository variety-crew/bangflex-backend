package com.swcamp9th.bangflixbackend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteReviewDTO {
    Integer reviewCode;
    Integer memberCode;
}
