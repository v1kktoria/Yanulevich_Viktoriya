package senla;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Mojo(name = "checkstyle", defaultPhase = LifecyclePhase.PACKAGE)
public class CheckStylePlugin extends AbstractMojo {

    @Parameter(property = "sourceDirectory", defaultValue = "${project.basedir}/src/main/java", readonly = true)
    private File sourceDirectory;

    @Parameter(property = "maxLineLength", defaultValue = "150")
    private int maxLineLength;

    @Override
    public void execute() {
        getLog().info("Плагин checkstyle начал выполнение...");
        if (!sourceDirectory.exists()) {
            getLog().warn("Источник не существует: " + sourceDirectory.getAbsolutePath());
            return;
        }
        processFiles(sourceDirectory);
        getLog().info("Плагин checkstyle завершил выполнение.");
    }

    private void processFiles(File directory) {
        try {
            Files.walk(directory.toPath())
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        checkMethodStyle(path.toFile());
                    });
        } catch (IOException e) {
            getLog().error("Ошибка при обходе файлов в директории: " + directory.getAbsolutePath(), e);
        }
    }

    private void checkMethodStyle(File file) {
        try {
            String content = Files.readString(file.toPath());
            String[] lines = content.split("\n");
            checkLineLengths(lines, file);
            checkMethodNaming(lines, file);
            checkClassNaming(lines, file);

        } catch (IOException e) {
            getLog().error("Ошибка при чтении файла: " + file.getName(), e);
        }
    }

    private void checkLineLengths(String[] lines, File file) {
        IntStream.range(0, lines.length)
                .filter(i -> lines[i].length() > maxLineLength)
                .forEach(i -> {
                    StringBuilder message = new StringBuilder().append("Строка превышает максимальную длину ").append(maxLineLength).append(" символов");
                    logWarning(message.toString(), file, "Строка " + (i + 1));
                });
    }

    private void checkMethodNaming(String[] lines, File file) {
        Stream.of(lines)
                .filter(line -> line.trim().matches(".*\\b(public|private|protected|static)\\b.*\\(.*\\)"))
                .forEach(line -> extractMethodName(line)
                        .filter(methodName -> Character.isUpperCase(methodName.charAt(0)))
                        .ifPresent(methodName -> {
                            logWarning("Имя метода должно начинаться с маленькой буквы", file, methodName);
                        }));
    }

    private void checkClassNaming(String[] lines, File file) {
        Stream.of(lines)
                .forEach(line -> extractClassName(line)
                        .filter(className -> Character.isLowerCase(className.charAt(0)))
                        .ifPresent(className -> {
                            logWarning("Имя класса должно начинаться с большой буквы", file, className);
                        }));
    }

    private Optional<String> extractMethodName(String line) {
        Pattern pattern = Pattern.compile("\\b(public|private|protected|static)\\s+([a-zA-Z0-9_<>]+)\\s+([a-zA-Z0-9_]+)\\(");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? Optional.of(matcher.group(3)) : Optional.empty();
    }

    private Optional<String> extractClassName(String line) {
        Pattern pattern = Pattern.compile("\\bclass\\b\\s+([a-zA-Z0-9_]+)");
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private void logWarning(String messageTemplate, File file, String detail) {
        String message = new StringBuilder()
                .append(messageTemplate).append(" в файле ").append(file.getName())
                .append(": ").append(detail).toString();
        getLog().warn(message);
    }
}
