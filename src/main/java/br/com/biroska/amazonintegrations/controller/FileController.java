package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.file.service.FileService;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import br.com.biroska.amazonintegrations.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/file")
public class FileController {

    private final FileService fileService;

    @LogMethod
    @GetMapping
    public List<Person> listPerson() {
        return null;
    }

    @LogMethod
    @PostMapping
    public String fileUpload(MultipartFile file) {
        return fileService.upload( file );
    }

}
