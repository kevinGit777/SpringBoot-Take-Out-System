package com.myProject.reggie.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //name for dish/ setmeal
    private String name;

    private Long userId;

    private Long dishId;

    private Long setmealId;

    private String dishFlavor;

    //count
    private Integer number;

    //price
    private BigDecimal amount;

    private String image;

    private LocalDateTime createTime;
}
