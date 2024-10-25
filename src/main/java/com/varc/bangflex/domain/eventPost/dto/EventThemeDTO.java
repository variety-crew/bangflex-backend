package com.varc.bangflex.domain.eventPost.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventThemeDTO {

    private Integer themeCode;
    private String name;
    private String posterImage;
    private Integer storeCode;
}
