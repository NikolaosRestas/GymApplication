package com.example.demo4.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "kind")
    private String kind;

    @Column(name = "duration")
    private String duration;

    @Column(name = "price")
    private String price;

    @ManyToMany
    @JoinColumn(name = "customer_key")
    private List<Customer> customers;

}
