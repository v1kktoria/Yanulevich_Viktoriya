plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("checkForDataAnnotation") {
            id = "check-for-data-annotation"
            implementationClass = "CheckForDataAnnotationPlugin"
        }
    }
}
