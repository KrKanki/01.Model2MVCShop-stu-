package com.model2.mvc.view.product;


import java.util.HashMap;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ListProductAction extends Action {


	@Override
	public String execute(HttpServletRequest request, 
							HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		int page= 1;
		SearchVO searchVO = new SearchVO();
		System.out.println("listproductaction 실행");
		String menu = request.getParameter("menu");
		request.setAttribute("menu", menu);
		
		UserVO userVO = (UserVO)session.getAttribute("user");
				
		
		
		System.out.println(request.getParameter("searchCondition"));
		System.out.println(request.getParameter("searchKeyword"));
		System.out.println("if문 전 시작 ");
		
		
		if(request.getParameter("page")!=null) { 
			page=Integer.parseInt(request.getParameter("page"));
		}else if(request.getParameter("page") ==null) {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}else if(request.getMethod().equals("Post")) {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}
			
		
		
		searchVO.setPage(page);
		searchVO.setSearchCondition((String)session.getAttribute("searchCondition"));
		searchVO.setSearchKeyword((String)session.getAttribute("searchKeyword"));
		if(searchVO.getSearchCondition() == null) {
			searchVO.setSearchCondition(request.getParameter("searchCondition"));
			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
			String sc = searchVO.getSearchCondition();
			String sk = searchVO.getSearchKeyword();
			
			
			session.setAttribute("searchCondition", sc);
			session.setAttribute("searchKeyword", sk);
			request.setAttribute("searchCondition", sc);
			request.setAttribute("searchKeyword", sk);
			
		
		}
			
		
		System.out.println(searchVO+"페이지유닛생성 바로위1");

		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map =  (HashMap<String, Object>) service.getProductList(searchVO);
		
		PurchaseVO purchaseVO = new PurchaseVO();
	
		
	
		
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		session.setAttribute("searchVO", searchVO);
	
		System.out.println(searchVO);
		System.out.println(map);
		System.out.println("listProductAction 끝");
	
		
		System.out.println("userVO 값 확인"+userVO);
		
		
		
		return "forward:/product/listProduct.jsp";
	}
  

	
		
}