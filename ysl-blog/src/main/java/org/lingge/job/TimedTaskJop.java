package org.lingge.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimedTaskJop {
    @Scheduled(cron = "0 0/10 0/1 * * ? ")
    public void testjop(){
        System.out.println("定时任务---");
    }
}
