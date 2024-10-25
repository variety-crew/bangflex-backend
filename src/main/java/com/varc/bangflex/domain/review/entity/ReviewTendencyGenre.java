package com.varc.bangflex.domain.review.entity;

import com.varc.bangflex.domain.theme.entity.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tendency_genre")
@IdClass(ReviewTendencyGenreId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTendencyGenre {

    @Id
    @Column(name = "tendency_code", nullable = false)
    private Integer tendencyCode;

    @Id
    @Column(name = "genre_code", nullable = false)
    private Integer genreCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tendency_code", insertable = false, updatable = false)
    private ReviewTendency tendency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_code", insertable = false, updatable = false)
    private Genre genre;
}
