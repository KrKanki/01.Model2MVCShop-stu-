package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService prodService = new ProductServiceImpl();
		ProductVO productVO = prodService.getProduct(prodNo);
		request.setAttribute("productVO", productVO);
	
		String userId=request.getParameter("userId");
		UserService userService=new UserServiceImpl();
		UserVO vo= userService.getUser(userId);
		
		request.setAttribute("userVO", vo);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setBuyer(vo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));	
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));	
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));	
		request.setAttribute("purchaseVO", purchaseVO);
		
		
		
		
		
		
		return  "forward:/purchase/addPurchase.jsp";
	}

}
