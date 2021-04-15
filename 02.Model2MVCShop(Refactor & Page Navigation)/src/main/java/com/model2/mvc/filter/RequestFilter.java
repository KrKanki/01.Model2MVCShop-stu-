package com.model2.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class RequestFilter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,	FilterChain arg2) 
																						throws IOException, ServletException {
		arg0.setCharacterEncoding("euc-kr");
		arg2.doFilter(arg0, arg1); 
		System.out.println("리퀘스트필터 실행");
		// 모든 URI 는 XML에 /* 맵핑으로 인해 여기 
		//필터를 지나가게된다. 이필터의 기능은 문자를 EUC-KR로 인코딩해주는것. 
	}

	public void destroy() {
	}
}