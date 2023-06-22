package com.r2s.mockproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_line_item")
public class CartLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false, columnDefinition = "smallint unsigned check(quantity > 0) not null")
    private int quantity;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_product_id")
    private VariantProduct variantProduct;




}
