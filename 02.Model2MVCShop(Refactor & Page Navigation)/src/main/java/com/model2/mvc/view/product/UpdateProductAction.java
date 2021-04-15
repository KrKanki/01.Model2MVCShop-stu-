package com.model2.mvc.view.product;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdateProductAction 1 角青");
		
		System.out.println(request.getParameter("prodNo"));
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println(prodNo);
		System.out.println("UpdateProductAction 2-1 角青");
		//SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("UpdateProductAction 2-2 角青");
		Product product = new Product();
		System.out.println("UpdateProductAction 2-3 角青");
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setFileName(request.getParameter("fileName"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		
		
		//product.setRegDate((Date)transFormat.parse(request.getParameter("regDate")));
		System.out.println("UpdateProductAction 3 角青");
	
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		
		 
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=search";
	
	}

}
