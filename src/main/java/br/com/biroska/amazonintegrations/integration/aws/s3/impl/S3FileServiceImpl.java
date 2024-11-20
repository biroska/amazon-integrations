package br.com.biroska.amazonintegrations.integration.aws.s3.impl;

import br.com.biroska.amazonintegrations.integration.aws.s3.S3FileService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.person.model.Person;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private static final Logger logger = LoggerFactory.getLogger(S3FileServiceImpl.class);

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    private final S3Template s3Template;


    @Override
    @LogMethod
    public List<Person> listAll() {
        return null;
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
