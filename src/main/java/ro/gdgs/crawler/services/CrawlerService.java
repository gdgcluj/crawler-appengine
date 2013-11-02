package ro.gdgs.crawler.services;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.urlfetch.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ro.gdgs.crawler.domain.Page;
import ro.gdgs.crawler.repositories.JpaRepository;
import ro.gdgs.crawler.repositories.PageRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * @author hasna
 * @since 1.0.128-SNAPSHOT
 */
public class CrawlerService {
    private JpaRepository pageRepository;
    private URLFetchService service;

    public CrawlerService() {
        pageRepository = new PageRepository();
        service = URLFetchServiceFactory.getURLFetchService();
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

    }

    public String downloadPage(URI uri) throws IOException {
        HTTPResponse response = service.fetch(
                new HTTPRequest(uri.toURL(), HTTPMethod.GET));
        return new String(response.getContent(),
                Charset.forName("UTF-8"));
    }

    public void savePage(String url, String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("title");
        String title = elements.get(0).text();
        elements = document.select("meta[name=description]");
        String description = elements.get(0).attr("content");

        Page page = new Page();
        page.setTitle(title);
        page.setDescription(description);
        page.setCrawled(true);
        page.setUrl(url);
        pageRepository.save(page);

    }

    public void closeRepository(){
        pageRepository.close();
    }

}
