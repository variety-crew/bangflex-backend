package com.swcamp9th.bangflixbackend.domain.ex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//
//@Entity
//@Table(name = "ex")
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Ex {

//    @Id
//    private Long id;

    @Column
    int a;

    @Column
    int b;

}
