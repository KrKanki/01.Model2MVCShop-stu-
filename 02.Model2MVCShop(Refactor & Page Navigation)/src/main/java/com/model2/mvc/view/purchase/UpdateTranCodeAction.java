package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdateTranCodeByProdAction 角青");
		
		HttpSession session = request.getSession();
		Product product = new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("tranNo")));
		User user = (User)session.getAttribute("user");
		
		Purchase purchase  = new Purchase();
		
		purchase.setPurchaseProd(product);
		//String tranCode = request.getParameter("tranCode");
		purchase.setTranCode(request.getParameter("tranCode"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		System.out.println("purchase.prodNo 眉农"+ purchase.getPurchaseProd().getProdNo() );
		System.out.println("tranCode 眉农"+ request.getParameter("tranCode") );	
		
		 System.out.println(user.getRole().equals("admin"));
		if(user.getRole().equals("admin")) {
		
		System.out.println("\"forward:/listProduct.do?menu=manage\" 角青");
		return 	"forward:/listProduct.do?menu=manage";
		}else {
		System.out.println("forward:/listPurchase.do 角青");	
		return "forward:/listPurchase.do";			
		}
	}

}
