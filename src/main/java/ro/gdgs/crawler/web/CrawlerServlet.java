package ro.gdgs.crawler.web;

import com.google.appengine.api.urlfetch.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * @author Octavian
 * @since 1.0
 */
public class CrawlerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {
            String url = request.getParameter("url");
            URI uri = URI.create(url);
            String result = downloadPage(uri);
            out.print(result);
        } finally {
            out.close();
        }
    }

    private String downloadPage(URI uri) throws IOException {
        StringBuilder page = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                uri.toURL().openStream(), Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                page.append(line);
            }
        }
        return page.toString();
    }

    private String downloadPage2(URI uri) throws IOException {
        URLFetchService service =
                URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = service.fetch(
                new HTTPRequest(uri.toURL(), HTTPMethod.GET));
        return new String(response.getContent(),
                Charset.forName("UTF-8"));
    }
}
