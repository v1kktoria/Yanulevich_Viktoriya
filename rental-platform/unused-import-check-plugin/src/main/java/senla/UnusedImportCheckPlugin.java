package senla;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mojo(name = "check-unused-imports", defaultPhase = LifecyclePhase.VALIDATE)
public class UnusedImportCheckPlugin extends AbstractMojo {

    @Parameter(property = "sourceDirectory", defaultValue = "${project.basedir}/src/main/java", readonly = true)
    private File sourceDirectory;

    @Override
    public void execute() {
        getLog().info("Плагин check-unused-imports начал выполнение...");
        if (!sourceDirectory.exists()) {
            getLog().warn("Источник не существует: " + sourceDirectory.getAbsolutePath());
            return;
        }
        processFiles(sourceDirectory);
        getLog().info("Плагин check-unused-imports завершил выполнение.");
    }

    private void processFiles(File directory) {
        try {
            Files.walk(directory.toPath())
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        checkUnusedImports(path.toFile());
                    });
        } catch (IOException e) {
            getLog().error("Ошибка при обходе файлов в директории: " + directory.getAbsolutePath(), e);
        }
    }

    private void checkUnusedImports(File file) {
        try {
            String content = Files.readString(file.toPath());
            List<String> declaredImports = extractDeclaredImports(content);
            String codeWithoutImports = removeImportsFromCode(content);

            declaredImports.stream()
                    .filter(importedClass -> {
                        String simpleClassName = importedClass.substring(importedClass.lastIndexOf('.') + 1);
                        Pattern usagePattern = Pattern.compile("\\b" + simpleClassName + "\\b");
                        Matcher usageMatcher = usagePattern.matcher(codeWithoutImports);
                        return !usageMatcher.find();
                    })
                    .forEach(importedClass -> {
                        StringBuilder message = new StringBuilder()
                                .append("Неиспользуемый импорт: ").append(importedClass)
                                .append(" в файле ").append(file.getName());
                        getLog().warn(message.toString());
                    });

        } catch (IOException e) {
            getLog().error("Ошибка при чтении файла: " + file.getName(), e);
        }
    }

    private List<String> extractDeclaredImports(String content) {
        Pattern importPattern = Pattern.compile("import\\s+([\\w\\.]+);");
        Matcher matcher = importPattern.matcher(content);
        List<String> imports = new ArrayList<>();
        while (matcher.find()) {
            imports.add(matcher.group(1));
        }
        return imports;
    }

    private String removeImportsFromCode(String content) {
        return content.replaceAll("import\\s+[\\w\\.]+;", "");
    }
}

