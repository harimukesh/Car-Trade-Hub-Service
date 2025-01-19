package com.example.sellcar_spring.dto;

import com.example.sellcar_spring.enums.BidStatus;
import lombok.Data;

@Data
public class BidDTO {

    private Long id;

    private Long price;

    private BidStatus bidStatus;

    private Long userId;

    private Long carId;

    private String username;

    private String carName;

    private String carBrand;

    private String email;

    private String sellerName;
}
