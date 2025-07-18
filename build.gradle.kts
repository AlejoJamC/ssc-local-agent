plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

group = "com.alejojamc"
version = "0.0.1-SNAPSHOT"
description = "ssc-local-agent"
extra["springAiVersion"] = "1.0.0"

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.actuator)
    api(libs.org.springframework.boot.spring.boot.starter.data.jdbc)
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.com.fasterxml.jackson.module.jackson.module.kotlin)
    api(libs.org.jetbrains.kotlin.kotlin.reflect)
    api(libs.org.jetbrains.kotlin.kotlin.stdlib)
    api(libs.org.springframework.ai.spring.ai.advisors.vector.store)
    api(libs.org.springframework.ai.spring.ai.starter.model.chat.memory.repository.jdbc)
    api(libs.org.springframework.ai.spring.ai.starter.model.ollama)
    api(libs.org.springframework.ai.spring.ai.starter.vector.store.pgvector)
    api(libs.org.springframework.ai.spring.ai.pdf.document.reader)
    api(libs.org.springframework.ai.spring.ai.markdown.document.reader)
    runtimeOnly(libs.org.springframework.boot.spring.boot.devtools)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.org.jetbrains.kotlin.kotlin.test.junit5)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
