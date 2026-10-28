package com.lipan.pdfrag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
        @NotBlank(message = "User query must not be empty")
        @Size(min = 2, max = 1000, message = "Query must be between 2 and 1000 characters")
        String userQuery
) {}
