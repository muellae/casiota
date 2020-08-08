package ch.muellae.casiota;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewTransactionEventListener implements ApplicationListener<NewTransactionEvent> {

    @Override
    public void onApplicationEvent(NewTransactionEvent event) {
        System.out.println("Received spring custom event - " + event);
    }
}
