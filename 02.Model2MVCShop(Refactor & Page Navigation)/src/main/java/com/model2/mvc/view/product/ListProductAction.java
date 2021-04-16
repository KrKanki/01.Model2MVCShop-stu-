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
		System.out.println("listproductaction 실행");
		Search search = new Search();
		HttpSession session = request.getSession();
		int currentPage= 1;
		
		if(request.getParameter("currentPage")!=null) { 
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}else if(request.getParameter("currentPage") ==null) {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}else if(request.getMethod().equals("Post")) {
			session.removeAttribute("searchCondition");
			session.removeAttribute("searchKeyword");
		}
		
		search.setCurrentPage(currentPage);	
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		
		
		System.out.println(request.getParameter("searchCondition"));
		System.out.println(request.getParameter("searchKeyword"));
		System.out.println("if문 전 시작 ");
		
		
	//	System.out.println(request.getQueryString());
	//	System.out.println(session.getAttributeNames());
	//	System.out.println("==================");
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
	//	System.out.println(search+" 디버깅 1번");
		if(search.getSearchCondition() == null) {
	
			search.setSearchCondition(request.getParameter("searchCondition"));
			search.setSearchKeyword(request.getParameter("searchKeyword"));
	
			String sc = search.getSearchCondition();
			String sk = search.getSearchKeyword();
			
			//System.out.println(sc+"sc스트링값");
			//System.out.println(sk+"sk스트링값");
			
			session.setAttribute("searchCondition", sc);
			session.setAttribute("searchKeyword", sk);
			request.setAttribute("searchCondition", sc);
			request.setAttribute("searchKeyword", sk);
		}
		
//		System.out.println("2");
//		System.out.println(search+"impl생성 바로아래1");
		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map =  (HashMap<String, Object>) service.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue()  , pageUnit, pageSize);
	
		//Purchase purchase = new Purchase();
	

		 String title = null;

		 String url1 = null;
		 String url2 = null;
		 String url3 = null;
		 
		ArrayList<String> menu = new ArrayList<String>();
		 
		if( request.getParameter("menu").equals("manage")) {
				 title = "상품 관리";
			 	 url1 = "menu=manage";
			 	 url2 = search.getSearchCondition();
	 			 url3 = search.getSearchKeyword();
			}else{
				 title = "상품 목록조회";
				 url1 = "menu=search";
				 url2 = search.getSearchCondition();
	 			 url3 = search.getSearchKeyword();
			}
		
		menu.add(title);
		menu.add(url1);
		menu.add(url2);
		menu.add(url3);
		
		System.out.println("ListProductAction ::"+resultPage);
		request.setAttribute("menu", menu);
		System.out.println(menu);
		request.setAttribute("list", map.get("list"));
//		request.setAttribute("purlist", map.get("purlist"));
		request.setAttribute("search", search);			
		request.setAttribute("resultPage", resultPage);
		System.out.println(search);
		System.out.println(map);
		System.out.println("listProductAction 끝");
	
		
		return "forward:/product/listProduct.jsp";
	}
  

}