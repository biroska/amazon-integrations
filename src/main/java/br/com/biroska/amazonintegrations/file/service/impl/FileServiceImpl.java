package br.com.biroska.amazonintegrations.file.service.impl;

import br.com.biroska.amazonintegrations.file.service.FileService;
import br.com.biroska.amazonintegrations.integration.aws.s3.S3FileService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final S3FileService s3Service;

    @Override
    @LogMethod
    public List<Person> listAll() {
        return null;
    }

    @Override
    @LogMethod
    public String upload(MultipartFile multipartFile) {

        try {
            return s3Service.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao realizar o upload do arquivo", e );
        }

        return StringUtils.EMPTY;
    }
}