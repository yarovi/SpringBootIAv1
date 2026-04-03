package org.yasmani.io.springbootiav1;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentService  {

    private final VectorStore vectorStore;

    @Value("classpath:docs/manual-empleado.txt")
    private Resource documentFile;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDocuments() {
        try {
            log.info("📚 Cargando manual del empleado...");

            // Leer el documento
            TikaDocumentReader reader = new TikaDocumentReader(documentFile);
            List<Document> documents = reader.read();

            // Guardar DIRECTAMENTE sin dividir (Qdrant maneja documentos grandes)
            vectorStore.add(documents);

            log.info("✅ Manual cargado exitosamente en Qdrant");

        } catch (Exception e) {
            log.error("❌ Error cargando documento: {}", e.getMessage(), e);
        }
    }

}
