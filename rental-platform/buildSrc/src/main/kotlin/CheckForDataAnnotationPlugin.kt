import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.GradleException

class CheckForDataAnnotationPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("checkForDataAnnotation") {
            it.group = "verification"
            it.doLast {
                val filesWithData = project.fileTree(project.projectDir).apply {
                    include("**/*.java")
                    exclude("src/gradleTest/**")
                }.filter { file ->
                    file.readText().contains("@Data")
                }
                if (!filesWithData.isEmpty) {
                    println("Найдена аннотация @Data в файлах:")
                    filesWithData.forEach { f -> println(" - ${f.relativeTo(project.projectDir)}") }
                    throw GradleException("Сборка прервана из-за использования @Data")
                } else {
                    println("Аннотация @Data не найдена")
                }
            }
        }
    }
}
