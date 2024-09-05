package org.example.tablereservation.controller;

import org.example.tablereservation.model.dto.ReservationDto;
import org.example.tablereservation.model.entity.ReservationEntity;
import org.example.tablereservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    예약하기(*추후 매장이름이 있는지 확인, 날짜 시간 양식대로 입력하게끔 하기 구현)
    @PostMapping("/store")
    public ResponseEntity<String> reservationStore(@RequestBody ReservationDto reservationDto) {
        ReservationEntity reservationEntity = reservationService.reservationStore(
                reservationDto.getPhoneNumber(),
                reservationDto.getStoreName(),
                reservationDto.getDate(),
                reservationDto.getTime());
        return ResponseEntity.ok("success");
    }


//    조건별 매장 예약 조회하기(날짜별/매장별/전체)

    @GetMapping("/list")
    public ResponseEntity<List<ReservationDto>> getReservationsByStoreNameAndDate(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String date){
        List<ReservationDto> reservation = reservationService.getReservations(storeName, date)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservation);

    }

    //    핸드폰 번호로 예약 10분 전 방문 확인
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmVisitByPhoneNumber(@RequestParam String phoneNumber, String storeName) {
        boolean isConfirmed = reservationService.confirmVisitByPhoneNumberAndStoreName(phoneNumber, storeName);
        if (isConfirmed) {
            return ResponseEntity.ok("방문확인 되었습니다^^");
        } else {
            return ResponseEntity.badRequest().body("이 핸드폰 번호로 예약된 내역이 없습니다.");
        }
    }

//    엔티티를 dto로 변환시켜주는 구현(사용자가 입력한 정보만 보이게끔 하기)
    private ReservationDto convertToDto(ReservationEntity reservationEntity) {

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setPhoneNumber((reservationEntity.getPhoneNumber()));
        reservationDto.setStoreName((reservationEntity.getStoreName()));
        reservationDto.setDate((reservationEntity.getDate()));
        reservationDto.setTime((reservationEntity.getTime()));
        return reservationDto;
    }
}
