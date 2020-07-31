package ch.muellae.casiota.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties("iota.seed")
@Validated
@NoArgsConstructor
@Getter
@Setter
public class SeedProperties {

    @NotBlank
    private String sourceSeed;

    @NotBlank
    private String destinationSeed;

    @NotBlank
    private String dealerSeed;

    @NotBlank
    private String playerSeed;


}
