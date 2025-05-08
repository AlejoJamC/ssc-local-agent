package com.alejojamc.ssc_local_agent.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloSSCController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, SSC!"
    }

}