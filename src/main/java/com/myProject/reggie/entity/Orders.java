package com.myProject.reggie.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 */
@Data
public class Orders implements Serializable {
	

    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    //refer to the status enum 
    private Integer status;


    private Long userId;

    private Long addressBookId;


    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;


    //1: wechat 2: alipay
    private Integer payMethod;

    private BigDecimal amount;

    //comment
    private String remark;


    private String userName;


    private String phone;


    private String address;

    private String consignee;
    
    public enum Status{TO_BE_PAID, TO_BE_DELIEVER, DELEVERED, COMPELETED, CANCELED};
    
    
}
