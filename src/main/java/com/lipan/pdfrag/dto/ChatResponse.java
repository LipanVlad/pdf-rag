package com.lipan.pdfrag.dto;

import org.springframework.ai.document.Document;

import java.util.List;

public record ChatResponse(String message, List<Document> sources) {}
