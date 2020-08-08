package ch.muellae.casiota;

import ch.muellae.casiota.properties.IotaApiConfiguration;
import ch.muellae.casiota.properties.NodeConfiguration;
import ch.muellae.casiota.properties.SeedProperties;
import org.iota.jota.IotaAPI;
import org.iota.jota.pow.pearldiver.PearlDiverLocalPoW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableConfigurationProperties({NodeConfiguration.class, SeedProperties.class, IotaApiConfiguration.class})
@ActiveProfiles("comnet")

class CasiotaApplicationTests {

	@Autowired
	private NodeConfiguration nodeConfiguration;


	@Bean
	@Profile("!localpow")
	public IotaAPI createIotaApiLocalPow() {
		return new IotaAPI.Builder()
				.protocol(nodeConfiguration.getProtocol())
				.host(nodeConfiguration.getHost())
				.port(nodeConfiguration.getPort())
				.build();
	}

	@Profile("localpow")
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
