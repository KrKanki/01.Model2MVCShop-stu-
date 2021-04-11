package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	public void init() throws ServletException {
		super.init();
		String resources = getServletConfig().getInitParameter("resources");
		mapper = RequestMapping.getInstance(resources);
		
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length()); // 리퀘스트의 URI 셋팅후 패스로 만듦. 
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);   			// 패스인자를 리퀘스트매핑의 겟액션에 파라미터로 실행 .
			action.setServletContext(getServletContext());
			System.out.println("path 출력"+path);
			
			String resultPage=action.execute(request, response);   // 리절트페이지의 값은 redirect:/~~~ or forward:/~~~ 로 나옴.
			String result=resultPage.substring(resultPage.indexOf(":")+1);   // 리절트페이지 값의 : 이후의 /~~~ 리절트값 반영 
			System.out.println(" 리졀트페이지+ 리절트 : "+resultPage + result); 
			
			if(resultPage.startsWith("forward:"))   // 시작이 포워드면 httputil 의 포워드 메소드 실행 아니면 redirect 실행!.
				HttpUtil.forward(request, response, result); 
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}