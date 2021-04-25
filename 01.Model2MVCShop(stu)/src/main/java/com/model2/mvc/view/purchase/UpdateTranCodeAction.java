package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdateTranCodeByProdAction 角青");
		
		HttpSession session = request.getSession();
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(Integer.parseInt(request.getParameter("tranNo")));
		UserVO userVO = (UserVO)session.getAttribute("user");
		
		PurchaseVO purchaseVO  = new PurchaseVO();
		
		purchaseVO.setPurchaseProd(productVO);
		//String tranCode = request.getParameter("tranCode");
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		System.out.println("purchaseVO.prodNo 眉农"+ purchaseVO.getPurchaseProd().getProdNo() );
		System.out.println("tranCode 眉农"+ request.getParameter("tranCode") );	
		
		 System.out.println(userVO.getRegDate().equals("admin"));
		if(userVO.getRegDate().equals("admin")) {
		
		System.out.println("\"forward:/listProduct.do?menu=manage\" 角青");
		return 	"forward:/listProduct.do?menu=manage";
		}else {
		System.out.println("forward:/listPurchase.do 角青");	
		return "forward:/listPurchase.do";			
		}
	}

}
