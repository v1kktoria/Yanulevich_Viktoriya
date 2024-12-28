package senla.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import senla.exception.EntityNotFoundException;
import senla.exception.ServiceException;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            handleException(httpResponse, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServiceException e) {
            handleException(httpResponse, e.getStatusCode(), e.getMessage());
        } catch (NumberFormatException e) {
            handleException(httpResponse, HttpServletResponse.SC_BAD_REQUEST, "Неверный формат числового параметра: " + e.getMessage());
        } catch (RuntimeException e) {
            handleException(httpResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера: " + e.getMessage());
        } catch (Exception e) {
            handleException(httpResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка");
        }
    }

    private void handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("text/html");
        response.getWriter().write(buildErrorPage(statusCode, message));
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
                "<p><strong>Описание ошибки:</strong> " + message + "</p>" +
                "</body>" +
                "</html>";
    }


}
