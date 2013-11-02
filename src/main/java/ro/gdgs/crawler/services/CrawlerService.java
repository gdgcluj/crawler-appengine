package ro.gdgs.crawler.services;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.urlfetch.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ro.gdgs.crawler.domain.Page;
import ro.gdgs.crawler.repositories.GaePageRepository;
import ro.gdgs.crawler.repositories.JpaRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author hasna
 * @since 1.1
 */
public class CrawlerService implements Serializable {
    private JpaRepository pageRepository;
    private URLFetchService service;

    public CrawlerService() {
        pageRepository = new GaePageRepository();
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

    private String downloadPageOld(URI uri) throws IOException {
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

    public void savePage(String url, String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("title");
        String title = elements.get(0).text();
        elements = document.select("meta[name=description]");
        String description = elements.get(0).attr("content");

        Page page = new Page();
        page.setTitle(title);
        page.setDescription(description);
//        page.setCrawled(true);
        page.setUrl(url);
        //pentru testarea update-ului, iei id-ul din Datastore View coloana ID
//        page.setId(5629499534213120L);
        pageRepository.save(page);
    }

    public List<Page> getPages() {
        return pageRepository.findAll();
    }

    public void closeRepository() {
        pageRepository.close();
    }

    public void updatePage(String id, String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("title");
        String title = elements.get(0).text();
        elements = document.select("meta[name=description]");
        String description = elements.get(0).attr("content");
        Page page = new Page();
        page.setId(Long.parseLong(id));
        page.setTitle(title);
        page.setDescription(description);
        page.setCrawled(true);
        pageRepository.save(page);
    }
}
