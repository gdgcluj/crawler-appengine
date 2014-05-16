package ro.gdgs.crawler.web.filters;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hasna
 * @since 1.1
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig;
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        userService = UserServiceFactory.getUserService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (!userService.isUserLoggedIn()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return; //break filter chain, requested JSP/servlet will not be executed
        }

        //propagate to next element in the filter chain, ultimately JSP/ servlet gets executed
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        userService = null;
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }
}
