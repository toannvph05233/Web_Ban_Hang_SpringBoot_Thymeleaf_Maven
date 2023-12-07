package com.example.demo.models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String avatar;
    private String description;
    private int amount;
    private long commentCount;

    @Transient
    private MultipartFile image;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private TypeProduct typeProduct;
}
