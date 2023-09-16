package com.myProject.reggie.service.implementation;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myProject.reggie.entity.AddressBook;
import com.myProject.reggie.mapper.AddressBookMapper;
import com.myProject.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
