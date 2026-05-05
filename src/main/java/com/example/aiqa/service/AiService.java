package com.example.aiqa.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiService {

    private final String apiKey = System.getenv("OPENAI_API_KEY");
    private final ObjectMapper mapper = new ObjectMapper();

    public String askQuestion(String documentText, String question) {

        if (documentText == null || documentText.isEmpty()) {
            return "Please upload a PDF first.";
        }

        String prompt = "Answer ONLY from this document. If answer is not present, say: Not found in document.\n\n"
                + documentText.substring(0, Math.min(3000, documentText.length()))
                + "\n\nQuestion: " + question;

        return callOpenAI(prompt);
    }

    public String summarize(String documentText) {

        if (documentText == null || documentText.isEmpty()) {
            return "Please upload a PDF first.";
        }

        String prompt = "Summarize this document clearly:\n\n"
                + documentText.substring(0, Math.min(3000, documentText.length()));

        return callOpenAI(prompt);
    }

    private String callOpenAI(String prompt) {

        try {

            String jsonBody = mapper.createObjectNode()
                    .put("model", "gpt-4o-mini")
                    .set("messages", mapper.createArrayNode()
                            .add(mapper.createObjectNode()
                                    .put("role", "user")
                                    .put("content", prompt)))
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("API RESPONSE: " + response.body());

            JsonNode root = mapper.readTree(response.body());

            if (root.has("error")) {
                return "OpenAI API Error: " + root.get("error").get("message").asText();
            }

            if (!root.has("choices")) {
                return "Invalid API response: " + response.body();
            }

            return root.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "AI error: " + e.getMessage();
        }
    }
}