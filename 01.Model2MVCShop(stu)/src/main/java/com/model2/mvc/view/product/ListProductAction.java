package com.model2.mvc.view.product;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {


	@Override
	public String execute(HttpServletRequest request, 
							HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		int page= 1;
		System.out.println("listproductaction ½ÇÇà");
		String menu = request.getParameter("menu");
		request.setAttribute("menu", menu);
		
		if(request.getParameter("page") != null ) { 
			page=Integer.parseInt(request.getParameter("page"));
		}else if(request.getParameter("page") == null) {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}else if(request.getMethod() == "Post") {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}
			
		SearchVO searchVO = new SearchVO();
		
		searchVO.setPage(page);

		searchVO.setSearchCondition((String)session.getAttribute("searchCondition"));
		searchVO.setSearchKeyword((String)session.getAttribute("searchKeyword"));
		
		if(searchVO.getSearchCondition() == null) {
			
			searchVO.setSearchCondition(request.getParameter("searchCondition"));
			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
	
			String sc = searchVO.getSearchCondition();
			String sk = searchVO.getSearchKeyword();
			
		}
			searchVO.setSearchCondition("searchVO", sc);
		
		

		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println("2");
		
		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map =  service.getProductList(searchVO);

		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		session.setAttribute("searchVO", searchVO);
	
		System.out.println(searchVO);
		System.out.println("listProductAction ³¡");
	
		
	
		
		
		
		return "forward:/product/listProduct.jsp";
	}
		
}