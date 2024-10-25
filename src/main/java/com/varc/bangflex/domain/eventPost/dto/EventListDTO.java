package com.varc.bangflex.domain.eventPost.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventListDTO {

    String category;
    List<EventPostDTO> eventList;
}
