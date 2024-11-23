package br.com.biroska.amazonintegrations.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String upload(MultipartFile file);

    List<String> listAll();
}
