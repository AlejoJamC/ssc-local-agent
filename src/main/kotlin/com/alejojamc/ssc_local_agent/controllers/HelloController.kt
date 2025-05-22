package com.alejojamc.ssc_local_agent.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController {

    @RequestMapping
    fun hello(): String {
        return "Hello, Server Side Chat!"
    }

}