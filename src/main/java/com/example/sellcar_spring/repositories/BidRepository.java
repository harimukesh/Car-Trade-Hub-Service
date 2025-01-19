package com.example.sellcar_spring.repositories;

import com.example.sellcar_spring.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByUserId(Long userId);

    List<Bid> findAllByCarId(Long carId);
}
