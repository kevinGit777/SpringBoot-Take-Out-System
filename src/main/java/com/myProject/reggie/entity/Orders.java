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

    //order id in string
    private String number;

    //refer to the status consts 
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

    //name of people to be deliver
    private String consignee;
    
    public final static int TO_BE_PAID = 1 ;

    public static final int TO_BE_DELIEVER = 2;

    public static final int DELEVERED = 3;

    public static final int COMPELETED=4;

    public static final int CANCELED = 5;
    
    
}
