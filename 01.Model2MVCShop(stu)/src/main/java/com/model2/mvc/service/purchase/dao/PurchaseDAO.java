package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
		
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		System.out.println("insertPurchase 실행");
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
	
	public HashMap<String,Object> getPurchaseList(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("getPurchaseList 실행");
		
		String sql = "Select * from transaction WHERE user_id IN (?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getBuyer().getUserId());
				
		ResultSet rs = stmt.executeQuery();
		int total = rs.getRow();
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		Map<String,Object> map = new HashMap(); 
		map.put("count", new Integer(total));
		
		while(rs.next()) {
			
			PurchaseVO purchsaeVO = new PurchaseVO();
			purchaseVO.setBuyer(null);
			purchaseVO.setTranNo(total);
			purchaseVO.setPurchaseProd(null);
			purchaseVO.setTranCode(sql);
			purchaseVO.set
			
			
			
		}
		
		return map;
	}
	

}
