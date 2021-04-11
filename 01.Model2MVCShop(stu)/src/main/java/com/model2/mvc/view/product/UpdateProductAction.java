package com.model2.mvc.view.product;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = (Integer) request.getAttribute("prod_No");
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setRegDate((Date)transFormat.parse(request.getParameter("regDate")));
		
	
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(productVO);
		
		HttpSession session = request.getSession();
		String sessionName=((ProductVO)session.getAttribute("product")).getProdName();
		
		if(sessionName.equals(prodNo)) {
			session.setAttribute("prodName", productVO);
		}
		
		return "redirect:/getUser.do?prodNo="+prodNo;
	
	}

}
