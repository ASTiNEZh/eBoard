plugins {
    java
}

allprojects {
    group = "ru.ASTiNEZh"
    version = "0.0.1-SNAPSHOT"
    description = "eBoard"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    enabled = false
}
