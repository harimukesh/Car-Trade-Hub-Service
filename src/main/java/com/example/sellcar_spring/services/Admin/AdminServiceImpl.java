package com.example.sellcar_spring.services.Admin;

import com.example.sellcar_spring.dto.AnalyticsDTO;
import com.example.sellcar_spring.dto.BidDTO;
import com.example.sellcar_spring.dto.CarDTO;
import com.example.sellcar_spring.dto.SearchCarDTO;
import com.example.sellcar_spring.entities.Bid;
import com.example.sellcar_spring.entities.Car;
import com.example.sellcar_spring.enums.BidStatus;
import com.example.sellcar_spring.repositories.BidRepository;
import com.example.sellcar_spring.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


    private final CarRepository carRepository;

    private final BidRepository bidRepository;


    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarById(Long id) {
        return carRepository.findById(id).map(Car::getCarDTO).orElse(null);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarDTO> searchCar(SearchCarDTO searchCarDTO) {
        Car car = new Car();
        car.setType(searchCarDTO.getType());
        car.setBrand(searchCarDTO.getBrand());
        car.setTransmission(searchCarDTO.getTransmission());
        car.setColor(searchCarDTO.getColor());

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);
        List<Car> cars = carRepository.findAll(carExample);
        return cars.stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public List<BidDTO> getBidsByUserId(Long userId) {
        return  bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
    }

    @Override
    public boolean changeBidStatus(Long id, String status) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        if (optionalBid.isPresent()) {
            Bid bid = optionalBid.get();
            if(status.equals("Approve")) {
                bid.setBidStatus(BidStatus.APPROVED);
            }else{
                bid.setBidStatus(BidStatus.REJECTED);
            }
            bidRepository.save(bid);
            return true;
        }
        return false;
    }

    @Override
    public AnalyticsDTO getAnalyticsById(Long userId) {
        AnalyticsDTO analyticsDTO = new AnalyticsDTO();
        analyticsDTO.setTotalOfCars(carRepository.countByUserId(userId));
        analyticsDTO.setSoldCars(carRepository.countByUserIdAndSoldTrue(userId));
        return analyticsDTO;
    }


}
