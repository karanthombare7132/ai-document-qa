# AI Document Q&A Web Application

## Overview
This is a full-stack AI-powered application that allows users to upload PDF documents and ask questions based on the content.

## Features
- Upload PDF documents
- Extract text from PDF
- Ask questions using AI
- Generate summary of document
- React frontend + Spring Boot backend
- OpenAI API integration
- Dockerfile included

## Tech Stack
- Backend: Spring Boot (Java)
- Frontend: React.js
- AI: OpenAI API
- PDF Processing: Apache PDFBox
- Containerization: Docker

## How to Run

### Backend
```bash
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Docker
```bash
mvn clean package
docker build -t aiqa-app .
docker run -p 8080:8080 aiqa-app
```

## Future Improvements
- Audio/video upload
- Whisper transcription
- Timestamp extraction
- Play button for media
- Database integration
- CI/CD pipeline

## Demo
Demo video link: Add here