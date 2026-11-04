package com.lipan.pdfrag.controller;

import com.lipan.pdfrag.dto.DocumentUploadResponse;
import com.lipan.pdfrag.dto.PdfDocumentResponse;
import com.lipan.pdfrag.service.IngestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class DocumentController {
    private final IngestionService ingestionService;
    public DocumentController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @GetMapping("/api/documents")
    public ResponseEntity<List<PdfDocumentResponse>> getDocuments(){
        return ResponseEntity.status(HttpStatus.OK).body(ingestionService.getAllDocuments());
    }
    @DeleteMapping("/api/documents/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id){
        String response = ingestionService.deleteDocument(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
