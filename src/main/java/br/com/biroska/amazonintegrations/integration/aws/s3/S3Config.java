package br.com.biroska.amazonintegrations.integration.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.internal.http.loader.DefaultSdkAsyncHttpClientBuilder;
import software.amazon.awssdk.http.SdkHttpConfigurationOption;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.utils.AttributeMap;

@Configuration
public class S3Config {

    @Autowired
    private StaticCredentialsProvider awsCredentialsProvider;

    @Bean
    public S3AsyncClient s3AsyncClient(){


        final AttributeMap attributeMap = AttributeMap.builder()
                .put(SdkHttpConfigurationOption.TRUST_ALL_CERTIFICATES, true)
                .build();

/*        return S3Client.builder()
                .httpClient(sdkHttpClient)
                .build();*/

        final SdkAsyncHttpClient sdkAsyncHttpClient = new DefaultSdkAsyncHttpClientBuilder().buildWithDefaults(attributeMap);


        return S3AsyncClient.builder()
                .region(Region.US_EAST_1 )
                .credentialsProvider(  awsCredentialsProvider )
                .httpClient( sdkAsyncHttpClient )
                .build();
    }
}
