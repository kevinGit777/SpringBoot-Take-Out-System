package com.myProject.reggie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.myProject.reggie.common.R;
import com.myProject.reggie.common.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
	
	static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			String requestUrl =  httpServletRequest.getRequestURI();
			
			log.info("Interceped request at: {}", requestUrl);
			
			String[] permittedUrls = { "/employee/login", "/employee/logout", "/backend/**", "/front/**"};
			
			//if is not permitted url and not login yet
			if( !checkUrlPermitted(requestUrl, permittedUrls) && httpServletRequest.getSession().getAttribute("employee") == null )
			{
				log.info("{} has been deniled.", requestUrl );
				response.getWriter().write( JSON.toJSONString(R.error("NOTLOGIN")));
				return;
			}
			
			Util.setCurUserEmployeeId( (Long) httpServletRequest.getSession().getAttribute("employee"));
			chain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	
	boolean checkUrlPermitted(String requestUrl, String[] permittedUrls)
	{
		for(String permitted : permittedUrls)
		{
			if( PATH_MATCHER.match(permitted, requestUrl) )
			{
				return true;
			}
		}
		return false;
	}

}
