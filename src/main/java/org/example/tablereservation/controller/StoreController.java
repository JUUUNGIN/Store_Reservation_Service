package org.example.tablereservation.controller;

import org.example.tablereservation.model.dto.StoreDto;
import org.example.tablereservation.model.dto.StoreRegistrationDto;
import org.example.tablereservation.model.entity.StoreEntity;
import org.example.tablereservation.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;

    }

    //  매장등록(파트너 회원가입 겸 매장 등록)
    @PostMapping("/register")
    public ResponseEntity<String> registerStore(@RequestBody StoreRegistrationDto storeRegistrationDto) {
        StoreEntity storeEntity = storeService.registerStore(storeRegistrationDto.getStoreName(), storeRegistrationDto.getLocation(), storeRegistrationDto.getDescription(), storeRegistrationDto.getPassword());
        return ResponseEntity.ok("success");
    }

    //    매장 리스트 조회
    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores() {
        List<StoreDto> stores = storeService.getAllStores()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stores);
    }


    //  특정 매장 상세 조회(매장은 이름으로 검색)
    @GetMapping("/{storeName}")
    public ResponseEntity<StoreDto> getStoreDetailsByName(@PathVariable String storeName) {
        StoreEntity storeEntity = storeService.getStoreDetailsByName(storeName);
        StoreDto storeDto = convertToDto(storeEntity);
        return ResponseEntity.ok(storeDto);
    }

//    엔티티를 dto로 변환시키는 과정(비밀번호 노출 안되게 설정)
    private StoreDto convertToDto(StoreEntity storeEntity) {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreName(storeEntity.getStoreName());
        storeDto.setLocation(storeEntity.getLocation());
        storeDto.setDescription(storeEntity.getDescription());
        return storeDto;
    }

}
