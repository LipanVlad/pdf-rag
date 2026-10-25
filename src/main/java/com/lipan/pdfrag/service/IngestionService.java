package com.lipan.pdfrag.service;

import com.lipan.pdfrag.dto.DocumentUploadResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class IngestionService {
    private final VectorStore vectorStore;
    public IngestionService(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    public DocumentUploadResponse documentEmbedding(MultipartFile file){
        try{
            // MultipartFile gives raw bytes, but Spring AI readers require 'Resource'
            Resource pdfResource = new InputStreamResource(file.getInputStream());

            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            List<Document> pages = reader.read();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(300)
                    .build();
            List<Document> chunks = splitter.apply(pages);
            vectorStore.accept(chunks);

            return new DocumentUploadResponse(
                    file.getOriginalFilename(),
                    chunks.size(),
                    "Document successfully ingested with " + chunks.size() + " chunks."
            );
        }catch(IOException e){
            throw new RuntimeException("Failed to read PDF file: " + file.getOriginalFilename());
        }
    }
}
