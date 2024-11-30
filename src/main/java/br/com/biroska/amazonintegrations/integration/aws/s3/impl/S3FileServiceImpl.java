package br.com.biroska.amazonintegrations.integration.aws.s3.impl;

import br.com.biroska.amazonintegrations.integration.aws.s3.S3FileService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        List<S3Object> contents = getS3Object();

        return contents.stream()
                    .map(S3Object::key)
                    .toList();
    }

    @Override
    @LogMethod
    public String upload(InputStream stream, String filename) {

        try {
            S3Resource uploaded = s3Template.upload(bucketName, filename, stream, null);
            URL url = uploaded.getURL();
            return url.toString();
        } catch (IOException e) {
            logger.error("Ocorreu um erro ao realizar o upload para o bucket: {}", bucketName, e);
        }

        return StringUtils.EMPTY;
    }

    @Override
    @LogMethod
    public String download(String s3Filename, Path downloadPath) {

        Path pathResolved = null;
        String key = null;

        try {
            pathResolved = downloadPath.resolve(s3Filename);

            List<S3Object> s3Objects = getS3Object();

            Optional<String> keyFile = s3Objects.stream()
                    .map(S3Object::key)
                    .filter(s3Filename::equals)
                    .findFirst();

            key = keyFile.orElse(null);

            if (StringUtils.isBlank(key)) {
                logger.warn("Nao foi possivel encontrar arquivos no bucket: {}", bucketName);
                return "Arquivo nao encontrado";
            }

            GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

            CompletableFuture<GetObjectResponse> objectResponseAsync = s3AsyncClient.getObject(objectRequest, pathResolved);

            GetObjectResponse getObjectResponse = objectResponseAsync.get();

            System.out.println("getObjectResponse = " + getObjectResponse);

        } catch (InvalidPathException e){
            logger.error("Nao foi possivel montar o path para a escrita do arquivo: {}", pathResolved, e);
        } catch (SdkException e ){
            logger.error("Nao foi possivel realizar o download do arquivo: {}", key, e);

        } catch (ExecutionException | InterruptedException e) {
            logger.error("Nao foi possivel escrever o arquivo em disco: {} -  {}", pathResolved, key, e);
        }


        return key;
    }

    @Override
    @LogMethod
    public Boolean delete(String s3Filename) {

        try {
            List<S3Object> s3Objects = getS3Object();

            Optional<String> keyFile = s3Objects.stream()
                    .map(S3Object::key)
                    .filter(s3Filename::equals)
                    .findFirst();

            String key = keyFile.orElse(null);

            if (StringUtils.isBlank(key)) {
                logger.warn("Nao foi possivel encontrar arquivos no bucket: {}", bucketName);
                return Boolean.FALSE;
            }

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                                                        .bucket(bucketName)
                                                        .key(s3Filename).build();

            CompletableFuture<DeleteObjectResponse> future = s3AsyncClient.deleteObject(deleteObjectRequest);
            future.get();
            return future.isDone();

        } catch(S3Exception | ExecutionException | InterruptedException e){
            logger.error("Nao foi possivel excluir o arquivo: {}", s3Filename, e);
        }

        return Boolean.FALSE;
    }

    private List<S3Object> getS3Object() {

        List<S3Object> EMPTY_LIST = new ArrayList<>();

        try {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder().bucket(bucketName).build();
            CompletableFuture<ListObjectsResponse> future = s3AsyncClient.listObjects(listObjectsRequest);

            ListObjectsResponse listObjectsResponse = future.get();

            if (listObjectsResponse == null) {
                logger.warn("Nao foi possivel encontrar arquivos no bucket: {}", bucketName);
                return EMPTY_LIST;
            }

            return listObjectsResponse.contents();

        } catch (InterruptedException | ExecutionException e) {
            logger.error("Ocorreu um erro ao obter os arquivos no bucket: {}", bucketName, e);
        }

        return EMPTY_LIST;

    }
}
