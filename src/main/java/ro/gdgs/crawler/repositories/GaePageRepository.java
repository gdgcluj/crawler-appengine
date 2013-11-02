package ro.gdgs.crawler.repositories;

import com.google.appengine.api.datastore.*;
import ro.gdgs.crawler.db.Specification;
import ro.gdgs.crawler.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hasna
 * @since 1.0.128-SNAPSHOT
 */
public class GaePageRepository implements JpaRepository {
    DatastoreService datastore;

    public GaePageRepository() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    public void save(Page page) {
        Entity entity;
        try {
            Key key = KeyFactory.createKey("pages", page.getId());
            entity = datastore.get(key);
        } catch (Exception e) {
            entity = new Entity("pages");
        }
        entity.setProperty("url", page.getUrl());
        entity.setProperty("title", page.getTitle());
        entity.setProperty("description", page.getDescription());
        entity.setProperty("crawled", page.isCrawled());

        datastore.put(entity);
    }

    @Override
    public List<Page> findAll(Specification<Page> specification) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Page> findAll() {
        // Use class Query to assemble a query
        Query q = new Query("pages");
        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
        List<Page> result = new ArrayList<>();
        for (Entity entity : list) {
            Page page = new Page();
            page.setId(entity.getKey().getId());
            page.setTitle(entity.getProperty("title").toString());
            page.setDescription(entity.getProperty("description").toString());
            page.setUrl(entity.getProperty("url").toString());
            page.setCrawled((boolean) entity.getProperty("crawled"));
            result.add(page);
        }
        return result;
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
