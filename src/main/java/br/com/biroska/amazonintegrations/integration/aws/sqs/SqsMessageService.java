package br.com.biroska.amazonintegrations.integration.aws.sqs;

public interface SqsMessageService {
    Boolean sendMessage(String message);
}
