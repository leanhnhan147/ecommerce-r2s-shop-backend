package com.r2s.mockproject.repository;

import com.r2s.mockproject.entity.VariantProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Long> {
    Optional<VariantProduct> findByName(String name);
}
