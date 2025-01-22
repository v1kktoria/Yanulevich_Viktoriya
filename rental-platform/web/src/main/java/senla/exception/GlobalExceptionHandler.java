package senla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        String errorPage = buildErrorPage(500, "Произошла ошибка: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(errorPage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("<br>"));
        String errorPage = buildErrorPage(400, "Ошибки валидации:<br>" + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(errorPage);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        String errorPage = buildErrorPage(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode())
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(errorPage);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        String errorPage = buildErrorPage(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode())
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(errorPage);
    }

    private String buildErrorPage(int statusCode, String message) {
        return "<!DOCTYPE html>" +
                "<html lang=\"ru\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Ошибка</title>" +
                "</head>" +
                "<body>" +
                "<h1>Ошибка " + statusCode + "</h1>" +
                "<p><strong>Описание ошибки:</strong><br>" + message + "</p>" +
                "</body>" +
                "</html>";
    }
}
