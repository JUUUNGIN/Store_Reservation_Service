package org.example.tablereservation.service;

import org.example.tablereservation.model.entity.ReservationEntity;
import org.example.tablereservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {

        this.reservationRepository = reservationRepository;
    }

    //  예약하기
    public ReservationEntity reservationStore(String phoneNumber, String storeName, String date, String time) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setPhoneNumber(phoneNumber);
        reservationEntity.setStoreName(storeName);
        reservationEntity.setDate(date);
        reservationEntity.setTime(time);

        return reservationRepository.save(reservationEntity);
    }

    //    특정 매장과 날짜의 예약리스트 조회하기
    public List<ReservationEntity> getReservations(String storeName, String date) {
        if (storeName != null && date != null) {
            return reservationRepository.findByStoreNameAndDate(storeName, date);
        } else if (storeName != null) {
            return reservationRepository.findByStoreName(storeName);
        } else if (date != null) {
            return reservationRepository.findByDate(date);
        } else {
            return reservationRepository.findAll();
        }
    }

    //    예약시간 10분 전 확인하기(핸드폰 번호로 본인 확인)
    public boolean confirmVisitByPhoneNumberAndStoreName(String phoneNumber, String storeName) {
        Optional<ReservationEntity> reservationOptional = reservationRepository.findFirstByPhoneNumberAndStoreNameOrderByDateAsc(phoneNumber);

        if (reservationOptional.isPresent()) {                                   // 예약이 있다면
            ReservationEntity nearestReservation = reservationOptional.get();    // 예약 객체 가져옴

            LocalDateTime reservationTime = LocalDateTime.parse(nearestReservation.getDate() + "T" + nearestReservation.getTime()); //(T : 날짜-시간 표기법에서 날짜와 시간을 구분하는 문자)
            LocalDateTime now = LocalDateTime.now();

//            예약 시간이 현재 시간으로 부터 10분 전인지 확인
            if (reservationTime.isAfter(now) && reservationTime.minusMinutes(10).isBefore(now)) {
                return true;
            }
        }
        return false;
    }

}
