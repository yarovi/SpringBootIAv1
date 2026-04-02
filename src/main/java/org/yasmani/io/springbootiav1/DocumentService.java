package org.yasmani.io.springbootiav1;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DocumentService implements CommandLineRunner {

    private final VectorStore vectorStore;

    // Inyectamos el archivo de documentación desde 'resources/docs/'
    @Value("classpath:docs/mi-documentacion.txt")
    private Resource documentationFile;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Este método se ejecuta al iniciar la app para cargar los documentos
    @Override
    public void run(String... args) throws Exception {
        // 1. Leer el documento


        // 3. Guardar los trozos en Qdrant
        vectorStore.add(chunks);
        System.out.println("✅ Documentación cargada y dividida en " + chunks.size() + " fragmentos.");
    }

}
