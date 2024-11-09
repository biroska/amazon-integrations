package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageListener;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageListenerImpl implements SqsMessageListener {


    @SqsListener("person-queue.fifo")
    public void queueListener(String message){

        try {
            System.out.println("message = " + message);
        } catch (Exception e) {
            System.err.println("e = " + e.getMessage());
        }
    }
}
