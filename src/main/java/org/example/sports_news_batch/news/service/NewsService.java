package org.example.sports_news_batch.news.service;

import lombok.RequiredArgsConstructor;
import org.example.sports_news_batch.news.entity.NaverSoccerDto;
import org.example.sports_news_batch.news.entity.SoccerNews;
import org.example.sports_news_batch.news.repsitory.SoccerNewsRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : org.example.sports_news_batch.news.service
 * fileName       : NewsService
 * author         : user
 * date           : 2024-07-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-17        user       최초 생성
 */
@Service
public class NewsService {

    @Autowired
    private SoccerNewsRepositroy repository;

    @Autowired
    private NaverSoccerNews naverSoccerNews;

    public void test() {
        System.out.println(repository.count());
    }

    @Transactional
    public void deleteNews() {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(23, 59, 59));

        repository.deleteDateTime(startDatetime, endDatetime);
    }

    @Transactional
    public void saveNews() {

        try {
            // 오늘 날짜를 가져온다.
            Date nowDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

            // 변환 날짜
            String strNowDate = simpleDateFormat.format(nowDate);

            // 전체 페이지 수
            int totalPage = Integer.parseInt(naverSoccerNews.getTotalPages(strNowDate));

            // 전체 페이지 수 만큼 진행
            for (int i = 1; i <= totalPage; i++) {
                // 페이지당 기사 가져오기
                List<NaverSoccerDto> result = naverSoccerNews.getSoccerNews(strNowDate, i);

                // SoccerNews Entity로 변환
                List<SoccerNews> convertList = result.stream().map(SoccerNews::createSoccerNews).collect(Collectors.toList());

                repository.saveAll(convertList);
            }
        } catch (Exception ex){
            ex.getStackTrace();
        }
    }
}
