package br.com.biroska.amazonintegrations.integration.aws.sqs;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig {

    @Autowired
    private StaticCredentialsProvider awsCredentialsProvider;

    @Bean
    public SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient.builder()
                .region(Region.US_EAST_1 )
                .credentialsProvider( awsCredentialsProvider )
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient ){
        return SqsTemplate.builder().sqsAsyncClient( sqsAsyncClient ).build();
    }
}
