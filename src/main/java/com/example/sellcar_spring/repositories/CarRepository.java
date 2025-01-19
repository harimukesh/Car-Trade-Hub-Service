package com.example.sellcar_spring.repositories;

import com.example.sellcar_spring.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByUserId(Long userId);

    int countByUserId(Long userId);

    int countByUserIdAndSoldTrue(Long userId);

    List<Car> findAllByPincode(Long pincode);
}
