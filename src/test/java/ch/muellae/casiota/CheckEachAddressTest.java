package ch.muellae.casiota;

import ch.muellae.casiota.properties.IotaApiConfiguration;
import ch.muellae.casiota.properties.SeedProperties;
import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.FindTransactionResponse;
import org.iota.jota.dto.response.GetNewAddressResponse;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.Checksum;
import org.iota.jota.utils.TrytesConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("comnet")
@SpringBootTest(classes = CasiotaApplicationTests.class)
class CheckEachAddressTest {

    @Autowired
    private SeedProperties seedProperties;

    @Autowired
    private IotaAPI iotaAPI;

    @Autowired
    private IotaApiConfiguration iotaApiConfiguration;

    @BeforeEach
    void setUp() {
        System.out.println("iotaApiConfiguration = " + iotaApiConfiguration);
    }

    @Test
    void name() {

        AddressRequest addressRequest = new AddressRequest.Builder(seedProperties.getDealerSeed(), iotaApiConfiguration.getSecurityLevel())
                .amount(5)
                .checksum(true)
//                .addSpendAddresses(true)
                .build();
        GetNewAddressResponse response = iotaAPI.generateNewAddresses(addressRequest);

        System.out.println("response = " + response);


//        List<String> addresses = response.getAddresses();
        String first = response.first();
        System.out.println("first = " + first);

        String[] addresses = {"ELUUR9SO9PSRBHQXXFCGPZVGGBQGJOUXKMBEX9DJSOPJQXQR9VQRZADXHXOULGDPZBUCUXKRCUQZ9JYJYCCQD9BJRD"};
//        FindTransactionResponse transaction = iotaAPI.findTransactionsByAddresses(Checksum.addChecksum(response.first()));
//        FindTransactionResponse transaction = iotaAPI.findTransactions(addresses, null, null, null);
//        System.out.println("transaction = " + transaction);

        List<Transaction> transactionObjectsByAddresses = iotaAPI.findTransactionObjectsByAddresses(addresses);
        System.out.println("transactionObjectsByAddresses = " + transactionObjectsByAddresses);

        for (Transaction transactionObjectsByAddress : transactionObjectsByAddresses) {
            String s = TrytesConverter.trytesToAscii(transactionObjectsByAddress.getSignatureFragments() + "9");
            System.out.println("s = " + s);

        }

    }
}