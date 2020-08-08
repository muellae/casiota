package ch.muellae.casiota;

import ch.muellae.casiota.properties.IotaApiConfiguration;
import ch.muellae.casiota.properties.SeedProperties;
import lombok.extern.slf4j.Slf4j;
import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetNewAddressResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.TrytesConverter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@EnableScheduling

public class TransactionReceiverService {

    public static final int NEW_ADDRESS_BUFFER = 1;

    private final IotaAPI iotaAPI;

    private final IotaApiConfiguration iotaApiConfiguration;

    private final SeedProperties seedProperties;

    private final ApplicationEventPublisher applicationEventPublisher;

    private boolean running = false;
    private final LinkedHashSet<String> newAddresses = new LinkedHashSet<>();
    private final LinkedHashSet<String> usedAddresses = new LinkedHashSet<>();

    public TransactionReceiverService(IotaAPI iotaAPI, IotaApiConfiguration iotaApiConfiguration, SeedProperties seedProperties, ApplicationEventPublisher applicationEventPublisher) {
        this.iotaAPI = iotaAPI;
        this.iotaApiConfiguration = iotaApiConfiguration;
        this.seedProperties = seedProperties;
        this.applicationEventPublisher = applicationEventPublisher;
        log.info("ReceivingService initialized");
    }


    @Scheduled(fixedRate = 10000)
    public void pollNewAddresses() {
        if (running) {
            log.debug("skipping, because already running...");
            return;
        }
        running = true;
        try {
            createNewAddresses();
            String nextAddress = newAddresses.iterator().next();
            log.info("Polling next address = {} ", nextAddress);

            // RECEIVE
            List<Transaction> transactionsByAddress = findTransactionObjectsByAddress(nextAddress);

            if (!transactionsByAddress.isEmpty()) {
                newAddresses.remove(nextAddress);
                usedAddresses.add(nextAddress);

                for (Transaction transaction : transactionsByAddress) {
                    log.info("transaction = " + transaction);
                    log.info("firstTransaction.getValue() = " + transaction.getValue());
                    log.info("transaction.Message() = " + TrytesConverter.trytesToAscii(transaction.getSignatureFragments() + "9"));

                    applicationEventPublisher.publishEvent(new NewTransactionEvent(transaction));
                }
            } else {
                log.info("Nothing new");
            }

        } catch (ArgumentException ex) {
            log.warn("Error connecting to node: {}", ex.getMessage());
        } finally {
            running = false;
        }
    }

    private List<Transaction> findTransactionObjectsByAddress(String nextAddress) {
        String[] addresses = new String[]{nextAddress};
        return iotaAPI.findTransactionObjectsByAddresses(addresses);
    }

    private void createNewAddresses() {
        while (newAddresses.size() < NEW_ADDRESS_BUFFER) {
            try {
                AddressRequest addressRequest = new AddressRequest.Builder(seedProperties.getDealerSeed(), iotaApiConfiguration.getSecurityLevel())
                        .checksum(true)
                        .index(usedAddresses.size() + newAddresses.size())
                        .amount(NEW_ADDRESS_BUFFER - newAddresses.size())
                        .build();
                GetNewAddressResponse response = iotaAPI.generateNewAddresses(addressRequest);
                newAddresses.addAll(response.getAddresses());
            } catch (RuntimeException e) {
                log.debug("Error connecting node: {}", e.getMessage());
            }
            log.info("newAddresses: " + newAddresses.size());
        }
    }
}
