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
		
		
		System.out.println("listproductaction ����");
		SearchVO searchVO = new SearchVO();
		System.out.println("�׽�Ʈ");
		int page= 1;
		
		String menu = request.getParameter("menu");
		
		
		
		
		if(request.getParameter("page") != null ) 
			page=Integer.parseInt(request.getParameter("page"));
			
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		System.out.println(searchVO.getSearchCondition());
		System.out.println(searchVO.getSearchKeyword());
		System.out.println("1");
		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println("2");
		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map =  service.getProductList(searchVO);
		System.out.println("3");
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		request.setAttribute("menu", menu);
		session.setAttribute("vo", searchVO);

		System.out.println("listProductAction ��");
	
		
		
		
		
		System.out.println("������ ���� ����");
		
		
		
		return "forward:/product/listProduct.jsp";
	}
		
}
