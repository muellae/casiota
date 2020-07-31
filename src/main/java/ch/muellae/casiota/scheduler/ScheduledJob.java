package ch.muellae.casiota.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledJob {

    private String source;

    public ScheduledJob(String source) {
        this.source = source;
    }

    @Scheduled(fixedDelay = 2000)
    public void doIT() {
        log.info("Cleaning temp directory via {}", source);
    }
}
