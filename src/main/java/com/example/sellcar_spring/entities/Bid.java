package com.example.sellcar_spring.entities;

import com.example.sellcar_spring.dto.BidDTO;
import com.example.sellcar_spring.enums.BidStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long price;

    private BidStatus bidStatus;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "car_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;


    public BidDTO getBidDTO(){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setId(id);
        bidDTO.setPrice(price);
        bidDTO.setBidStatus(bidStatus);
        bidDTO.setCarId(car.getId());
        bidDTO.setCarName(car.getName());
        bidDTO.setCarBrand(car.getBrand());
        bidDTO.setUsername(user.getName());
        bidDTO.setEmail(user.getEmail());
        bidDTO.setSellerName(car.getUser().getName());
        return bidDTO;
    }

}
