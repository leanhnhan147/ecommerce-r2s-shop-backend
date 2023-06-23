package com.r2s.mockproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "variant_product")
public class VariantProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant_product_name", nullable = false)
    private String name;

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "storage_capacity")
    private String storageCapacity;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private String weight;

    @Column(name = "price")
    private double price;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    @OneToMany(mappedBy = "variantProduct")
    @JsonManagedReference
    private List<CartLineItem> cartLineItems;

}

