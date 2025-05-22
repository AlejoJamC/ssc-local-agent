package com.alejojamc.ssc_local_agent.services

import com.alejojamc.ssc_local_agent.readers.PdfDocumentReader
import com.alejojamc.ssc_local_agent.readers.ImageReader
import com.alejojamc.ssc_local_agent.readers.MarkdownReader
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class IngestionService(
    private val vectorStore: VectorStore,
    private val pdfDocumentReader: PdfDocumentReader,
    private val markdownReader: MarkdownReader,
    private val imageReader: ImageReader,
    private val ollamaChatModel: OllamaChatModel
) {
    private val logger = LoggerFactory.getLogger(IngestionService::class.java)

    fun ingest(type: IngestionType) {
        when (type) {
            IngestionType.PDF -> ingestPdf()
            IngestionType.MARKDOWN -> ingestMarkdown()
            IngestionType.IMG -> ingestImage()
        }
    }

    private fun ingestPdf() {
        logger.info("Ingesting PDF using PdfDocumentReader component")
        pdfDocumentReader.getDocsFromPdfWithCatalog()
            .let { TokenTextSplitter().apply(it) }
            .let { vectorStore.add(it) }
        logger.info("PDF loaded into vector store")
    }

    private fun ingestMarkdown() {
        logger.info("Ingesting Markdown using MarkdownReader component")
        markdownReader.loadMarkdown()
            .let { TokenTextSplitter().apply(it) }
            .let { vectorStore.add(it) }
        logger.info("Markdown loaded into vector store")
    }

    private fun ingestImage() {
        logger.info("Ingesting Image using ImageReader component")
        imageReader.getDocsFromImage()
            .let { TokenTextSplitter().apply(it) }
            .let { vectorStore.add(it) }
        logger.info("Image loaded into vector store")
    }

    fun queryRAGKnowledge(query: String): ResponseEntity<String> {
        val information = vectorStore.similaritySearch(query)
            ?.joinToString(System.lineSeparator()) { it.getFormattedContent() }
            .orEmpty()

        val systemPromptTemplate = SystemPromptTemplate(
            """
        You are a helpful assistant.
        Use only the following information to answer the question.
        Do not use any other information. If you do not know, simply answer: IDK :(

        {information}
        """.trimIndent()
        )

        val systemMessage = systemPromptTemplate.createMessage(mapOf("information" to information))
        val userMessage = PromptTemplate("{query}").createMessage(mapOf("query" to query))
        val prompt = Prompt(listOf(systemMessage, userMessage))

        return ollamaChatModel.call(prompt)
            .result
            .output
            .text
            .let { ResponseEntity.ok(it) }
    }
}