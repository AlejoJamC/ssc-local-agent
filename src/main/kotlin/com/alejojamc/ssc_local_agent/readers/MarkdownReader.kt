package com.alejojamc.ssc_local_agent.readers

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.ai.reader.markdown.MarkdownDocumentReader
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig
import org.springframework.ai.document.Document

@Component
class MarkdownReader(
    @Value("classpath:ragf/faq.md") private val resource: Resource
) {
    fun loadMarkdown(): List<Document> {
        val config = MarkdownDocumentReaderConfig.builder()
            .withHorizontalRuleCreateDocument(true)
            .withIncludeCodeBlock(false)
            .withIncludeBlockquote(false)
            .build()

        val reader = MarkdownDocumentReader(resource, config)
        return reader.get()
    }
}