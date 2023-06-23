package com.r2s.mockproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.r2s.mockproject.enumrate.OrderStatus;
import com.r2s.mockproject.enumrate.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "deliver_time")
    private Date deliverTime;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonBackReference
    private Address address;
}
