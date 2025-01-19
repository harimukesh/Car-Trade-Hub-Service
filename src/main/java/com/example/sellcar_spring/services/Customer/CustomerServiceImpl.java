package com.example.sellcar_spring.services.Customer;


import com.example.sellcar_spring.dto.*;
import com.example.sellcar_spring.entities.Bid;
import com.example.sellcar_spring.entities.Car;
import com.example.sellcar_spring.entities.User;
import com.example.sellcar_spring.entities.WishList;
import com.example.sellcar_spring.enums.BidStatus;
import com.example.sellcar_spring.repositories.BidRepository;
import com.example.sellcar_spring.repositories.CarRepository;
import com.example.sellcar_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;
    private final BidRepository bidRepository;
    private final com.example.sellcar_spring.repositories.wishListRepository wishListRepository;

    @Override
    public boolean createCar(CarDTO carDTO) throws IOException {

        Optional<User> user = userRepository.findById(carDTO.getUserId());
        if(user.isPresent()) {
            Car car = new Car();
            Car newCar = setCarDTO(car, carDTO);
            newCar.setUser(user.get());
            carRepository.save(newCar);
            return true;
        }
        return false;
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarById(Long id)  {
        return carRepository.findById(id).map(Car::getCarDTO).orElse(null);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public boolean updateCar(Long id, CarDTO carDTO) throws IOException {
        Optional<Car> optionalCarDTO = carRepository.findById(id);
        if(optionalCarDTO.isPresent()) {
            Car car = optionalCarDTO.get();
             Car updateCar = setCarDTO(car, carDTO);
            carRepository.save(updateCar);
            return true;
        }
        return false;
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
    public List<CarDTO> getMyCars(Long userId) {
        return carRepository.findAllByUserId(userId).stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public boolean bidACar(BidDTO bidDTO) {
        Optional<Car> optionalCar = carRepository.findById(bidDTO.getCarId());
        Optional<User> optionalUser = userRepository.findById(bidDTO.getUserId());

        if(optionalCar.isPresent() && optionalUser.isPresent()){
            Bid bid = new Bid();
            bid.setCar(optionalCar.get());
            bid.setUser(optionalUser.get());
            bid.setPrice(bidDTO.getPrice());
            bid.setBidStatus(BidStatus.PENDING);
            bidRepository.save(bid);
            return true;
        }
        return false;
    }

    @Override
    public List<BidDTO> getBidsByUserId(Long userId) {
        return bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
    }

    @Override
    public List<BidDTO> getBidsByCarId(Long carId) {
        return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
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

    @Override
    public List<CarDTO> getCarsByPincode(Long pincode) {
        return carRepository.findAllByPincode(pincode).stream().map(Car::getCarDTO).collect(Collectors.toList());
    }

    @Override
    public WishListDTO getWishListByUserId(Long userId) {
        WishListDTO wishListDTO = new WishListDTO();
         List<WishList> wishList = wishListRepository.findAllByUserId(userId);
         ArrayList<Long> wishListIds = new ArrayList<>();
         for(WishList wishItem : wishList){
             wishListIds.add(wishItem.getWishedCarId());
         }
        wishListDTO.setUserId(userId);
         wishListDTO.setWishList(wishListIds);

        return wishListDTO;
    }

    public Car setCarDTO(Car car ,  CarDTO carDTO) throws IOException {
            car.setName(carDTO.getName());
            car.setBrand(carDTO.getBrand());
            car.setColor(carDTO.getColor());
            car.setYear(carDTO.getYear());
            car.setPrice(carDTO.getPrice());
            car.setTransmission(carDTO.getTransmission());
            car.setSold(false);
            car.setType(carDTO.getType());
            car.setPincode(carDTO.getPincode());
            car.setLocation( carDTO.getLocation());
            car.setCity(carDTO.getCity());
            car.setState(carDTO.getState());
            car.setDescription(carDTO.getDescription());
            if(carDTO.getImg() != null) {
                car.setImg(carDTO.getImg().getBytes());
            }
            return car;
    }
}
