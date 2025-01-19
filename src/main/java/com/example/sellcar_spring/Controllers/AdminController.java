package com.example.sellcar_spring.Controllers;


import com.example.sellcar_spring.dto.AnalyticsDTO;
import com.example.sellcar_spring.dto.BidDTO;
import com.example.sellcar_spring.dto.CarDTO;
import com.example.sellcar_spring.dto.SearchCarDTO;
import com.example.sellcar_spring.repositories.CarRepository;
import com.example.sellcar_spring.services.Admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/admin")
@CrossOrigin("*")
public class AdminController {


  private final AdminService adminService;

    @GetMapping(path = "/cars")
    public List<CarDTO> getAllCars(){
        return adminService.getAllCars();
    }

    @GetMapping(path = "/car/{id}")
    public CarDTO getCarByID(@PathVariable Long id) {
        return adminService.getCarById(id);
    }

    @DeleteMapping(path = "/car/{id}")
    public void deleteCarByID(@PathVariable Long id) {
        adminService.deleteCarById(id);
    }

    @PostMapping(path = "/car/search")
    public List<CarDTO> searchCar(@RequestBody SearchCarDTO searchCarDTO){
        return adminService.searchCar(searchCarDTO);
    }

    @GetMapping(path = "/car/bids/{userId}")
    public List<BidDTO> getBidsByID(@PathVariable Long userId){
        return adminService.getBidsByUserId(userId);
    }

    @GetMapping(path = "/car/bid/{bidId}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable Long bidId, @PathVariable String status){
       boolean success = adminService.changeBidStatus(bidId, status);
       if(success) return ResponseEntity.ok().build();
       return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/car/{userId}/analytics")
    public AnalyticsDTO getAnalyticsByUserID(@PathVariable Long userId){
        return adminService.getAnalyticsById(userId);
    }


}
