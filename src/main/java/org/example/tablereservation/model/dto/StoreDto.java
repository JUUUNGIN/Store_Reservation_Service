package org.example.tablereservation.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {

//매장 조회용 DTO(password x)
    private String storeName;
    private String location;
    private String description;


}
