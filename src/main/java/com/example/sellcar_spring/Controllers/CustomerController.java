package com.example.sellcar_spring.Controllers;


import com.example.sellcar_spring.dto.*;
import com.example.sellcar_spring.services.Customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/car")    /*@ModelAttribute for img in post payload*/
    public ResponseEntity<?> addCar(@ModelAttribute CarDTO carDTO) throws IOException{
        boolean success = customerService.createCar(carDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(path = "/cars")
    public List<CarDTO> getAllCars(){
        return customerService.getAllCars();
    }

    @GetMapping(path = "/car/{id}")
    public CarDTO getCarByID(@PathVariable Long id) {
        return customerService.getCarById(id);
    }

    @DeleteMapping(path = "/car/{id}")
    public void deleteCarByID(@PathVariable Long id) {
        customerService.deleteCarById(id);
    }

    @PutMapping(path = "/car/{id}")    /*@ModelAttribute for img in post payload*/
    public ResponseEntity<?> updateCar(@PathVariable Long id, @ModelAttribute CarDTO carDTO) throws IOException {
        boolean success = customerService.updateCar(id , carDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping(path = "/car/search")
    public List<CarDTO> searchCar(@RequestBody SearchCarDTO searchCarDTO){
        return customerService.searchCar(searchCarDTO);
    }

    @GetMapping(path = "/my-cars/{userId}")
    public List<CarDTO> getMyCars(@PathVariable Long userId){
        return customerService.getMyCars(userId);
    }


    @PostMapping(path = "/car/bid")
    public ResponseEntity<?> bidACar(@RequestBody BidDTO bidDTO){
        boolean success = customerService.bidACar(bidDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(path = "/car/bids/{userId}")
    public List<BidDTO> getBidsByID(@PathVariable Long userId){
        return customerService.getBidsByUserId(userId);
    }

    @GetMapping(path = "/car/{carId}/bids")
    public List<BidDTO> getBidsCarByID(@PathVariable Long carId){
        return customerService.getBidsByCarId(carId);
    }

    @GetMapping(path = "/car/bid/{bidId}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable Long bidId, @PathVariable String status){
        boolean success = customerService.changeBidStatus(bidId, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/car/{userId}/analytics")
    public AnalyticsDTO getAnalyticsByUserID(@PathVariable Long userId){
        return customerService.getAnalyticsById(userId);
    }

    @GetMapping(path = "/cars/search/{pincode}")
    public List<CarDTO> getCarsByPincode(@PathVariable Long pincode){
        return customerService.getCarsByPincode(pincode);
    }

    @GetMapping(path = "/cars/my-wishlist/{userId}")
    public WishListDTO getWishListByUserID(@PathVariable Long userId){
        return customerService.getWishListByUserId(userId);
    }




}
