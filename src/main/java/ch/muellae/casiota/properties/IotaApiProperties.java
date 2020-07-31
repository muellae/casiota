package ch.muellae.casiota.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Component
@ConfigurationProperties("iota.api")
@Validated
@NoArgsConstructor
@Data
public class IotaApiProperties {

    @NotNull
    private int mwm;

    @NotNull
    private int depth;

    @NotNull
    private int securityLevel;

    @NotNull
    private int connectionTimeout = 500;

    @NotNull
    private int threshold = 100;

}
