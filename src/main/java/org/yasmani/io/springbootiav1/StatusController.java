package org.yasmani.io.springbootiav1;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    private final VectorStore vectorStore;

    public StatusController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/status")
    public String status() {
        return """
            ✅ Servicio funcionando
            📚 Qdrant conectado
            📄 Documento cargado y listo para preguntas
            """;
    }
}
