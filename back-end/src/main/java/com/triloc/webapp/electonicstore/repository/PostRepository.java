package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAll();

}
