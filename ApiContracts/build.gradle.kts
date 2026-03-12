import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java-library")
    id("org.openapi.generator") version "7.3.0"
}

group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-web:3.2.4")
    compileOnly("jakarta.validation:jakarta.validation-api:3.0.2")
    compileOnly("jakarta.annotation:jakarta.annotation-api:2.1.1")
    compileOnly("io.swagger.core.v3:swagger-annotations:2.2.20")
    compileOnly("org.openapitools:jackson-databind-nullable:0.2.6")
}

// Генерация OpenAPI
val apiSpecs = fileTree("src/main/resources").matching {
    include("**/*-openapi.yaml", "**/*-openapi.yml")
}

val generateTasks = mutableListOf<String>()

apiSpecs.forEach { spec ->
    val baseName = spec.nameWithoutExtension
        .replace(Regex("[-.]"), "_")

    val taskName = "generate${baseName.replaceFirstChar { it.uppercaseChar() }}"
    generateTasks += taskName

    tasks.register<GenerateTask>(taskName) {
        generatorName.set("spring")

        // ⬇️ РАБОЧЕЕ РЕШЕНИЕ: правильный URI для Windows
        val uri = spec.canonicalFile.toURI().toString()
            .replace("file:/", "file:///")
            .replace("\\", "/")
        inputSpec.set(uri)

        outputDir.set(layout.buildDirectory.dir("generated/$taskName").get().asFile.absolutePath)
        apiPackage.set("${project.group}.${baseName.lowercase()}.controller")
        modelPackage.set("${project.group}.${baseName.lowercase()}.dto")

        configOptions.set(mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "useJakartaEe" to "true",
            "skipDefaultInterface" to "true"
        ))

        globalProperties.set(mapOf(
            "apis" to "",
            "models" to ""
        ))
    }
}

tasks.compileJava {
    dependsOn(generateTasks)
}

sourceSets["main"].java {
    srcDir(layout.buildDirectory.dir("generated"))
}