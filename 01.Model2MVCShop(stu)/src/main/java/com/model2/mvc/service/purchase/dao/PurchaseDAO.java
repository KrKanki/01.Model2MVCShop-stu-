package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
		
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO TRANSACTION ";
				
		
	}
	

}
