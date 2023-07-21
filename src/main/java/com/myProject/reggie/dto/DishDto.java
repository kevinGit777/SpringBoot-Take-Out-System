package com.myProject.reggie.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import com.myProject.reggie.entity.Dish;
import com.myProject.reggie.entity.DishFlavor;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
