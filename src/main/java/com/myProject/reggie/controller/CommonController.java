package com.myProject.reggie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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
		if (!baseDirFile.exists()) {
			baseDirFile.mkdirs();
		}

		String fileOriginalNameString = file.getOriginalFilename();
		String filenewNameString = UUID.randomUUID().toString()
				+ fileOriginalNameString.substring(fileOriginalNameString.lastIndexOf('.'));

		try {
			file.transferTo(new File(imageLocationBasePath + "\\" + filenewNameString));
			log.info("successful transfer File to {}", imageLocationBasePath + "\\" + filenewNameString);
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return R.success(filenewNameString);
	}
	
	
	/**
	 * 
	 * return of this method has been handle by the respond.outputstream
	 * 
	 * @param name
	 * @param response
	 */
	@GetMapping("/download")
	public void fileDownLoad(String name, HttpServletResponse response )
	{
		try {
			FileInputStream inputStream = new FileInputStream(new File(imageLocationBasePath + "\\" + name));
			
			ServletOutputStream outputStream = response.getOutputStream();
			
			response.setContentType("image/jepg");
			
			int readInLen = 0;
			byte[] byteStream = new byte[1024];
			while ((readInLen = inputStream.read(byteStream) )!= -1) {
				outputStream.write(byteStream, 0, readInLen);
			}
			outputStream.flush();
			
			inputStream.close();
			outputStream.close();
			
			// output Stream has been close
			//return R.success("File has been transfered.");
		} catch (IOException e) {
			e.printStackTrace();
			//return R.error("File reading error.");
		}
		
		
	}
	
	
	
	protected void deleteImages(List<String> imgLocations) {
		
		for (String imgLocation : imgLocations)
		{
			new File(imageLocationBasePath + "\\" + imgLocation).delete();
		}
		
		
	}
	

}
