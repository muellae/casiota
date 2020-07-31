package ch.muellae.casiota.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties("iota.node")
@Validated
@NoArgsConstructor
@Getter
@Setter
public class NodeProperties {

    @NotBlank
    private String host;

    @NotBlank
    private String protocol;

    @NotNull
    private int port = 443;

}
