package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}

