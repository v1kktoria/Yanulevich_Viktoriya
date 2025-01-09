package senla.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class MethodOverrideFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Проверяем, если есть параметр _method в запросе
        String methodOverride = servletRequest.getParameter("_method");

        // Если _method существует, изменяем метод запроса
        if (methodOverride != null) {
            if ("PUT".equalsIgnoreCase(methodOverride)) {
                httpRequest = new MethodOverrideRequestWrapper(httpRequest, "PUT");
            } else if ("DELETE".equalsIgnoreCase(methodOverride)) {
                httpRequest = new MethodOverrideRequestWrapper(httpRequest, "DELETE");
            }
        }

        // Продолжаем фильтрацию запроса
        filterChain.doFilter(httpRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // Очистка при уничтожении фильтра
    }
}
