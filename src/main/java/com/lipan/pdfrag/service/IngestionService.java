package com.lipan.pdfrag.service;

import com.lipan.pdfrag.dto.DocumentUploadResponse;
import com.lipan.pdfrag.dto.PdfDocumentResponse;
import com.lipan.pdfrag.entity.PdfDocument;
import com.lipan.pdfrag.repository.PdfDocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngestionService {
    private final VectorStore vectorStore;
    private final PdfDocumentRepository pdfDocumentRepository;
    public IngestionService(VectorStore vectorStore, PdfDocumentRepository pdfDocumentRepository){
        this.vectorStore = vectorStore;
        this.pdfDocumentRepository = pdfDocumentRepository;
    }
    private PdfDocumentResponse documentToDTO(PdfDocument document){

            return new PdfDocumentResponse(document.getId(),
                    document.getFileName(),
                    document.getFileSize(),
                    document.getUploadTimestamp(),
                    document.getChunkCount()
            );
    }
    public String deleteDocument(Long id){
        PdfDocument doc = pdfDocumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document with id " + id + " not found"));
        pdfDocumentRepository.deleteById(id);
        return ("Pdf with id: " + id + " has been deleted!");
    }

    public List<PdfDocumentResponse> getAllDocuments(){
        List<PdfDocument> rawPdfList = pdfDocumentRepository.findAll();

        List<PdfDocumentResponse> returnList = new ArrayList<>();
        rawPdfList.forEach((rawFile)->{
            returnList.add(documentToDTO(rawFile));
        });
        return returnList;
    }

    public DocumentUploadResponse documentEmbedding(MultipartFile file){
        try{
            Resource pdfResource = new InputStreamResource(file.getInputStream());

            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            List<Document> pages = reader.read();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(300)
                    .build();
            List<Document> chunks = splitter.apply(pages);
            vectorStore.accept(chunks);

            PdfDocument pdfDocument = new PdfDocument(file.getOriginalFilename(), file.getSize(), LocalDateTime.now(), chunks.size());
            pdfDocumentRepository.save(pdfDocument);

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
