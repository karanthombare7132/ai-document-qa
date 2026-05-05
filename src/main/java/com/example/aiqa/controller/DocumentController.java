package com.example.aiqa.controller;

import com.example.aiqa.service.AiService;
import com.example.aiqa.service.PdfService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentController {

    private final PdfService pdfService;
    private final AiService aiService;

    public DocumentController(PdfService pdfService, AiService aiService) {
        this.pdfService = pdfService;
        this.aiService = aiService;
    }

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        pdfService.extractText(file);
        return "PDF uploaded and text extracted successfully.";
    }

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        return aiService.askQuestion(pdfService.getExtractedText(), question);
    }

    @GetMapping("/summary")
    public String summarizePdf() {
        return aiService.summarize(pdfService.getExtractedText());
    }
}