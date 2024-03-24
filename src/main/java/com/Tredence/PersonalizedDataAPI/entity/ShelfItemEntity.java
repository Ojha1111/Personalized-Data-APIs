package com.Tredence.PersonalizedDataAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shelf_item")
@Data
public class ShelfItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private double relevancyScore;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_metadata_id")
    private ProductMetadataEntity productMetadata;
}