package ch.muellae.casiota.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties("casino")
@EnableScheduling
public class ApplicationProperties {

    @NotNull
    private boolean skipConfirmation = true;

    private String tag = "MUELLAEONTHETANGLE9";


}
