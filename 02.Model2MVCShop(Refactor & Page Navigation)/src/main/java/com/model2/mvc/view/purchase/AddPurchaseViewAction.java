package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;


public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, 
						HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		HttpSession session = request.getSession();
		
		ProductService prodService=new ProductServiceImpl();
		Product product=prodService.getProduct(prodNo);
		session.setAttribute("product", product);
		
		User user = (User)session.getAttribute("user");
		
		
		
		
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}

}
