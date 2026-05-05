package com.example.aiqa.service;

import org.springframework.stereotype.Service;

@Service
public class AiService {

    public String askQuestion(String documentText, String question) {

        if (documentText == null || documentText.isEmpty()) {
            return "Please upload a PDF first.";
        }

        String lowerDoc = documentText.toLowerCase();
        String lowerQuestion = question.toLowerCase();

        if (lowerQuestion.contains("summary") || lowerQuestion.contains("summarize")) {
            return summarize(documentText);
        }

        String[] words = lowerQuestion.split(" ");

        for (String word : words) {
            if (word.length() > 3 && lowerDoc.contains(word)) {
                int index = lowerDoc.indexOf(word);
                int start = Math.max(0, index - 250);
                int end = Math.min(documentText.length(), index + 500);

                return "Answer found in document:\n\n" +
                        documentText.substring(start, end);
            }
        }

        return "Answer not found in document.";
    }

    public String summarize(String documentText) {

        if (documentText == null || documentText.isEmpty()) {
            return "Please upload a PDF first.";
        }

        return "Summary of uploaded document:\n\n" +
                documentText.substring(0, Math.min(1000, documentText.length()));
    }
}