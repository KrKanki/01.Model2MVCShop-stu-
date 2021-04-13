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
		System.out.println("listproductaction ����");
		String menu = request.getParameter("menu");
		request.setAttribute("menu", menu);
		
		System.out.println(request.getParameter("searchCondition"));
		System.out.println(request.getParameter("searchKeyword"));
		System.out.println("if�� �� ���� ");
		
		
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
	//	System.out.println(searchVO+" ����� 1��");
		if(searchVO.getSearchCondition() == null) {
	//		System.out.println(request.getParameter("searchCondition")+"    1��° ��ġ����� Ű���尪");
	//		System.out.println(request.getParameter("searchKeyword"));
			searchVO.setSearchCondition(request.getParameter("searchCondition"));
			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
	//		System.out.println(request.getParameter("searchCondition")+"2��° ��ġ�����");
	//		System.out.println(request.getParameter("searchKeyword"));
			String sc = searchVO.getSearchCondition();
			String sk = searchVO.getSearchKeyword();
			
			//System.out.println(sc+"sc��Ʈ����");
			//System.out.println(sk+"sk��Ʈ����");
			
			session.setAttribute("searchCondition", sc);
			session.setAttribute("searchKeyword", sk);
			request.setAttribute("searchCondition", sc);
			request.setAttribute("searchKeyword", sk);
			
		
		}
			
		
		System.out.println(searchVO+"���������ֻ��� �ٷ���1");

		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
//		System.out.println("2");
//		System.out.println(searchVO+"impl���� �ٷξƷ�1");
		ProductService service = new ProductServiceImpl();
//		System.out.println(searchVO+"impl���� �ٷξƷ�");
		HashMap<String, Object> map =  service.getProductList(searchVO);
//		System.out.println(searchVO+"�ؽ��� ���� �ٷξƷ�");
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);			
		session.setAttribute("searchVO", searchVO);
	
		System.out.println(searchVO);
		System.out.println("listProductAction ��");
	
		
	
		
		
		
		return "forward:/product/listProduct.jsp";
	}
		
}