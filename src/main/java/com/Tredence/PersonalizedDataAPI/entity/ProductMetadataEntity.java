package com.Tredence.PersonalizedDataAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "product_metadata")
@Data
public class ProductMetadataEntity {
    @Id
    private String productId;
    private String category;
    private String brand;
}