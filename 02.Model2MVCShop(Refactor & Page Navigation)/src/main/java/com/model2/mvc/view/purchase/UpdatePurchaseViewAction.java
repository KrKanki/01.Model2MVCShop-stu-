package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("updatePurchaseViewAction���� tranNo check"+tranNo);
		 
		Purchase purchase = new Purchase();
		
		
		PurchaseService service = new PurchaseServiceImpl();
		purchase = service.getPurchase(tranNo);
		 
		request.setAttribute("purchase", purchase);
		 
		
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
