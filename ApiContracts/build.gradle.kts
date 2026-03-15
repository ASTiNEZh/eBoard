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

//sourceSets.forEach { source ->
//    source.resources.forEach {
//        tasks.named {  }
//    }
//}