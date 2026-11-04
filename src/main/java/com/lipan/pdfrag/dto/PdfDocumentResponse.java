package com.lipan.pdfrag.dto;

import java.time.LocalDateTime;

public record PdfDocumentResponse(
        Long id,
        String fileName,
        Long fileSize,
        LocalDateTime uploadTimestamp,
        int chunkCount) {
}
