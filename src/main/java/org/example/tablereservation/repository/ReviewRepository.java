package org.example.tablereservation.repository;

import org.example.tablereservation.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByPhoneNumber(String phoneNumber);
    List<ReviewEntity> findByStoreName(String store);

    Optional<ReviewEntity> findByPhoneNumberAndStoreName(String phoneNumber, String storeName);



}
