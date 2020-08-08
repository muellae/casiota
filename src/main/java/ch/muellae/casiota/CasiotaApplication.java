package ch.muellae.casiota;

import ch.muellae.casiota.properties.NodeConfiguration;
import org.iota.jota.IotaAPI;
import org.iota.jota.pow.pearldiver.PearlDiverLocalPoW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CasiotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasiotaApplication.class, args);
    }


    @Autowired
    private NodeConfiguration nodeConfiguration;

    @Bean
    @Profile("!remotepow")
    public IotaAPI createIotaApiLocalPow() {
        return new IotaAPI.Builder()
                .protocol(nodeConfiguration.getProtocol())
                .host(nodeConfiguration.getHost())
                .port(nodeConfiguration.getPort())
                .build();
    }

    @Profile("remotepow")
    @Bean
    public IotaAPI createIotaApiRemotePow() {
        return new IotaAPI.Builder()
                .protocol(nodeConfiguration.getProtocol())
                .host(nodeConfiguration.getHost())
                .port(nodeConfiguration.getPort())
                .localPoW(new PearlDiverLocalPoW())
                .build();
    }

}
