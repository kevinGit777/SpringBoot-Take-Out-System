package com.myProject.reggie.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailServise {

	public void SendValicationCode(String codeString,  String to);
}
