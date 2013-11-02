package ro.gdgs.crawler.repositories;

import ro.gdgs.crawler.db.EMFService;
import ro.gdgs.crawler.db.Specification;
import ro.gdgs.crawler.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author hasna
 * @since 1.1
 */
public class PageRepository {
    private EntityManager entityManager;

    public PageRepository() {
        this.entityManager = EMFService.get().createEntityManager();
    }

    public void save(Page entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        }
    }

    public List<Page> findAll(Specification<Page> specification) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Page> q = cb.createQuery(Page.class);
        Root<Page> root = q.from(Page.class);

        q.where(specification.toPredicate(root, q, cb));

        return entityManager.createQuery(q).getResultList();
    }

    public List<Page> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Page> q = cb.createQuery(Page.class);
        Root<Page> root = q.from(Page.class);
        q.select(root);

        return entityManager.createQuery(q).getResultList();
    }

    public void flush() {
        entityManager.flush();
    }

    public void close() {
        entityManager.close();
    }
}
