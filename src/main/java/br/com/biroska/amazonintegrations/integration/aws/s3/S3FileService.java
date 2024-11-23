package br.com.biroska.amazonintegrations.integration.aws.s3;

import java.io.InputStream;
import java.util.List;

public interface S3FileService {

    String upload(InputStream stream, String filename);

    List<String> listAll();
}
