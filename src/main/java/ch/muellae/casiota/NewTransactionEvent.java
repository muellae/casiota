package ch.muellae.casiota;

import org.springframework.context.ApplicationEvent;

public class NewTransactionEvent extends ApplicationEvent {
    public NewTransactionEvent(Object source) {
        super(source);
    }
}
