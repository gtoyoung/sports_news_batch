package org.example.sports_news_batch.news.repsitory;

import org.example.sports_news_batch.news.entity.SoccerNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * packageName    : org.example.sports_news_batch.news.repsitory
 * fileName       : SoccerNewsRepositroy
 * author         : user
 * date           : 2024-07-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-17        user       최초 생성
 */
@Repository
public interface SoccerNewsRepositroy extends JpaRepository<SoccerNews, Long> {
    @Modifying
    @Query("delete from SoccerNews u where u.datetime between ?1 and ?2")//동시성 문제로 직접 쿼리 입력
    void deleteDateTime(LocalDateTime before, LocalDateTime after);
}
