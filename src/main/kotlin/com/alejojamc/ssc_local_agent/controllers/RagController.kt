package com.alejojamc.ssc_local_agent.controllers

import com.alejojamc.ssc_local_agent.services.IngestionService
import com.alejojamc.ssc_local_agent.services.IngestionType.*
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rag")
class RagController(private val ollamaChatModel: OllamaChatModel, private val ingestionService: IngestionService) {

    /**
     * Plain text prompt to the Ollama chat model.
     */
    @GetMapping("/prompt")
    fun basicPrompt(
        @RequestParam(
            value = "message",
            defaultValue = "What is RAG?"
        ) message: String
    ): Map<String, String> {
        val response = ollamaChatModel.call(message)
        return mapOf("generation" to response)
    }

    /**
     * Ingests Unstructured file into the vector store.
     */
    @GetMapping("/load-pdf")
    fun loadPdf(): Map<String, String> {
        ingestionService.ingest(PDF)
        return mapOf("status" to "PDF loaded into vector store")
    }

    @GetMapping("/load-markdown")
    fun loadMd(): Map<String, String> {
        ingestionService.ingest(MARKDOWN)
        return mapOf("status" to "Markdown loaded into vector store")
    }

    @GetMapping("/load-img")
    fun loadImg(): Map<String, String> {
        ingestionService.ingest(IMG)
        return mapOf("status" to "Image loaded into vector store")
    }

    /**
     * Consume the vector store and return the result.
     */
    @GetMapping("/query")
    fun queryRAGKnowledge(@RequestParam message: String): ResponseEntity<String> {
        return ingestionService.queryRAGKnowledge(message)
    }
}