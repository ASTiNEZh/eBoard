plugins {
    java
    idea
    id("org.springframework.boot") version "3.4.3"
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
        languageVersion = JavaLanguageVersion.of(21)
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

    // Jackson
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    // SpringDoc dependencies
    // я тут переопределяю для "springdoc-openapi-starter-webmvc" зависимость "commons-lang3" с уязвимостью
    // отключаем старую
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5") {
        exclude(group = "org.apache.commons", module = "commons-lang3")
    }
    // подключаем новую
    implementation("org.apache.commons:commons-lang3:3.20.0")

    // PostgreSQL
    implementation("org.postgresql:postgresql")

    // Spring Data JPA
//    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    // ModelMapper
    implementation("org.modelmapper:modelmapper:3.2.6")

    // Flyway
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
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
