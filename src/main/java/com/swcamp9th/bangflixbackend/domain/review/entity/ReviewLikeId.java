package com.swcamp9th.bangflixbackend.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewLikeId implements Serializable {
    private Integer memberCode;
    private Integer reviewCode;
}
