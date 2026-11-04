package com.lipan.pdfrag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class PdfDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadTimestamp;
    private int chunkCount;

    public PdfDocument(){}

    public PdfDocument(String fileName, Long fileSize, LocalDateTime uploadTimestamp, int chunkCount) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadTimestamp = uploadTimestamp;
        this.chunkCount = chunkCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(LocalDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public int getChunkCount() {
        return chunkCount;
    }

    public void setChunkCount(int chunkCount) {
        this.chunkCount = chunkCount;
    }
}
