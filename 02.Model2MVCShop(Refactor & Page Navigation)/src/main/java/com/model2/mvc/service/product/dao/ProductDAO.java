package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;



public class ProductDAO {

	public ProductDAO() {
	}
	
	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product"+					
					" values( seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
			 
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		
		stmt.executeUpdate();
		
		con.close();	
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		
		Connection con = DBUtil.getConnection();
		System.out.println("productDAO getProductList 실행");		
		System.out.println(search);
		Map<String,Object> map = new HashMap<String,Object>(); 
		
		String sql = "SELECT * FROM product pd, transaction ta WHERE pd.prod_no = ta.prod_no(+)  " ;
				
		if(search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += "AND pd.prod_no LIKE ('%" + search.getSearchKeyword()
						+ "%') ";
				
			} else if (search.getSearchCondition().equals("1")) {
				sql += "AND pd.prod_name LIKE ('%" + search.getSearchKeyword()
						+ "%') ";
			} else if (search.getSearchCondition().equals("2")){
				sql += "AND pd.price LIKE ('%" + search.getSearchKeyword()
						+ "%')";
			}		
			search.setSearchCondition(search.getSearchCondition());
			System.out.println(search.getSearchCondition());
			System.out.println(search.getSearchKeyword());
			search.setSearchKeyword(search.getSearchKeyword());
			
			System.out.println(search+" searcVO 값 확인");
		}
		sql += " ORDER BY pd.prod_no(+) ";
		System.out.println("1-1번디버깅");
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt =  con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		System.out.println("2번디버깅");
		
		
		List<Product > list = new ArrayList<Product >();
		List<Purchase> purList = new ArrayList<Purchase >();

			while(rs.next()) {
				Product product = new Product();
				Purchase purchase = new Purchase();
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setPrice(rs.getInt("price"));
				product.setRegDate(rs.getDate("reg_date"));
				purchase.setPurchaseProd(product);
				purchase.setTranCode(rs.getString("tran_status_code"));
				//vo.setProTranCode(rs.getString("proTranCode"));
			
				System.out.println("3번 if문안디버깅");
				System.out.println(rs.getString("tran_status_code"));
				list.add(product);
				purList.add(purchase);
				System.out.println(purList+"test");
				System.out.println("trancode 체크"+purchase.getTranCode());
			}	
			
			
		
				
			
	
		
		map.put("list", list);
		map.put("purList", purList);
		System.out.println("map().size() : "+ map.size());
		System.out.println("list.size() : "+ list.size());
		System.out.println("purList.size() : "+ purList.size());
		

		con.close();
			
		
		
		return map;
	}

	
public Product findProduct(int prodNo) throws Exception {
	
	Connection con = DBUtil.getConnection();
	
	String sql =  "SELECT * FROM product WHERE prod_no IN (?)"; 
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setInt(1, prodNo);
	
	ResultSet rs= stmt.executeQuery();
	Product product = null;
	
	while(rs.next()) {
		product= new Product();
		product.setProdNo(rs.getInt("prod_no"));
		product.setProdName(rs.getString("prod_name"));
		product.setPrice(rs.getInt("price"));
		product.setRegDate(rs.getDate("reg_date"));
		product.setFileName(rs.getString("image_file"));
		product.setProdDetail(rs.getString("prod_detail"));
		product.setManuDate(rs.getString("manufacture_day"));
		//product.setProTranCode(rs.getString("proTranCode"));
		
	}
	
	con.close();	
	
	return product;
}

public void updateProduct(Product product) throws Exception {
	
	System.out.println("userDAO updateProdcut 실행");
	Connection con = DBUtil.getConnection();
	
	String sql = "UPDATE product SET "
			+ " prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? "
			+ " WHERE prod_no= ?";
	
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1, product.getProdName());			
	stmt.setString(2, product.getProdDetail());		
	stmt.setString(3, product.getManuDate());			
	stmt.setInt(4, product.getPrice());				
	stmt.setString(5, product.getFileName());
	stmt.setInt(6, product.getProdNo());				
	System.out.println(product.getProdName());
	System.out.println(product.getProdDetail());
	System.out.println(product.getManuDate());
	System.out.println(product.getPrice());
	System.out.println(product.getFileName());
	System.out.println(product.getProdNo());
	
	
	
	
	stmt.executeUpdate();
	
	con.close();	
}
	private String makeCurrentPageSql(String sql , Search search){
	sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  "
							+ "ROWNUM AS row_seq " +
							" 	FROM (	"+sql+" ) inner_table "+
							"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
	System.out.println("UserDAO :: make SQL :: "+ sql);	
	
	return sql;
}
	/*
	 * SELECT *
FROM (SELECT inta.* ,  ROWNUM AS row_seq , ta.tran_status_code
	FROM(SELECT * 
		FROM product 
		ORDER BY prod_no(+))
		inta, transaction ta	
		WHERE inta.prod_no = ta.prod_no(+)) 		
		WHERE row_seq BETWEEN 4 AND 6   재가공할것
	 */ 
	
	
	
	

}	
