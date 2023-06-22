package com.r2s.mockproject.repository;

import com.r2s.mockproject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
