package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageListener;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SqsMessageListenerImpl implements SqsMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SqsMessageListenerImpl.class);

    @Override
    @LogMethod
    @SqsListener("${aws.sqs.person-queue}")
    public void queueListener(String message){
        try {

            logger.info("Mensagem recebida from SQS: {}", message);
        } catch (Exception e){
            logger.error("Ocorreu um erro ao receber a mensagem do SQS: {}", e.getMessage(), e);
        }
    }
}
