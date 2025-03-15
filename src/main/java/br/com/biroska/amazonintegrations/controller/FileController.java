package br.com.biroska.amazonintegrations.controller;

import br.com.biroska.amazonintegrations.api.FileApi;
import br.com.biroska.amazonintegrations.file.service.FileService;
import br.com.biroska.amazonintegrations.file.service.model.FileDelete;
import br.com.biroska.amazonintegrations.file.service.model.FileDownload;
import br.com.biroska.amazonintegrations.logging.LogMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aws-integration/file")
public class FileController implements FileApi {

    private final FileService fileService;

    @LogMethod
    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ofNullable( fileService.listAll() );
    }

    @LogMethod
    @PostMapping
    public ResponseEntity<String> fileUpload(MultipartFile file) {
        return ResponseEntity.ofNullable( fileService.upload( file ) );
    }

    @LogMethod
    @PostMapping("/download")
    public ResponseEntity<String> fileDownload(@RequestBody FileDownload fileDownload) {
        return ResponseEntity.ofNullable(fileService.download( fileDownload ) );
    }

    @LogMethod
    @PostMapping("/delete")
    public ResponseEntity<Boolean> fileDelete(@RequestBody FileDelete fileDelete) {
        return ResponseEntity.ofNullable(fileService.delete( fileDelete ) );
    }
}
