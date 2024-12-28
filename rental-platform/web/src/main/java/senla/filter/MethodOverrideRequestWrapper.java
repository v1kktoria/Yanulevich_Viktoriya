package senla.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MethodOverrideRequestWrapper  extends HttpServletRequestWrapper {

    private final String method;

    public MethodOverrideRequestWrapper(HttpServletRequest request, String method) {
        super(request);
        this.method = method;
    }

    @Override
    public String getMethod() {
        return method;
    }
}

