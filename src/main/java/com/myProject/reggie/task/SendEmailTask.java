package com.myProject.reggie.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailTask implements Runnable {
	
    private String to;
    private String subject;
    private String text; 
    private SimpleMailMessage message;
    
    @Autowired
    private JavaMailSender emailSender;

	public SendEmailTask() {   	
            message = new SimpleMailMessage(); 
            message.setFrom(System.getenv("EmailSenderUsername"));
            message.setTo(to); 
            message.setSubject(subject); 
            message.setText(text);
            
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		emailSender.send(message);
	}

}
