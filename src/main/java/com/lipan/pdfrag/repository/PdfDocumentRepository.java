package com.lipan.pdfrag.repository;

import com.lipan.pdfrag.entity.PdfDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
}
