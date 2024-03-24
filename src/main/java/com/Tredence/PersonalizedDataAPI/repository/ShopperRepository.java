package com.Tredence.PersonalizedDataAPI.repository;

import com.Tredence.PersonalizedDataAPI.entity.ShopperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopperRepository extends JpaRepository<ShopperEntity, String> {
}
