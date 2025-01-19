package com.example.sellcar_spring.services.Admin;


import com.example.sellcar_spring.dto.AnalyticsDTO;
import com.example.sellcar_spring.dto.BidDTO;
import com.example.sellcar_spring.dto.CarDTO;
import com.example.sellcar_spring.dto.SearchCarDTO;

import java.util.List;

public interface AdminService {
    List<CarDTO> getAllCars();

    CarDTO getCarById(Long id);

    void deleteCarById(Long id);

    List<CarDTO> searchCar(SearchCarDTO searchCarDTO);

    List<BidDTO> getBidsByUserId(Long id);

    boolean changeBidStatus(Long id, String status);

    AnalyticsDTO getAnalyticsById(Long id);
}
