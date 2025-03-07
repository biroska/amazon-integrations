package br.com.biroska.amazonintegrations.integration.aws.sqs;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class SqsConfig {

    private final StaticCredentialsProvider awsCredentialsProvider;

    @Value("${aws.domain.sqs}")
    private String sqsDomain;

    @Bean
    public SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create( sqsDomain ))
                .region(Region.US_EAST_1 )
                .credentialsProvider( awsCredentialsProvider )
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient ){
        return SqsTemplate.builder().sqsAsyncClient( sqsAsyncClient ).build();
    }
}
