package com.lipan.pdfrag.dto;

public record DocumentUploadResponse(
        String fileName,
        int chunkCount,
        String message
) {}
