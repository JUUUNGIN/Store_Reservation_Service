package org.example.tablereservation.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRegistrationDto {

// 매장 등록용 DTO
    private String storeName;
    private String location;
    private String description;
    private String password;


}
