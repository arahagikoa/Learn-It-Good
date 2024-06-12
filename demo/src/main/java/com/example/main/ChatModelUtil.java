package com.example.main;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.testcontainers.containers.GenericContainer;

//@Author 
//Aleksander Majkowski

public class ChatModelUtil {
    private static final String MODEL_NAME = "llama2";
    private static final GenericContainer<?> ollama;

    static {
        ollama = new GenericContainer<>("langchain4j/ollama-" + MODEL_NAME + ":latest")
                .withExposedPorts(11434);

        try {
            ollama.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to start Docker container for Ollama model", e);
        }
    }

    public static String generateAnswer(String prompt) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(baseUrl())
                .modelName(MODEL_NAME)
                .build();
        return model.generate(prompt);
    }

    private static String baseUrl() {
        return String.format("http://%s:%d", ollama.getHost(), ollama.getFirstMappedPort());
    }
}
