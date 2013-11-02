package ro.gdgs.crawler.db;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Interface used for filtering database results.
 *
 * @param <T>
 * @author hasna
 * @since 1.1
 */
public interface Specification<T> {
    /**
     * Transforms this specification into a predicate.
     *
     * @param root  The starting point, must not be null.
     * @param query The query object to use, must not be null.
     * @param cb    The criteria builder to use, must not be null.
     * @return The created predicate which checks the rules.
     * @throws UnsupportedOperationException If this specification does not support predicate transformation.
     */
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            throws UnsupportedOperationException;
}
