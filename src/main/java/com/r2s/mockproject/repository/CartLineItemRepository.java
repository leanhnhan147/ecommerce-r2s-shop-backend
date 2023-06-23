package com.r2s.mockproject.repository;

import com.r2s.mockproject.entity.CartLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {
}
