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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;


public class PurchaseDAO {
	
	public PurchaseDAO() {
		
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		System.out.println("insertPurchase 실행");
		String sql = "INSERT INTO transaction "
				+ "(tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, demailaddr, dlvy_request, dlvy_date, order_data, tran_status_code) "
				+ "VALUES(seq_product_prod_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());
		stmt.setString(9, "1");
		stmt.executeUpdate();
		
		con.close();
				
		
	}
	
	public HashMap<String,Object> getPurchaseList(Search search, String buyerId ) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("getPurchaseList 실행");
		HashMap<String,Object> map = new HashMap(); 
			
		String sql = "SELECT"
				+ "		ta.prod_no, ta.buyer_id, ta.payment_option, ta.receiver_name, ta.receiver_phone,"
				+ "		ta.demailaddr, ta.dlvy_request, ta.tran_status_code, ta.order_data, ta.dlvy_date, pd.prod_name"
				+ "		FROM transaction ta, product pd"
				+ "		WHERE ta.prod_no = pd.prod_no AND"
				+ "		BUYER_ID IN ( '"+buyerId+"' )";
			
		
		sql += " ORDER BY prod_no ";
		
		
		
		System.out.println("sql 출력"+sql);
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt =  con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

	
		List<Purchase> list = new ArrayList<Purchase>();
		User user = new User();
		user.setUserId(buyerId);
				
		while(rs.next()) {
				Product product = new Product();
				Purchase purchase = new Purchase();
				purchase.setBuyer(user);
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setTranCode(rs.getString("tran_status_code"));
				product.setProdNo(rs.getInt("prod_no"));
				System.out.println("prodNo 번호체크"+product.getProdNo());
				product.setProdName(rs.getString("prod_name"));
				
				purchase.setPurchaseProd(product);
				purchase.setPaymentOption(rs.getString("payment_option"));
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setDivyDate(rs.getString("dlvy_date"));
				purchase.setOrderDate(rs.getDate("order_data"));
		
				list.add(purchase);
				System.out.println("for문 내부의 list값"+list.toString());
				
		}		
			
		System.out.println("for문 외부의 list값"+list.toString());
	
		map.put("list", list);
		map.put("totalCount", totalCount);
		System.out.println( " : list size 의 값 "+ list.size() );
		
		rs.close();
		stmt.close();
		con.close();
		return map;
	}
	
	public Purchase getPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("getPurchase 실행");
		Purchase purchase = new Purchase();
		User user = new User();
		Product product = new Product();
		
		String sql = "Select * from transaction WHERE prod_no IN ( ? )";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			product.setProdNo(rs.getInt("prod_No"));
			purchase.setPurchaseProd(product);
			user.setUserId(rs.getString("buyer_id"));
			purchase.setBuyer(user);
				
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));	
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
		
		}
		
		
		
		System.out.println("purchase 체크 : "+purchase);
		
		return purchase;
	}
	
	
	public void updatePurcahse(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("updatePurchase 실행");

		
		
		String sql = "UPDATE transaction set payment_option=?, receiver_name=?, "
				+ " receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? "
				+ " WHERE prod_no IN (?)"; 
		
		System.out.println("update purcahse purchase===== "+purchase);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6,  purchase.getDivyDate());
		stmt.setInt(7, purchase.getPurchaseProd().getProdNo());
		  stmt.executeUpdate();
		System.out.println(sql);
		con.close();
		
		
		
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("updateTranCode 실행");
		
		String sql = "UPDATE transaction set tran_status_code = ? "
				+ "	 WHERE prod_no IN (?) "; 
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		
		int rs = stmt.executeUpdate();
		
		System.out.println("rs 체크"+rs);
		System.out.println("sql 체크\n" + sql);
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          " FROM ( " +sql+ ") ";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();	
		
		return totalCount;
	}

	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT "
				+ "intable.* "
				+ "FROM (SELECT inta.* ,  ROWNUM AS rnum "
				+ "	FROM( 	"+sql+" ) inta "
				+ "	WHERE rownum <="+search.getCurrentPage()*search.getPageSize()+" ) intable "
				+ "	WHERE intable.rnum BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println(search.getCurrentPage()*search.getPageSize());
		System.out.println(search.getPageSize());
		System.out.println(search.getCurrentPage());
		
		
		System.out.println("purchaseDAO :: make SQL :: "+sql);	
		
		return sql;
	}
	
}
