package br.com.biroska.amazonintegrations.integration.aws.sqs.impl;

import br.com.biroska.amazonintegrations.integration.aws.sqs.SqsMessageListener;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.person.adapter.PersonAdapter;
import br.com.biroska.amazonintegrations.person.model.Person;
import br.com.biroska.amazonintegrations.person.service.PersonService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SqsMessageListenerImpl implements SqsMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SqsMessageListenerImpl.class);

    private final PersonService personService;

    @Override
    @LogMethod
    @SqsListener("${aws.sqs.person-queue}")
    public void queueListener(String message){
        try {
            logger.info("Mensagem recebida from SQS: {}", message);

            Person person = PersonAdapter.toPerson(message);

            if (ObjectUtils.isEmpty(person) ){
                logger.error("Nao foi possivel gravar a pessoa no DB pois o objeto esta vazio");
                return;
            }
            personService.save( person );

        } catch (Exception e){
            logger.error("Ocorreu um erro ao receber a mensagem do SQS: {}", e.getMessage(), e);
        }
    }
}
