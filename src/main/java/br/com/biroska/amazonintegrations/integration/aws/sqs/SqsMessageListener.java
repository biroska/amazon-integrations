package br.com.biroska.amazonintegrations.integration.aws.sqs;

public interface SqsMessageListener {

    void queueListener(String message);
}
