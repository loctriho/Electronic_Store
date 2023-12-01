package com.triloc.webapp.electonicstore.service;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class IndexingService {

    private final EntityManager entityManager;

    public IndexingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void initiateIndexing() throws InterruptedException {
        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer()
                .startAndWait(); // Index all entities
    }
}
