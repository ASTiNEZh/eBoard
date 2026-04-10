import org.apache.commons.io.FilenameUtils
import org.gradle.kotlin.dsl.register
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java-library")
    id("maven-publish")
    id("org.openapi.generator") version "7.3.0"
}

group = "ru.ASTiNEZh"
version = "0.0.1-SNAPSHOT"
description = "APIContrascts"

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

val projGroup = group
sourceSets.filter { it.name != "test" && it.name != "common" }.forEach { source ->
    source.resources.filter { it.isFile }.filter { it.extension in listOf("yaml", "yml", "json") }.forEach {
        val group = "$group"
        val (artifact, version, description) = getInfoOpenApi(it)
        processingOpenApi(file = it, groupId = group, artifactId = artifact, version = version)
    }
}

fun getInfoOpenApi(file: File): Triple<String, String, String> {
    val openapi = io.swagger.v3.parser.OpenAPIV3Parser().read(file.absolutePath)
    val description = openapi.info.description
    val version = if (!openapi.info.version.isNullOrBlank()) openapi.info.version else "$version"
    val packageName = file.nameWithoutExtension
    return Triple(packageName, version, description)
}

fun processingOpenApi(file: File, groupId: String, artifactId: String, version: String) {
    val fileName = file.nameWithoutExtension
    val generateTask = tasks.register<GenerateTask>("generate_$groupId-$artifactId-$version") {
        generatorName.set("spring")
        library.set("spring-cloud")
        inputSpec.set(file.absolutePath.replace("\\", "/"))
        outputDir.set(layout.buildDirectory.dir("/openapi/$fileName").get().asFile.absolutePath)
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
    val pathFromRoot = file.absolutePath
    val fileNameList = FilenameUtils.getPathNoEndSeparator(pathFromRoot).split(File.separator)
    println("File paths $fileNameList")


    val compileTask = tasks.register("compile_$groupId-$artifactId-$version", JavaCompile::class.java) {
        this.group = "openapi compilation"
        this.source = files(layout.buildDirectory.dir("/openapi/$fileName/src").get().asFile).asFileTree
        this.destinationDirectory.set(layout.buildDirectory.dir("classes/$fileName").get().asFile)
        this.classpath = sourceSets.getByName("main").compileClasspath

        dependsOn(generateTask)
    }

    val packageTask = tasks.register("package_$groupId-$artifactId-$version", Jar::class.java) {
        group = "openapi packaging"
        archiveVersion.set(version)
        archiveBaseName.set(fileName)
        from(layout.buildDirectory.dir("classes/$fileName").get().asFile)

        dependsOn(compileTask)
    }

    tasks.named("jar") {
        dependsOn(packageTask)
    }

    publishing {
        publications {
            create<MavenPublication>("${groupId}_${artifactId}_$fileName") {
                this.groupId = groupId
                this.artifactId = fileName
                this.version = version
                artifact(packageTask.get())
                repositories {
                    mavenLocal()
                }
            }
        }
    }
}

afterEvaluate {
    tasks.named("build") {
        dependsOn("publishToMavenLocal")
    }
}
