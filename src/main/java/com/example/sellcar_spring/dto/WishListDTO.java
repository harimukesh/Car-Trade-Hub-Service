package com.example.sellcar_spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class WishListDTO {
    public Long userId;
    public List<Long> wishList;
}
