package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;


public class ProductDAO {

	public ProductDAO() {
	}
	
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product"+					
					" values( seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		stmt.executeUpdate();
		
		con.close();	
	}
	
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		System.out.println(searchVO);
		Connection con = DBUtil.getConnection();
		System.out.println("productDAO getProductList 실행");		
		String sql = "SELECT * FROM PRODUCT" ;
		System.out.println(searchVO);
		System.out.println("if문 전 시작 ");
		if(searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where prod_no LIKE ('%" + searchVO.getSearchKeyword()
						+ "%')";
				
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where prod_name LIKE ('%" + searchVO.getSearchKeyword()
						+ "%')";
			} else if (searchVO.getSearchCondition().equals("2")){
				sql += " where price LIKE ('%" + searchVO.getSearchKeyword()
						+ "%')";
			}		
			searchVO.setSearchCondition(searchVO.getSearchCondition());
			System.out.println(searchVO.getSearchCondition());
			System.out.println(searchVO.getSearchKeyword());
			searchVO.setSearchKeyword(searchVO.getSearchKeyword());
			
			System.out.println(searchVO+" searcVO 값 확인");
		}
		sql += " ORDER BY prod_no ";
		System.out.println("1-1번디버깅");
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		System.out.println("1-2번디버깅");
		ResultSet rs = stmt.executeQuery();
		System.out.println("1-3번디버깅");
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());	 
		System.out.println("2번디버깅");
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("prod_no"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setPrice(rs.getInt("price"));
				vo.setRegDate(rs.getDate("reg_date"));
				//vo.setProTranCode(rs.getString("proTranCode"));
				
				System.out.println("3번 if문안디버깅");

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		
		
		return map;
	}

	
public ProductVO findProduct(int prodNo) throws Exception {
	
	Connection con = DBUtil.getConnection();
	
	String sql =  "SELECT * FROM product WHERE prod_no IN (?)"; 
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setInt(1, prodNo);
	
	ResultSet rs= stmt.executeQuery();
	ProductVO productVO = null;
	
	while(rs.next()) {
		productVO= new ProductVO();
		productVO.setProdNo(rs.getInt("prod_no"));
		productVO.setProdName(rs.getString("prod_name"));
		productVO.setPrice(rs.getInt("price"));
		productVO.setRegDate(rs.getDate("reg_date"));
		productVO.setFileName(rs.getString("image_file"));
		productVO.setProdDetail(rs.getString("prod_detail"));
		productVO.setManuDate(rs.getString("manufacture_day"));
		//productVO.setProTranCode(rs.getString("proTranCode"));
		
	}
	
	con.close();	
	
	return productVO;
}

public void updateProduct(ProductVO productVO) throws Exception {
	
	System.out.println("userDAO updateProdcut 실행");
	Connection con = DBUtil.getConnection();
	
	String sql = "UPDATE product SET "
			+ " prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? "
			+ " WHERE prod_no= ?";
	
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1, productVO.getProdName());			
	stmt.setString(2, productVO.getProdDetail());		
	stmt.setString(3, productVO.getManuDate());			
	stmt.setInt(4, productVO.getPrice());				
	stmt.setString(5, productVO.getFileName());
	stmt.setInt(6, productVO.getProdNo());				
	System.out.println(productVO.getProdName());
	System.out.println(productVO.getProdDetail());
	System.out.println(productVO.getManuDate());
	System.out.println(productVO.getPrice());
	System.out.println(productVO.getFileName());
	System.out.println(productVO.getProdNo());
	
	
	
	
	stmt.executeUpdate();
	
	con.close();	
}


}	
