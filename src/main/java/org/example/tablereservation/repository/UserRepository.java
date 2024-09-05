package org.example.tablereservation.repository;

import org.example.tablereservation.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsById(String id);


}
