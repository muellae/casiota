package ch.muellae.casiota.iota;

import ch.muellae.casiota.properties.NodeProperties;
import lombok.RequiredArgsConstructor;
import org.iota.jota.IotaAPI;
import org.iota.jota.pow.pearldiver.PearlDiverLocalPoW;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@RequiredArgsConstructor
public class IotaApiConfiguration {

    private final NodeProperties nodeProperties;

    @Bean
    @Profile("!remotepow")
    public IotaAPI createIotaApiLocalPow() {
        return new IotaAPI.Builder()
                .protocol(nodeProperties.getProtocol())
                .host(nodeProperties.getHost())
                .port(nodeProperties.getPort())
                .build();
    }

    @Profile("remotepow")
    @Bean
    public IotaAPI createIotaApiRemotePow() {
        return new IotaAPI.Builder()
                .protocol(nodeProperties.getProtocol())
                .host(nodeProperties.getHost())
                .port(nodeProperties.getPort())
                .localPoW(new PearlDiverLocalPoW())
                .build();
    }

}
