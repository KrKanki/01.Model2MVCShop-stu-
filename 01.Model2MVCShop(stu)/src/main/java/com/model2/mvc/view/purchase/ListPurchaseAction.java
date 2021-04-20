package com.model2.mvc.view.purchase;


import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {


	@Override
	public String execute(HttpServletRequest request, 
							HttpServletResponse response) throws Exception {
		
		System.out.println("listPurchaseaction 실행");
		
		HttpSession session = request.getSession();
	
		UserVO userVO =  (UserVO)session.getAttribute("user");
		userVO.setUserId(userVO.getUserId());
		System.out.println(userVO.getUserId() +" userId값");
				
		int page= 1;
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(page);
		

		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println(searchVO+"페이지유닛생성 바로위1");
		
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = (Map<String, Object>)service.getPurchaseList(searchVO, userVO.getUserId());
		
		
		System.out.println("map의 값"+map);
		System.out.println( " : searcgVO의 값 " + searchVO);
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		session.setAttribute("searchVO", searchVO);
	
		System.out.println("listPurchase 끝");
	

		return "forward:/purchase/listPurchase.jsp";
	}
  
}