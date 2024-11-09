package br.com.biroska.amazonintegrations.integration.aws.sqs;

public interface SqsMessageService {
    void sendMessage(String message);
}
