package com.model2.mvc.view.purchase;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		userVO.setUserId(request.getParameter("buyerId"));
		
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		
		
		productVO.setProdNo(Integer.parseInt(request.getParameter("tranNo")));
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		purchaseVO.setBuyer(userVO);
		System.out.println("===== "+purchaseVO);
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurcahse(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);

		
		
		return "forward:/getPurchase.do";
	}

}
