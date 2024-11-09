package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageService;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SqsMessageServiceImpl implements SqsMessageService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    private final AmazonSQSAsync amazonSQSAsync;

    @Autowired
    public SqsMessageServiceImpl(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
        this.amazonSQSAsync = amazonSQSAsync;
    }

    @Override
    public void sendMessage(String message) {

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl("https://xxxxx.fifo")
                .withMessageBody( message)
                .withMessageGroupId( UUID.randomUUID().toString() )

                .withMessageDeduplicationId(UUID.randomUUID().toString());

        amazonSQSAsync.sendMessage(sendMessageRequest);
    }
}
