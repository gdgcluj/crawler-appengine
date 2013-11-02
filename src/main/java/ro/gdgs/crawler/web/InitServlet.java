package ro.gdgs.crawler.web;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import ro.gdgs.crawler.domain.Page;
import ro.gdgs.crawler.services.CrawlerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Octavian
 * @since 1.1
 */
public class InitServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        CrawlerService service = null;
        try {
            Queue queue = QueueFactory.getQueue("crawler-queue");
            service = new CrawlerService();
            List<Page> pages = service.getPages();
            for (Page page : pages) {
                if (!page.isCrawled()) {
                    queue.addAsync(TaskOptions.Builder
                            .withUrl("/crawl")
                            .param("url", page.getUrl()));
                }
            }

            out.write("Am salvat cu succes pagina.");
        } finally {
            if (service != null) {
                service.closeRepository();
            }
            out.close();
        }
    }
}
