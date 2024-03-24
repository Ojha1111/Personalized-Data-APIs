package com.Tredence.PersonalizedDataAPI.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "shopper")
@Data
public class ShopperEntity {
    @Id
    private String shopperId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shopper_id")
    private List<ShelfItemEntity> shelf;
}