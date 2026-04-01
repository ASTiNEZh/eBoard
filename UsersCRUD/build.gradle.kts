plugins {
    java
    idea
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.ASTiNEZh"
version = "0.0.1-SNAPSHOT"
description = "UsersCRUD"

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

    /** Jackson */
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    /** SpringDoc dependencies */
    // я тут переопределяю для "springdoc-openapi-starter-webmvc" зависимость "commons-lang3" с уязвимостью
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5") {
        // отключаем старую
        exclude(group = "org.apache.commons", module = "commons-lang3")
    }
    // подключаем новую
    implementation("org.apache.commons:commons-lang3:3.20.0")

    /** PostgreSQL */
    implementation("org.postgresql:postgresql")

    /** Spring Data JPA */
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    /** Lombok */
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    /** ModelMapper */
    implementation("org.modelmapper:modelmapper:3.2.6")

    /** Устанавливаем зависимость с APIContracts */
    implementation("${group}:users-crud-openapi:0.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
