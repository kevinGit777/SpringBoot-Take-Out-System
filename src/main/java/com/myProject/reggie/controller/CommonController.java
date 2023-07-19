package com.myProject.reggie.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myProject.reggie.common.R;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

	@Value("${reggie.imgPath}")
	private String imageLocationBasePath;
	
	@PostMapping("/upload")
	public R<String> fileUpload(MultipartFile file) {
		
		File baseDirFile = new File(imageLocationBasePath);
		if(!baseDirFile.exists())
		{
			baseDirFile.mkdirs();
		}
		
		String fileOriginalNameString = file.getOriginalFilename();
		String filenewNameString = UUID.randomUUID().toString() + fileOriginalNameString.substring( fileOriginalNameString.lastIndexOf('.') );
		
		try {
			file.transferTo( new File(imageLocationBasePath + "\\" + filenewNameString ));
			log.info("successful transfer File to {}" , imageLocationBasePath + "\\" + filenewNameString);
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		return R.success( filenewNameString);
		
		
		
	}

}
