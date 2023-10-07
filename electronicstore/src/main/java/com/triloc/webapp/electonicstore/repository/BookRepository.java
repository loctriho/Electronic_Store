package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
