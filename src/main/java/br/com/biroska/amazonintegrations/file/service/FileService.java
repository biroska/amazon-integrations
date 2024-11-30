package br.com.biroska.amazonintegrations.file.service;

import br.com.biroska.amazonintegrations.file.service.model.FileDelete;
import br.com.biroska.amazonintegrations.file.service.model.FileDownload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String upload(MultipartFile file);

    List<String> listAll();

    String download(FileDownload fileDownload);

    Boolean delete(FileDelete fileDelete);
}
