package ch.muellae.casiota.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class ScheduleJobsByProfile {


    @Profile("prod")
    @Bean
    public ScheduledJob scheduledJob() {
        return new ScheduledJob("@Profile");
    }
}
