package ro.gdgs.crawler.web.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

/**
 * Wraps the ServletRequest to remove the "If-Modified-Since" http header
 *
 * @author Derek Berube, WildstarTechnologies, LLC.
 */
public class HttpModifiedSinceRequestWrapper extends HttpServletRequestWrapper {
    private static final String _CLASS = HttpModifiedSinceRequestWrapper.class.getName();
    private static final Logger logger = Logger.getLogger(_CLASS);

    /**
     * @param request
     */
    public HttpModifiedSinceRequestWrapper(HttpServletRequest request) {
        super(request);
        logger.entering(_CLASS, "HttpModifiedSinceRequestWrapper");
        logger.exiting(_CLASS, "HttpModifiedSinceRequestWrapper");
    }

    @Override
    /**
     * Returns the header provided it is not the "If-Modified-Since" header.
     */
    public String getHeader(String name) {
        logger.entering(_CLASS, "getHeader(String)", name);
        String header = null;
        if (!"If-Modified-Since".equals(name)) {
            header = super.getHeader(name);
        } // END if (!"If-Modified-Since".equals(name))
        logger.exiting(_CLASS, "getHeader(String)", header);
        return header;
    }

    @SuppressWarnings("rawtypes")
    @Override
    /**
     * Returns headers stripping out the "If-Modified-Since" header if
     * present.
     */
    public Enumeration getHeaderNames() {
        logger.entering(_CLASS, "getHeaderNames()");
        String name;

        List<String> names = new ArrayList<>();
        Enumeration<?> enu = super.getHeaderNames();

        while (enu.hasMoreElements()) {
            name = enu.nextElement().toString();
            if (!"If-Modified-Since".equals(name)) {
                names.add(name);
            } // END if (!"If-Modified-Since".equals(name))
        } // END while (enu.hasMoreElements())
        Enumeration headerNames = Collections.enumeration(names);
        logger.exiting(_CLASS, "getHeaderNames()", headerNames);
        return headerNames;
    }
}
