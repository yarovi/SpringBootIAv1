package org.yasmani.io.springbootiav1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        // 1. Creamos un "Asesor" que se encarga de buscar en la BD vectorial
        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().topK(4).similarityThreshold(0.7).build())
                .build();

        // 2. Construimos el ChatClient y le añadimos el asesor
        this.chatClient = chatClientBuilder
                .defaultAdvisors(qaAdvisor)
                .build();
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody Map<String, String> payload) {
        String userQuestion = payload.get("question");
        // El ChatClient automáticamente buscará en la BD y usará el contexto para responder.
        return chatClient.prompt()
                .user(userQuestion)
                .call()
                .content();
    }
}
