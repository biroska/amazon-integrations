package br.com.biroska.amazonintegrations.file.service;

import br.com.biroska.amazonintegrations.person.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String upload(MultipartFile file);

    List<Person> listAll();
}
