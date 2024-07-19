package org.example.sports_news_batch.news.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NaverSoccerDto {

    private String oid;
    private String aid;
    private String officeName;
    private String title;
    private String subContent;
    private String thumbnail;
    private String datetime;
    private String sectionName;
}
