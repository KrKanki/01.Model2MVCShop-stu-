package com.model2.mvc.view.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		SearchVO searchVO=new SearchVO();
		System.out.println("ListUserAction 실행");
		int page=1;
		System.out.println("이프이전 실행");
		if(request.getParameter("page") != null)
			page=Integer.parseInt(request.getParameter("page"));
		System.out.println("이프직후 실행");
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		System.out.println(searchVO.getSearchCondition());
		System.out.println(searchVO.getSearchKeyword());
		System.out.println("1");
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println("2");
		UserService service=new UserServiceImpl();
		HashMap<String,Object> map=service.getUserList(searchVO);
		System.out.println("3");
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		
		System.out.println("리턴이전실행");
		return "forward:/user/listUser.jsp";
		
	}
}