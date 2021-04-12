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
		System.out.println("ActionServlet ���񽺸޼ҵ�.");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length()); // ������Ʈ�� URI ������ �н��� ����. 
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);   			// �н����ڸ� ������Ʈ������ �پ׼ǿ� �Ķ���ͷ� ���� .
			action.setServletContext(getServletContext());
			System.out.println("path ���"+path);
			
			String resultPage=action.execute(request, response);   // ����Ʈ�������� ���� redirect:/~~~ or forward:/~~~ �� ����.
			String result=resultPage.substring(resultPage.indexOf(":")+1);   // ����Ʈ������ ���� : ������ /~~~ ����Ʈ�� �ݿ� 
			System.out.println(" ����Ʈ������+ ����Ʈ : "+resultPage + result); 
			
			if(resultPage.startsWith("forward:"))   // ������ ������� httputil �� ������ �޼ҵ� ���� �ƴϸ� redirect ����!.
				HttpUtil.forward(request, response, result); 
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}