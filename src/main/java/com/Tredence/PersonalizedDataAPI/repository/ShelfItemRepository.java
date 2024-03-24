package com.Tredence.PersonalizedDataAPI.repository;


import com.Tredence.PersonalizedDataAPI.entity.ShelfItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfItemRepository extends JpaRepository<ShelfItemEntity, Long> {
}
