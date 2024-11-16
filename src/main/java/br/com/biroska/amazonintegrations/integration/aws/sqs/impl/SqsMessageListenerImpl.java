package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageListener;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SqsMessageListenerImpl implements SqsMessageListener {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Override
//    @SqsListener("person-queue.fifo")
    public void queueListener(String message){

        try {

            Message<?> received1 = queueMessagingTemplate.receive("person-queue.fifo");
            System.out.println("received1 = " + received1);

            System.out.println("message = " + message);
        } catch (Exception e) {
            System.err.println("e = " + e.getMessage());
        }
    }
}
