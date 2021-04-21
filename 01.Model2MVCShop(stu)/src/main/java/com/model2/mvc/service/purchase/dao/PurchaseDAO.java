package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;

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
				+ "(tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, demailaddr, dlvy_request, dlvy_date, order_data, tran_status_code) "
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
	
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId ) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("getPurchaseList 실행");
		
		String sql = "Select * from transaction WHERE BUYER_ID IN ( '"+buyerId+"' )";
		sql += " ORDER BY prod_no ";
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		//stmt.setString(1, buyerId);
		System.out.println(sql);		
		ResultSet rs = stmt.executeQuery();
		rs.last();
		int total = rs.getRow();
		System.out.println(buyerId);
		System.out.println(rs.getRow() +"row의 값");
		
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		UserVO userVO = new UserVO();
		ProductVO productVO = new ProductVO();
		HashMap<String,Object> map = new HashMap(); 
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		System.out.println(searchVO.getPageUnit() + " : searchVO pageUnit의 값" );
		userVO.setUserId(buyerId);
		
		
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
			
			PurchaseVO purchaseVO = new PurchaseVO();
			purchaseVO.setBuyer(userVO);
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			productVO.setProdNo(rs.getInt("prod_no"));
			purchaseVO.setPurchaseProd(productVO);
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			
			
			
			
			
			list.add(purchaseVO);
			System.out.println(list.toString());
			if (!rs.next())
				break;
			}
			
			
		
		}
		
		map.put("count", new Integer(total));
		map.put("list", list);
		
		
		
		System.out.println( " : list size 의 값 "+ list.size() );
		return map;
	}
	
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("getPurchase 실행");
		PurchaseVO purchaseVO = new PurchaseVO();
		UserVO userVO = new UserVO();
		ProductVO productVO = new ProductVO();
		
		String sql = "Select * from transaction WHERE prod_no IN ( ? )";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			productVO.setProdNo(rs.getInt("prod_No"));
			purchaseVO.setPurchaseProd(productVO);
			userVO.setUserId(rs.getString("buyer_id"));
			purchaseVO.setBuyer(userVO);
				
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));	
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
		
		}
		
		
		
		System.out.println("purchaseVO 체크 : "+purchaseVO);
		
		return purchaseVO;
	}
	
	
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("updatePurchase 실행");

		
		
		String sql = "UPDATE transaction set payment_option=?, receiver_name=?, "
				+ " receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? "
				+ " WHERE prod_no IN (?)"; 
		
		System.out.println("update purcahse purchaseVO===== "+purchaseVO);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6,  purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getPurchaseProd().getProdNo());
		  stmt.executeUpdate();
		System.out.println(sql);
		con.close();
		
		
		
	}
	
	
}
