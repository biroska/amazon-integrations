package br.com.biroska.amazonintegrations.integration.aws.s3.impl;

import br.com.biroska.amazonintegrations.integration.aws.s3.S3FileService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private static final Logger logger = LoggerFactory.getLogger(S3FileServiceImpl.class);

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    private final S3Template s3Template;

    private final S3AsyncClient s3AsyncClient;


    @Override
    @LogMethod
    public List<String> listAll() {

        List<String> EMPTY_LIST = List.of("");

        try {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder().bucket(bucketName).build();
            CompletableFuture<ListObjectsResponse> future = s3AsyncClient.listObjects(listObjectsRequest);

            ListObjectsResponse listObjectsResponse = future.get();

            if ( listObjectsResponse == null ){
                logger.warn("Nao foi possivel encontrar arquivos no bucket: {}", bucketName);
                return EMPTY_LIST;
            }

            List<S3Object> contents = listObjectsResponse.contents();

            if (CollectionUtils.isEmpty( contents )){
                logger.warn("A lista retornada dos arquivos no bucket: {} esta vazia", bucketName);
                return EMPTY_LIST;
            }

            return contents.stream()
                    .map(S3Object::key)
                    .toList();

        } catch (InterruptedException | ExecutionException e) {
            logger.error("Ocorreu um erro ao obter os arquivos no bucket: {}", bucketName, e);
        }
        return EMPTY_LIST;
    }

    @Override
    @LogMethod
    public String upload(InputStream stream, String filename) {

        try {
            S3Resource uploaded = s3Template.upload(bucketName, filename, stream, null);
            URL url = uploaded.getURL();
            return url.toString();
        } catch (IOException e) {
            logger.error("Ocorreu um erro ao realizar o upload para o bucket: {}", bucketName, e );
        }

        return StringUtils.EMPTY;
    }
}
