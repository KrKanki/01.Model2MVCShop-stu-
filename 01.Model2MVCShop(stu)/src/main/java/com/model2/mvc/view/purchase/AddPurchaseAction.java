package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;
import com.model2.mvc.view.product.GetProductAction;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		System.out.println("addpurchaseaction Ω√¿€");
		HttpSession session = request.getSession();
		
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct(prodNo);
		session.setAttribute("productVO", productVO);
		System.out.println(prodNo);
		
		UserVO userVO=(UserVO)session.getAttribute("user");
		String userId = userVO.getUserId(); 
		
		System.out.println(userVO.getUserId());
		
		PurchaseVO purchaseVO = new PurchaseVO();
		
			
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));	
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));	
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));	
		session.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println(purchaseVO);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchaseVO);
		
		System.out.println("addpurchaseaction ≥°");
		
		
		
		
		
		return  "forward:/purchase/addPurchase.jsp";
	}

}
