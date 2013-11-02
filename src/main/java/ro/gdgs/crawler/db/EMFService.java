package ro.gdgs.crawler.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author hasna
 * @since 1.1
 */
public class EMFService {
    private static final EntityManagerFactory emfInstance = Persistence
            .createEntityManagerFactory("transactions-optional");

    private EMFService() {
    }

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}