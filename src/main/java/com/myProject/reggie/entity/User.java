package com.myProject.reggie.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;


    private String phone;
    
    private String email;
    
    //female: 0, male: 1
    private String sex;


    private String idNumber;


    //avatarLocation
    private String avatar;


    //0: banned, 1: normal
    private Integer status;
    
    
}
