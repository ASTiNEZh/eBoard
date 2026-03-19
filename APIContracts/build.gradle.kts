import org.gradle.kotlin.dsl.register
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java-library")
    id("org.openapi.generator") version "7.3.0"
}

group = "ru.ASTiNEZh"
version = "0.0.1-SNAPSHOT"
description = "APIContrascts"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

val projGroup = group
sourceSets.filter { it.name != "test" }.forEach { source ->
    source.resources.forEach {
        val fileName = it.nameWithoutExtension
        val generateTask = tasks.register<GenerateTask>(fileName) {
            generatorName.set("spring")
            library.set("spring-cloud")
            inputSpec.set(it.absolutePath.replace("\\", "/"))
            outputDir.set(layout.buildDirectory.dir("/openapi/$fileName").get().toString())
            apiPackage.set("$projGroup.controller")
            modelPackage.set("$projGroup.dto")
            configOptions.set(
                mapOf(
                    "dateLibrary" to "java8",
                    "interfaceOnly" to "true",
                    "useJakartaEe" to "true"
                )
            )
        }

        tasks.named("compileJava") {
            dependsOn(generateTask)
        }

        sourceSets.main {
            java.srcDir("${layout.buildDirectory.get().toString()}/openapi/$fileName/src/main/java")
        }
    }
}

