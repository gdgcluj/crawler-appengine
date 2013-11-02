package ro.gdgs.crawler.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Replaces the If-Modified-Since header until Google App Engine bug 8415 is
 * resolved.
 * @see //code.google.com/p/googleappengine/issues/detail?id=8415
 *
 * @author Derek Berube, Wildstar Technologies
 *
 */
public class HttpIfModifiedSinceFix implements Filter {
    private static final String _CLASS = HttpIfModifiedSinceFix.class.getName();
    private static final Logger logger = Logger.getLogger(_CLASS);

    /**
     *  Called by the web container to indicate to a filter that it is being
     *  taken out of service.
     */
    @Override
    public void destroy() {
        logger.entering(_CLASS,"destroy()");
        logger.exiting(_CLASS,"destroy()");
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.entering(_CLASS,
                "doFilter(ServletRequest,ServletResponse,FilterChain)",
                new Object[] {request,response,chain});
        HttpServletRequest httpRequest=null;
        HttpServletRequestWrapper requestWrapper=null;

        httpRequest=(HttpServletRequest) request;
        requestWrapper=new HttpModifiedSinceRequestWrapper(httpRequest);
        chain.doFilter(requestWrapper, response);

        logger.exiting(_CLASS,
                "doFilter(ServletRequest,ServletResponse,FilterChain)");
    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * placed into service.
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        logger.entering(_CLASS,"init(FilterConfig)",config);
        logger.exiting(_CLASS,"init(FilterConfig)");
    }
}
