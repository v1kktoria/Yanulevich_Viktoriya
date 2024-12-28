package senla;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mojo(name = "empty-code-check", defaultPhase = LifecyclePhase.COMPILE)
public class EmptyCodeCheckPlugin extends AbstractMojo {

    @Parameter(property = "sourceDirectory", defaultValue = "${project.basedir}/src/main/java")
    private File sourceDirectory;

    @Override
    public void execute() {
        getLog().info("Плагин empty-code-check начал выполнение...");
        if (!sourceDirectory.exists()) {
            getLog().warn("Источник не существует: " + sourceDirectory.getAbsolutePath());
            return;
        }
        processFiles(sourceDirectory);
        getLog().info("Плагин empty-code-check завершил выполнение.");
    }

    private void processFiles(File directory) {
        try {
            Files.walk(directory.toPath())
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> checkEmptyCode(path.toFile()));
        } catch (IOException e) {
            getLog().error("Ошибка при обходе файлов в директории: " + directory.getAbsolutePath(), e);
        }
    }

    private void checkEmptyCode(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            checkEmptyMethods(file, content);
            checkEmptyClasses(file, content);
        } catch (IOException e) {
            getLog().error("Ошибка при чтении файла: " + file.getName(), e);
        }
    }

    private void checkEmptyMethods(File file, String content) {
        Optional<String> className = extractFirstClassName(content);

        findMatches(content, getMethodPattern())
                .stream()
                .filter(method -> !isConstructor(method, className))
                .forEach(method -> logWarning("Пустой метод", file, method));
    }

    private void checkEmptyClasses(File file, String content) {
        findMatches(content, Pattern.compile("(?m)class\\s+\\w+\\s*\\{\\s*(//.*|\\s)*\\}"))
                .forEach(classInfo -> logWarning("Пустой класс", file, classInfo));
    }

    private Optional<String> extractFirstClassName(String content) {
        return Arrays.stream(content.split("\n"))
                .map(this::extractClassName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<String> extractClassName(String line) {
        Pattern pattern = Pattern.compile("\\bclass\\b\\s+([a-zA-Z0-9_]+)");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private Pattern getMethodPattern() {
        return Pattern.compile(
                "\\s*(public|private|protected|static|final|synchronized|abstract)?\\s*" +
                        "\\w+\\s+(\\w+)\\s*" +
                        "\\([^)]*\\)\\s*" +
                        "(throws\\s+[\\w<>,\\s]*\\s*)?" +
                        "\\{\\s*(//.*|\\s)*\\}$",
                Pattern.MULTILINE
        );
    }

    private List<String> findMatches(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group().trim());
        }
        return matches;
    }

    private boolean isConstructor(String method, Optional<String> className) {
        return className.isPresent() && method.contains(className.get());
    }

    private void logWarning(String type, File file, String info) {
        StringBuilder message = new StringBuilder()
                .append(type).append(": ").append(file.getName()).append(" - ").append(info);
        getLog().warn(message.toString());
    }
}
