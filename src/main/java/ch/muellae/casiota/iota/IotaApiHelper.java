package ch.muellae.casiota.iota;

import ch.muellae.casiota.properties.IotaApiProperties;
import lombok.AllArgsConstructor;
import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetBalancesAndFormatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IotaApiHelper {

    @Autowired
    private IotaAPI iotaAPI;

    @Autowired
    private IotaApiProperties iotaApiProperties;


    public String getNextAddress(final String seed) {
        AddressRequest addressRequest = new AddressRequest.Builder(seed, iotaApiProperties.getSecurityLevel()).checksum(true).build();
        return iotaAPI.generateNewAddresses(addressRequest).first();
    }

    public GetBalancesAndFormatResponse getInputs(final String seed) {
        return iotaAPI.getInputs(seed,
                iotaApiProperties.getSecurityLevel(), 0, 0, 0);
    }

}
