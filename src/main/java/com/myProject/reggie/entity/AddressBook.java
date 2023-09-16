package com.myProject.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Long userId;


    private String consignee;


    private String phone;


    //female:0 ; male :1
    private String sex;


    private String provinceCode;


    private String provinceName;


    private String cityCode;


    private String cityName;


    private String districtCode;


    private String districtName;


    //detail address
    private String detail;


    //ex: home, company
    private String label;

    //0: false, 1: true
    private Integer isDefault;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    private Integer isDeleted;
}
