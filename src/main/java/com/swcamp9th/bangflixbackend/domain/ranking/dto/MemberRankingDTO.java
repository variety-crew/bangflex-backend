package com.swcamp9th.bangflixbackend.domain.ranking.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRankingDTO {

    private Integer memberCode;
    private String id;
    private String nickname;
    private String email;
    private Boolean isAdmin;
    private String image;
    private Integer point;
    private Date createdAt;
    private Boolean active;
}
