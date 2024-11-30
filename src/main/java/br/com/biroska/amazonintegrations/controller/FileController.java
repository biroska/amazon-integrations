package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.file.service.FileService;
import br.com.biroska.amazonintegrations.file.service.model.FileDelete;
import br.com.biroska.amazonintegrations.file.service.model.FileDownload;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/file")
public class FileController {

    private final FileService fileService;

    @LogMethod
    @GetMapping
    public List<String> listFiles() {
        return fileService.listAll();
    }

    @LogMethod
    @PostMapping
    public String fileUpload(MultipartFile file) {
        return fileService.upload( file );
    }

    @LogMethod
    @PostMapping("/download")
    public String fileDownload(@RequestBody FileDownload fileDownload) {
        return fileService.download( fileDownload );
    }

    @LogMethod
    @PostMapping("/delete")
    public Boolean fileDelete(@RequestBody FileDelete fileDelete) {
        return fileService.delete( fileDelete );
    }
}
