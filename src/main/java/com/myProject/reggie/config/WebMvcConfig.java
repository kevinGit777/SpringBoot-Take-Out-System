package com.myProject.reggie.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.myProject.reggie.common.JacksonObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/***
	 * 
	 * handle static resources mapping
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		log.info("static recources mapping");
		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

	}
	
	
	/**
	 * 
	 * apply JacksonObject Mapper
	 */
	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		MappingJackson2HttpMessageConverter	j2HMessageConverter = new MappingJackson2HttpMessageConverter();
		
		j2HMessageConverter.setObjectMapper(new JacksonObjectMapper());
		
		converters.add(0, j2HMessageConverter); //add to index 0 for higher priority
	}
}
