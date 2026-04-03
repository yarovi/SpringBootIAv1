# 🧠 Spring AI: Guía de Conceptos Fundamentales

> **Propósito:** Esta guía consolida los conceptos clave de Spring AI para el proyecto de asistente de documentación técnica. Sirve como referencia rápida y material de estudio.

## 🎯 ¿Qué estamos construyendo?

Un **Chatbot de Documentación Técnica** que responde preguntas basándose **EXCLUSIVAMENTE** en documentos proporcionados por el usuario (no usa conocimiento general de Internet).

### Flujo de trabajo

1.  **Preparación (Una sola vez)**
    - El usuario proporciona documentos (TXT, PDF, Word, etc.).
    - La aplicación lee y divide los documentos en fragmentos pequeños.
    - Un **modelo de embedding** convierte cada fragmento en un **vector** (una lista de números que representa su significado).
    - Los vectores se guardan en una **base de datos vectorial** (Qdrant).

2.  **Consulta (Cada vez que un usuario pregunta)**
    - El usuario hace una pregunta.
    - La pregunta se convierte en un vector usando el **mismo modelo de embedding**.
    - La base de datos vectorial busca los fragmentos de documento con vectores **más similares** al de la pregunta.
    - Los fragmentos encontrados se envían como **contexto** a un **modelo de chat** (LLM).
    - El LLM genera una respuesta basada **estrictamente** en el contexto proporcionado.

## 🔑 Conceptos Clave (La confusión aclarada)

### 1. Modelo de Chat (LLM)

- **¿Qué hace?** Genera texto, mantiene conversaciones, responde preguntas.
- **¿Cómo se usa?** Recibe un `prompt` (instrucción + contexto) y devuelve una respuesta en lenguaje natural.
- **Ejemplo en el proyecto:** `gpt-4o-mini` (OpenAI).
- **¿Necesita vectores?** **No.** El modelo de chat no trabaja directamente con vectores. Trabaja con texto.
- **Código:**
  ```java
  @Service
  public class ChatService {
      private final ChatClient chatClient;

      public String chat(String userMessage) {
          return chatClient.prompt()
                  .user(userMessage)
                  .call()
                  .content();
      }
  }