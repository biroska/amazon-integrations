package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageServiceImpl implements SqsMessageService {

    private static final Logger logger = LoggerFactory.getLogger(SqsMessageServiceImpl.class);

    @Value("${aws.sqs.endpoint.person-queue}")
    private String sqsEndpoint;

    private final SqsTemplate sqsTemplate;

    public SqsMessageServiceImpl(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    @LogMethod
    public Boolean sendMessage(String message) {

        logger.info("Mensagem enviada para o SQS: {}", message);

        try {
            sqsTemplate.send( sqsEndpoint, message);
        } catch (Exception e ){
            logger.error("Ocorreu um erro ao enviar a mensagem para o SQS: {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
