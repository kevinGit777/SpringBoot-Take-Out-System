package com.myProject.reggie.dto;

import lombok.Data;
import java.util.List;

import com.myProject.reggie.entity.Setmeal;
import com.myProject.reggie.entity.SetmealDish;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
