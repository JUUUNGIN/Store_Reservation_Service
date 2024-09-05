package org.example.tablereservation.repository;

import org.example.tablereservation.model.entity.ReservationEntity;
import org.example.tablereservation.model.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

//    특정 매장의 날짜별 예약조회
    List<ReservationEntity> findByStoreNameAndDate(String storeName, String date);

//    매장별 예약조회
    List<ReservationEntity> findByStoreName(String storeName);

//    날짜별 예약조회
    List<ReservationEntity> findByDate(String date);

    Optional<ReservationEntity> findFirstByPhoneNumberAndStoreNameOrderByDateAsc(String phoneNumber);


}
