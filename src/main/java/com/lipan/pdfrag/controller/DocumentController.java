package com.lipan.pdfrag.controller;

import com.lipan.pdfrag.dto.DocumentUploadResponse;
import com.lipan.pdfrag.service.IngestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentController {
    private final IngestionService ingestionService;
    public DocumentController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/api/documents/upload")
    public ResponseEntity<DocumentUploadResponse> uploadRawDocument (@RequestParam MultipartFile file){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file cannot be empty");
        }
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are supported");
        }

        DocumentUploadResponse response = ingestionService.documentEmbedding(file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
