package com.alejojamc.ssc_local_agent.services

import com.alejojamc.ssc_local_agent.readers.PdfDocumentReader
import com.alejojamc.ssc_local_agent.readers.ImageReader
import com.alejojamc.ssc_local_agent.readers.MarkdownReader
import org.slf4j.LoggerFactory
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class IngestionService(
    private val vectorStore: VectorStore,
    private val pdfDocumentReader: PdfDocumentReader,
    private val markdownReader: MarkdownReader,
    private val imageReader: ImageReader
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
            .let { vectorStore.accept(it) }
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
}