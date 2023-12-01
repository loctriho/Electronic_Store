package com.triloc.webapp.electonicstore.service;

import com.triloc.webapp.electonicstore.dto.ProductDTO;
import com.triloc.webapp.electonicstore.dto.ProductSearchResult;
import com.triloc.webapp.electonicstore.model.Product;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Transactional  // Ensure this annotation is present
    public List<ProductDTO> search(String searchTerm) throws InterruptedException {
        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer()
                .startAndWait();
//        List<List<?>> result = searchSession.search( Product.class )
//                .select( f -> f.composite().from(
//                        f.highlight( "product_name" ),
//                        f.highlight( "description" )
//                ).asList() )
//                .where( f -> f.match().fields( "description", "product_name" ).matching( searchTerm ).fuzzy(2) )
//
//                .fetchHits( 20 );
        List<Long> productIds = searchSession.search(Product.class)
                .select(f -> f.id(Long.class))
                .where(f -> f.match()
                        .fields("product_name", "description")
                        .matching(searchTerm)
                      )
                .fetchAllHits();

        // Step 2: Fetch ProductDTOs using the custom JPQL query
        return productRepository.getProductDTOs(productIds);


    }

}
