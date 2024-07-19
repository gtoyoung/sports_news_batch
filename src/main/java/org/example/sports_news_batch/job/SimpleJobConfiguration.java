package org.example.sports_news_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sports_news_batch.news.entity.SoccerNews;
import org.example.sports_news_batch.news.repsitory.SoccerNewsRepositroy;
import org.example.sports_news_batch.news.service.NewsService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * packageName    : org.example.sports_news_batch.job
 * fileName       : SimpleJobConfiguration
 * author         : user
 * date           : 2024-07-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-02        user       최초 생성
 */
@Slf4j
@Configuration
public class SimpleJobConfiguration {

    @Autowired
    private NewsService newsService;

    @Bean
    public Job myJob(JobRepository jobRepository, Step delStep, Step saveStep) {
        return new JobBuilder("myJob", jobRepository)
                .start(delStep)
                .incrementer(new RunIdIncrementer())
                .next(saveStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean("delStep")
    public Step delStep(JobRepository jobRepository, Tasklet deleteTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("delStep", jobRepository).tasklet(deleteTasklet, platformTransactionManager).allowStartIfComplete(true).build();
    }

    @Bean("saveStep")
    public Step saveStep(JobRepository jobRepository, Tasklet saveTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("saveStep", jobRepository).tasklet(saveTasklet, platformTransactionManager).allowStartIfComplete(true).build();
    }

    @Bean("deleteTasklet")
    public Tasklet deleteTasklet() {
        return ((contribution, chunkContext) -> {
            log.info("오늘 뉴스 삭제 시작.");
            newsService.deleteNews();
            log.info("오늘 뉴스 삭제 완료");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean("saveTasklet")
    public Tasklet saveTasklet() {
        return ((contribution, chunkContext) -> {
            log.info("오늘 뉴스 수집 시작");
            newsService.saveNews();
            log.info("오늘 뉴스 수집 완료");
            return RepeatStatus.FINISHED;
        });
    }
}
