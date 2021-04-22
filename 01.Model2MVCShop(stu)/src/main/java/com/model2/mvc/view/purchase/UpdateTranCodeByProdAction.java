package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(Integer.parseInt(request.getParameter("tranNo")));
		
		PurchaseVO purchaseVO  = new PurchaseVO();
		
		purchaseVO.setPurchaseProd(productVO);
		String tranCode = request.getParameter("tranCode");
		purchaseVO.setTranCode("tranCode");
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
			
	
		return 	"forward:/listProduct.do?menu=manage";
	}

}
