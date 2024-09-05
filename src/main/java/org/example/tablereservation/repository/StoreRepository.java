package org.example.tablereservation.repository;

import org.example.tablereservation.model.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByStoreName(String storeName);

}
