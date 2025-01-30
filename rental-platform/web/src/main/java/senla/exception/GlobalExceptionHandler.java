package senla.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        prepareErrorPage(model, 500, "Произошла ошибка: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("<br>"));
        prepareErrorPage(model, 400, "Ошибка валидации:<br>" + errorMessage);
        return "error";
    }

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException ex, Model model) {
        prepareErrorPage(model, ex.getStatusCode(), ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DatabaseException.class)
    public String handleDatabaseException(DatabaseException ex, Model model) {
        prepareErrorPage(model, ex.getStatusCode(), ex.getMessage());
        return "error";
    }

    private void prepareErrorPage(Model model, int statusCode, String message) {
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("message", message);
    }
}
