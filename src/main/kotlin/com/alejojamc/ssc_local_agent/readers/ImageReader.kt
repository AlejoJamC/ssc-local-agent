package com.alejojamc.ssc_local_agent.readers

import org.springframework.stereotype.Component
import org.springframework.core.io.Resource
import org.springframework.beans.factory.annotation.Value
import org.springframework.ai.document.Document

@Component
class ImageReader(
    @Value("classpath:ragf/ragf_logo1.png") private val logo1Png: Resource
) {
    fun getDocsFromImage(): List<Document> {
        // Replace with actual image-to-document logic as needed
        val doc = Document("Image content placeholder", mapOf("filename" to "ragf_logo1.png"))
        return listOf(doc)
    }
}