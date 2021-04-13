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
	//	System.out.println(request.getQueryString());
	//	System.out.println(session.getAttributeNames());
	//	System.out.println("==================");
		searchVO.setSearchCondition((String)session.getAttribute("searchCondition"));
		searchVO.setSearchKeyword((String)session.getAttribute("searchKeyword"));
	//	System.out.println(searchVO+" 디버깅 1번");
		if(searchVO.getSearchCondition() == null) {
	//		System.out.println(request.getParameter("searchCondition")+"    1번째 서치컨디션 키워드값");
	//		System.out.println(request.getParameter("searchKeyword"));
			searchVO.setSearchCondition(request.getParameter("searchCondition"));
			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
	//		System.out.println(request.getParameter("searchCondition")+"2번째 서치컨디션");
	//		System.out.println(request.getParameter("searchKeyword"));
			String sc = searchVO.getSearchCondition();
			String sk = searchVO.getSearchKeyword();
			
			//System.out.println(sc+"sc스트링값");
			//System.out.println(sk+"sk스트링값");
			
			session.setAttribute("searchCondition", sc);
			session.setAttribute("searchKeyword", sk);
			request.setAttribute("searchCondition", sc);
			request.setAttribute("searchKeyword", sk);
			
		
		}
			
		
		System.out.println(searchVO+"페이지유닛생성 바로위1");

		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
//		System.out.println("2");
//		System.out.println(searchVO+"impl생성 바로아래1");
		ProductService service = new ProductServiceImpl();
//		System.out.println(searchVO+"impl생성 바로아래");
		HashMap<String, Object> map =  service.getProductList(searchVO);
//		System.out.println(searchVO+"해쉬맵 서비스 바로아래");
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		session.setAttribute("searchVO", searchVO);
	
		System.out.println(searchVO);
		System.out.println("listProductAction 끝");
	
		
	
		
		
		
		return "forward:/product/listProduct.jsp";
	}
		
}