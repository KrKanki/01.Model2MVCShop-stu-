package com.model2.mvc.view.product;


import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ListProductAction extends Action {


	@Override
	public String execute(HttpServletRequest request, 
							HttpServletResponse response) throws Exception {
		System.out.println("listproductaction 角青");
		Search search = new Search();
	
		int currentPage= 1;
	
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);	
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
	
		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map =  (HashMap<String, Object>) service.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue()  , pageUnit, pageSize);
	
		

		 String title = null;

		 String url = null;
		 String sc = null;
		 String sk = null;
		 
		HashMap<String, String> menu = new HashMap();
		 
		if( request.getParameter("menu").equals("manage")) {
				 title = "惑前 包府";
				 url = "menu=manage";
				 sc = search.getSearchCondition();
				 sk = search.getSearchKeyword();
			}else{
				 title = "惑前 格废炼雀";
				 url = "menu=search";
				 sc = search.getSearchCondition();
	 			 sk = search.getSearchKeyword();
			}
		
		menu.put("title", title);
		menu.put("url", url);
		menu.put("sc", sc);
		menu.put("sk", sk);
		
		System.out.println("ListProductAction ::"+resultPage);
		request.setAttribute("menu", menu);
		System.out.println(menu);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("purList", map.get("purList"));
		request.setAttribute("search", search);			
		request.setAttribute("resultPage", resultPage);
		System.out.println(resultPage);
		System.out.println("ListUserAction ::"+resultPage);
		
		System.out.println("listProductAction 场");
	
		
		return "forward:/product/listProduct.jsp";
	}
  

}