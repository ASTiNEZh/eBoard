buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:12.0.3")
    }
}

plugins {
    id("java")
    id("org.flywaydb.flyway") version "12.0.3"
}

group = "ru.ASTiNEZh"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Flyway
    implementation("org.flywaydb:flyway-core:12.0.3")
    implementation("org.flywaydb:flyway-database-postgresql:12.0.3")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.7.10")
}


flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/e_board"
    user = "postgres"
    password = "postgres"
    locations = arrayOf("filesystem:src/main/resources/migration")
    cleanDisabled = true
}

tasks.named("compileJava") {
    dependsOn("flywayMigrate")
}

tasks.test {
    useJUnitPlatform()
}