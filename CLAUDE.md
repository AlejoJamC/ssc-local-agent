# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot application written in Kotlin that demonstrates building a local AI agent for small businesses. The application integrates with Ollama for LLM capabilities and uses PGVector (PostgreSQL) for vector storage to implement RAG (Retrieval-Augmented Generation) functionality.

## Key Technologies

- **Spring Boot 3.5.3** with Kotlin
- **Spring AI 1.0.0** for LLM integration
- **Ollama** as the local LLM provider
- **PGVector** (PostgreSQL) for vector storage
- **Gradle** for dependency management
- **Java 21** runtime

## Development Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Build executable JAR
./gradlew bootJar

# Run tests
./gradlew test

# Clean build
./gradlew clean
```

### Database Setup
```bash
# Start PostgreSQL with PGVector extension
docker-compose up -d

# The database will be available at:
# - Host: localhost:5432
# - Database: postgresml
# - User: myuser
# - Password: secret
```

### Prerequisites
- **Ollama** must be running locally at `http://localhost:11434`
- Required Ollama models: `gemma3`, `mxbai-embed-large`, `nomic-embed-text`
- PostgreSQL with PGVector extension (provided via Docker Compose)

## Application Architecture

### Core Components

**Controllers** (`src/main/kotlin/com/alejojamc/ssc_local_agent/controllers/`)
- `RagController`: RAG endpoints for document ingestion and querying
- `OllamaController`: Direct Ollama chat model interaction
- `HelloController`: Basic health check endpoint

**Services** (`src/main/kotlin/com/alejojamc/ssc_local_agent/services/`)
- `IngestionService`: Handles document ingestion into vector store and RAG querying
- `IngestionType`: Enum for different document types (PDF, MARKDOWN, IMG)

**Document Readers** (`src/main/kotlin/com/alejojamc/ssc_local_agent/readers/`)
- `PdfDocumentReader`: PDF document processing using Spring AI
- `MarkdownReader`: Markdown document processing 
- `ImageReader`: Image document processing

### Key API Endpoints

**RAG Operations:**
- `GET /rag/load-pdf` - Ingest PDF documents into vector store
- `GET /rag/load-markdown` - Ingest Markdown documents into vector store
- `GET /rag/load-img` - Ingest images into vector store
- `GET /rag/query?message=<query>` - Query the RAG knowledge base
- `GET /rag/prompt?message=<message>` - Direct prompt to LLM

**Ollama Operations:**
- `GET /ollama/test-conn` - Test Ollama connection
- `GET /ollama/prompt?message=<message>` - Direct Ollama chat

### Document Processing Flow

1. **Ingestion**: Documents are processed by appropriate readers
2. **Text Splitting**: Content is split using `TokenTextSplitter`
3. **Vector Storage**: Embeddings are stored in PGVector database
4. **Retrieval**: Similar documents are retrieved using vector similarity search
5. **Generation**: Retrieved context is used to generate responses via Ollama

### Configuration

Key configuration in `application.properties`:
- **Database**: PostgreSQL connection for vector storage
- **Ollama**: Base URL and model configuration
- **PGVector**: Index type (HNSW), distance type (COSINE), dimensions (1024)
- **Spring AI**: Chat memory and vector store settings

### Sample Documents

The application includes sample documents in `src/main/resources/ragf/`:
- `ReturnPolicyRGFC.pdf` - PDF document for testing
- `faq.md` - Markdown FAQ document
- Logo images for multimodal testing

## Development Notes

- The application uses Spring AI's advisors for vector store integration
- Document readers are configured as Spring components for dependency injection
- Vector embeddings use 1024 dimensions with COSINE distance
- Chat memory is persisted using JDBC repository
- The application supports multiple Ollama models (configurable in properties)