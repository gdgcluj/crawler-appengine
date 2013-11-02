package ro.gdgs.crawler.repositories;

import ro.gdgs.crawler.db.Specification;
import ro.gdgs.crawler.domain.Page;

import java.util.List;

/**
 * @author hasna
 * @since 1.0.128-SNAPSHOT
 */
public interface JpaRepository extends AutoCloseable {
    void save(Page entity);

    List<Page> findAll(Specification<Page> specification);

    List<Page> findAll();

    void flush();

    void close();
}
