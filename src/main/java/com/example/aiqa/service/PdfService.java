package com.example.aiqa.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    private String extractedText = "";

    public String extractText(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return "No file uploaded.";
        }

        try (PDDocument document = PDDocument.load(file.getInputStream())) {

            PDFTextStripper stripper = new PDFTextStripper();
            extractedText = stripper.getText(document);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error reading PDF file.";
        }

        return extractedText;
    }

    public String getExtractedText() {
        return extractedText;
    }
}