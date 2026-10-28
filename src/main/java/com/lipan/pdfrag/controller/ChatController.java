package com.lipan.pdfrag.controller;

import com.lipan.pdfrag.dto.ChatRequest;
import com.lipan.pdfrag.dto.ChatResponse;
import com.lipan.pdfrag.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("/api/chat")
    public ResponseEntity<ChatResponse> getThe3ClosestEmbeds(@Valid @RequestBody ChatRequest chatRequest){
        ChatResponse chatResponse = chatService.findSimilarDocuments(chatRequest.userQuery());
        return ResponseEntity.status(HttpStatus.OK).body(chatResponse);
    }
}
