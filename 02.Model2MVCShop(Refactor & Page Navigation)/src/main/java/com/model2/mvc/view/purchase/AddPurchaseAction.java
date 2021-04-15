package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.view.product.GetProductAction;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		System.out.println("addpurchaseaction Ω√¿€");
		HttpSession session = request.getSession();
		
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		session.setAttribute("product", product);
		System.out.println(prodNo);
		
		User user=(User)session.getAttribute("user");
		String userId = user.getUserId(); 
		
		System.out.println(user.getUserId());
		
		Purchase purchase = new Purchase();
		
			
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));	
		purchase.setDivyRequest(request.getParameter("receiverRequest"));	
		purchase.setDivyDate(request.getParameter("receiverDate"));	
		session.setAttribute("purchase", purchase);
		
		System.out.println(purchase);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		
		System.out.println("addpurchaseaction ≥°");
		
		
		
		
		
		return  "forward:/purchase/addPurchase.jsp";
	}

}
