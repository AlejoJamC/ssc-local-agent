package com.alejojamc.ssc_local_agent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.alejojamc.ssc_local_agent"])
class SscLocalAgentApplication

fun main(args: Array<String>) {
	runApplication<SscLocalAgentApplication>(*args)
}
