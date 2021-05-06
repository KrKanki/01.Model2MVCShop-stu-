package com.model2.mvc.view.purchase;


import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class ListPurchaseAction extends Action {


	@Override
	public String execute(HttpServletRequest request, 
							HttpServletResponse response) throws Exception {
		
		System.out.println("listPurchaseaction 실행");
		
		HttpSession session = request.getSession();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		
		User user =  (User)session.getAttribute("user");
		user.setUserId(user.getUserId());
		System.out.println(user.getUserId() +" userId값");
				
		Search search = new Search();
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		search.setCurrentPage(currentPage);
		
		
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = (Map<String, Object>)service.getPurchaseList(search, user.getUserId());
		
		Page resultPage = 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue()  , pageUnit, pageSize);
		
		System.out.println(resultPage);
		System.out.println("map의 값"+map);
		System.out.println( " : searcgVO의 값 " + search);
		request.setAttribute("map", map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
			
		
		System.out.println("listPurchase 끝");
	

		return "forward:/purchase/listPurchase.jsp";
	}
  
}