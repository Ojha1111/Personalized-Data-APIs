package com.Tredence.PersonalizedDataAPI.repository;

import com.Tredence.PersonalizedDataAPI.entity.ProductMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMetadataRepository extends JpaRepository<ProductMetadataEntity, String> {
}
