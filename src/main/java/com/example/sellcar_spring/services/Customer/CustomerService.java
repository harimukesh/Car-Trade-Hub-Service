package com.example.sellcar_spring.services.Customer;

import com.example.sellcar_spring.dto.*;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    boolean createCar(CarDTO carDTO) throws IOException;

    List<CarDTO> getAllCars();

    CarDTO getCarById(Long id);

    void deleteCarById(Long id);

    boolean updateCar(Long id , CarDTO carDTO) throws IOException;

    List<CarDTO> searchCar(SearchCarDTO searchCarDTO);

    List<CarDTO> getMyCars(Long id);

    boolean bidACar(BidDTO bidDTO);

    List<BidDTO> getBidsByUserId(Long id);

    List<BidDTO> getBidsByCarId(Long id);

    boolean changeBidStatus(Long id, String status);

    AnalyticsDTO getAnalyticsById(Long userId);

    List<CarDTO> getCarsByPincode(Long pincode);

    WishListDTO getWishListByUserId(Long userId);
}
