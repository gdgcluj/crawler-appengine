package ro.gdgs.crawler.repositories;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import ro.gdgs.crawler.db.Specification;
import ro.gdgs.crawler.domain.Page;

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
        Entity employee = new Entity("Page");

        employee.setProperty("url", page.getUrl());
        employee.setProperty("title", page.getTitle());
        employee.setProperty("description", page.getDescription());
        employee.setProperty("title", page.getTitle());


    }

    @Override
    public List<Page> findAll(Specification<Page> specification) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Page> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
