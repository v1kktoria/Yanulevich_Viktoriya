import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckForDataAnnotationTaskTest {

    @TempDir
    Path projectDir;

    @BeforeEach
    void setUp() throws IOException {
        writeSettings();
        writeBuildFile();
    }

    @Test
    void shouldPassWhenNoDataAnnotation() throws Exception {
        writeJavaFile("""
            public class Demo {
                private String field;
            }
            """);

        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir.toFile())
                .withArguments("checkForDataAnnotation")
                .build();

        assertEquals(TaskOutcome.SUCCESS, result.task(":checkForDataAnnotation").getOutcome());
        assertTrue(result.getOutput().contains("Аннотация @Data не найдена"));
    }

    @Test
    void shouldFailWhenDataAnnotationPresent() throws Exception {
        writeJavaFile("""
            import lombok.Data;
            
            @Data
            public class Demo {
                private String field;
            }
            """);

        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir.toFile())
                .withArguments("checkForDataAnnotation")
                .buildAndFail();

        assertTrue(result.getOutput().contains("Сборка прервана из за использования @Data"));
        assertTrue(result.getOutput().contains("Найдена аннотация @Data в файлах:"));
    }

    @Test
    void shouldCheckMultipleJavaFiles() throws Exception {
        writeJavaFile("""
            public class ValidClass {
                private String field;
            }
            """);

        Path anotherFile = projectDir.resolve("src/main/java/Invalid.java");
        Files.writeString(anotherFile, """
            import lombok.Data;
            
            @Data
            public class Invalid {
                private String field;
            }
            """);

        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir.toFile())
                .withArguments("checkForDataAnnotation")
                .buildAndFail();

        assertTrue(result.getOutput().contains("Найдена аннотация @Data в файлах:"));
        assertTrue(result.getOutput().contains("Invalid.java"));
    }

    private void writeBuildFile() throws IOException {
        String script = """
                import org.gradle.api.JavaVersion
                                
                plugins {
                    java
                }
                                
                java {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                                
                tasks.register("checkForDataAnnotation") {
                    group = "verification"
                    doLast {
                        val filesWithData = project.fileTree(project.projectDir).apply {
                            include("**/*.java")
                            exclude("src/gradleTest/**")
                        }.filter { file ->
                            file.readText().contains("@Data")
                        }
                        if (!filesWithData.isEmpty) {
                            println("Найдена аннотация @Data в файлах:")
                            filesWithData.forEach { f -> println(" - ${f.relativeTo(project.projectDir)}") }
                            throw GradleException("Сборка прервана из за использования @Data")
                        } else {
                            println("Аннотация @Data не найдена")
                        }
                    }
                }
                                
                """;
        Files.writeString(projectDir.resolve("build.gradle.kts"), script);
    }

    private void writeSettings() throws IOException {
        Files.writeString(projectDir.resolve("settings.gradle.kts"), "");
    }

    private void writeJavaFile(String content) throws IOException {
        Path src = projectDir.resolve("src/main/java/Demo.java");
        Files.createDirectories(src.getParent());
        Files.writeString(src, content);
    }
}