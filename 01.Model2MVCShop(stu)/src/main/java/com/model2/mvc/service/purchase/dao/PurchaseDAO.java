package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
		
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		System.out.println("insertPurchase ½ÇÇà");
		String sql = "INSERT INTO transaction "
				+ "(tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, dlvy_addr, dlvy_request, dlvy_date, order_data, tran_status_code) "
				+ "VALUES(seq_product_prod_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getDivyDate());
		stmt.setString(9, "1");
		stmt.executeUpdate();
		
		con.close();
				
		
	}
	

}
