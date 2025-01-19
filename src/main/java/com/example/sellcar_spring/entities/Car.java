package com.example.sellcar_spring.entities;


import com.example.sellcar_spring.dto.CarDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    private String year;
    private Boolean sold;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    private Long price;

    private Long pincode;

    private String city;

    private String state;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public CarDTO getCarDTO() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(id);
        carDTO.setName(name);
        carDTO.setBrand(brand);
        carDTO.setType(type);
        carDTO.setTransmission(transmission);
        carDTO.setColor(color);
        carDTO.setYear(year);
        carDTO.setSold(sold);
        carDTO.setDescription(description);
        carDTO.setPrice(price);
        carDTO.setReturnedImg(img);
        carDTO.setPincode(pincode);
        carDTO.setCity(city);
        carDTO.setState(state);
        carDTO.setLocation(location);
        return carDTO;
    }

}
