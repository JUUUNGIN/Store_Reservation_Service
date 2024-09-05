package org.example.tablereservation.service;

import org.example.tablereservation.model.entity.StoreEntity;
import org.example.tablereservation.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StoreService(StoreRepository storeRepository, PasswordEncoder passwordEncoder) {
        this.storeRepository = storeRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    매장 등록
    public StoreEntity registerStore(String storeName, String location, String description, String password) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName(storeName);
        storeEntity.setLocation(location);
        storeEntity.setDescription(description);
        storeEntity.setPassword(passwordEncoder.encode(password));

        return storeRepository.save(storeEntity);
    }

//    매장 리스트 조회
    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll();
    }
//    storeName으로 매장 상세 정보 조회
    public StoreEntity getStoreDetailsByName(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new IllegalArgumentException("store not found with store name: " + storeName));
    }

}
