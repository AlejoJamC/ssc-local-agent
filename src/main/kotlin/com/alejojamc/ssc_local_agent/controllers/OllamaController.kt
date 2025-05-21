package com.alejojamc.ssc_local_agent.controllers

import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ollama")
class OllamaController(private val ollamaChatModel: OllamaChatModel) {

    @GetMapping("/test-conn")
    fun testConnection(): Map<String, String> {
        val response = ollamaChatModel.call("Say Hello to the Server Side Chat Audience!")
        return mapOf("generation" to response)
    }

    @GetMapping("/prompt")
    fun basicPrompt(
        @RequestParam(
            value = "message",
            defaultValue = "What is your cut-off date?"
        ) message: String
    ): Map<String, String> {
        val response = ollamaChatModel.call(message)
        return mapOf("generation" to response)
    }

}
