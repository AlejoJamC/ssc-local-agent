package com.alejojamc.ssc_local_agent.readers

import org.springframework.stereotype.Component
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.document.Document
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader

@Component
class PdfDocumentReader {

    fun getDocsFromPdfWithCatalog(): List<Document> {
        val pdfReader = ParagraphPdfDocumentReader(
            /* resourceUrl = */ "classpath:ragf/ReturnPolicyRGFC.pdf",
            /* config = */ PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(
                    ExtractedTextFormatter.builder()
                        .withNumberOfTopTextLinesToDelete(0)
                        .build()
                )
                .withPagesPerDocument(1)
                .build()
        )
        return pdfReader.read()
    }
}