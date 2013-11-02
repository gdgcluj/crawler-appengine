package ro.gdgs.crawler.web;

import ro.gdgs.crawler.services.CrawlerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

/**
 * @author Octavian
 * @since 1.1
 */
public class TaskServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        CrawlerService service = null;
        try {
            service = new CrawlerService();
            String id = request.getParameter("id");
            String url = request.getParameter("url");
            URI uri = URI.create(url);
            String html = service.downloadPage(uri);
            service.updatePage(id, html);
            out.write("Am salvat cu succes pagina.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } finally {
            if (service != null) {
                service.closeRepository();
            }
            out.close();
        }
    }
}
