plugins {
    java
    idea
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.6.0"
    id("org.flywaydb.flyway") version "12.0.3"
}

group = "ru.ASTiNEZh"
version = "0.0.1-SNAPSHOT"
description = "UsersCRUD"
var apiDir = layout.buildDirectory.dir("openapi")!!

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Jackson
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    // SpringDoc dependencies
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // PostgreSQL
    implementation("org.postgresql:postgresql")

    // Spring Data JPA
    implementation("org.springframework.data:spring-data-jpa")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    // ModelMapper
    implementation("org.modelmapper:modelmapper:3.2.6")
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/UsersCRUD/src/main/resources/openapi.yaml")
    outputDir.set(apiDir.get().asFile.absolutePath)
    apiPackage.set("$group.controller")
    modelPackage.set("$group.dto")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "useJakartaEe" to "true"
        )
    )
}

sourceSets.main {
    java.srcDir(apiDir.map{it.dir("/src/main/java")})
}

tasks.named("compileJava") {
    dependsOn("openApiGenerate")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
