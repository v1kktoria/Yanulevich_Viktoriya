import org.gradle.api.JavaVersion

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("check-for-data-annotation")
    java
}

group = "senla"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.0"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("org.postgresql:postgresql")
    testImplementation("org.projectlombok:lombok:1.18.26")
    runtimeOnly("com.h2database:h2")
    implementation("org.liquibase:liquibase-core")

    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.3.1.Final")

    implementation ("io.minio:minio:8.5.17")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val functionalTestSourceSet = sourceSets.create("gradleTest") {
    java.srcDir("src/gradleTest/java")
    resources.srcDir("src/gradleTest/resources")
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += output + compileClasspath
}

configurations[functionalTestSourceSet.implementationConfigurationName].apply {
    dependencies.add(project.dependencies.gradleTestKit())
    dependencies.add(project.dependencies.platform("org.junit:junit-bom:5.10.0"))
    dependencies.add(project.dependencies.create("org.junit.jupiter:junit-jupiter"))
}

tasks.register<Test>("gradleTest") {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
    useJUnitPlatform()
    shouldRunAfter("test")
}

tasks.named("check") {
    dependsOn("gradleTest", "checkForDataAnnotation")
}
