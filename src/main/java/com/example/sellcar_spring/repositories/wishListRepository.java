package com.example.sellcar_spring.repositories;

import com.example.sellcar_spring.entities.User;
import com.example.sellcar_spring.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface wishListRepository extends JpaRepository<WishList,Long> {

    List<WishList> findAllByUserId(Long userId);
}
