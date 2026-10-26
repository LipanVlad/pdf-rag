package com.lipan.pdfrag.service;

import com.lipan.pdfrag.dto.ChatRequest;
import com.lipan.pdfrag.dto.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final VectorStore vectorStore;
    public ChatService(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    public ChatResponse getClosest3Embeds(String userQuery){
        try {
            SearchRequest searchRequest = SearchRequest.builder()
                    .query(userQuery)
                    .topK(3)
                    .build();
            List<Document> embeds = vectorStore.similaritySearch(searchRequest);
            return new ChatResponse("Number of sources retrieved: " + embeds.size(), embeds);
        }catch(Exception e){
            throw new RuntimeException("Error, " + e.getMessage());
        }
    }
}
