package com.myProject.reggie.service.implementation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.myProject.reggie.service.EmailServise;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailServiseImpl implements EmailServise {

    @Autowired
    private JavaMailSender emailSender;
    
    @Autowired
    public SimpleMailMessage template;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(System.getenv("EmailSenderUsername"));
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
   public void SendValicationCode(String codeString,  String to) {
	   
	   ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
       emailExecutor.execute(new Runnable() {
           @Override
           public void run() {
               String text = String.format(template.getText(), codeString);  
			   sendSimpleMessage(to, "Validation Code", text);
           }
       });
       emailExecutor.shutdown(); 
	   
	   
}


}
