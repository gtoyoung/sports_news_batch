package org.example.sports_news_batch.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // 작업 시작 전 로직 (필요한 경우 구현)
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("배치 작업이 성공적으로 완료되었습니다. 애플리케이션을 종료합니다.");
            System.exit(0);
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            System.out.println("배치 작업이 실패했습니다. 애플리케이션을 종료합니다.");
            System.exit(1);
        }
    }
}