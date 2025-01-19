package com.example.sellcar_spring.dto;


import com.example.sellcar_spring.enums.UserRole;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Setter
@Data
public class UserDTO {
    private  Long id;
    private String name;
    private String email;
    private UserRole userRole;
    private List<Long> wishList;
}
