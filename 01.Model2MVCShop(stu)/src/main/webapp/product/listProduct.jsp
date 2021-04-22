<%@page import="com.model2.mvc.service.user.vo.UserVO"%>
<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@page import = "java.util.List"%>  
<%@page import = "java.util.HashMap" %>
<%@page import = "com.model2.mvc.common.SearchVO" %>



 <% 
 HashMap<String, Object> map = (HashMap<String,Object>)request.getAttribute("map");
 SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
 UserVO userVO = (UserVO)session.getAttribute("user");
 System.out.println(userVO);
 int total = 0;
 List<ProductVO> list = null;
 List<PurchaseVO> purList = null;
 if(map != null){
	 total = ((Integer)map.get("count")).intValue();
	 list = (List<ProductVO>)map.get("list");
	 purList = (List<PurchaseVO>)map.get("purList");
	
	 
 	}
	 int currentPage = searchVO.getPage();
	 
	 int totalpage=0;
	 if(total> 0){
		 totalpage= total / searchVO.getPageUnit();
		 if(total%searchVO.getPageUnit() >0)
			 totalpage +=1;
	 }
	 String title = null;

	 String url1 = null;
	 String url2 = null;
	 String url3 = null;
	 String trans = null;
	if( request.getParameter("menu").equals("manage")) {
			 title = "상품 관리";
		 	 url1 = "menu=manage";
		 	 url2 = searchVO.getSearchCondition();
 			 url3 = searchVO.getSearchKeyword();
 			 
 			if(purchaseVO.getTranCode() == null){
			판매 중
		
		}else if(	purchaseVO.getTranCode().trim().equals("1") ){  
		구매 완료 <a href="/updateTranCodeByProd.do?tranNo= <%=purchaseVO.getPurchaseProd().getProdNo() %>&tranCode=2">배송 시작</a>
		}else if(purchaseVO.getTranCode().trim().equals("2") ){
		배송중 
		}else if(purchaseVO.getTranCode().trim().equals("3") ){					
			배송완료
		 } 
 			 
		}else{
			 title = "상품 목록조회";
			 url1 = "menu=search";
			 url2 = searchVO.getSearchCondition();
 			 url3 = searchVO.getSearchKeyword();
		}
 %>
 
 
 
 
 
 
<!DOCTYPE html>
<html>
<head>
<title></title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetProductList(){
	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=manage" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							<%=title %>
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<%
		if(searchVO.getSearchCondition() != null){
	%>
	
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				
				<%
				if(searchVO.getSearchCondition().equals("0")){
				%>
				<option value ="0" selected>상품번호</option>
				<option value ="1">상품명</option>
				<option value ="2">상품가격</option>
				<%
				}else if(searchVO.getSearchCondition().equals("1")){
					%>
				<option value ="0" >상품번호</option>
				<option value ="1" selected>상품명</option>
				<option value ="2">상품가격</option>
				<%
				}else if(searchVO.getSearchCondition().equals("2")){
					%>
				<option value ="0" >상품번호</option>
				<option value ="1" >상품명</option>
				<option value ="2" selected>상품가격</option>				
				<%
				}
				%>
				
			</select>
			<input type="text" name="searchKeyword" value="<%= searchVO.getSearchKeyword() %>" 
					class="ct_input_g" style="width:200px; height:19px" />
		</td>
	 <%
	 }else{
	 %>
		 <td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" selected>상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" >
		</td>
	<%
	 }
	%>
		 
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>




<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 <%=total %>  건수, 현재 <%=currentPage %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
		 
		
		
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		<%
			int no = list.size();
			for(int i= 0; i< list.size(); i++){
				ProductVO productVO = (ProductVO)list.get(i);
				PurchaseVO purchaseVO = (PurchaseVO)purList.get(i);
				
				System.out.println("def : >>>>>>>>>>>>>>>"+purchaseVO.getTranCode());
		%>
	<tr class="ct_list_pop">
		<td align="center"><%=no-- %></td>
		<td></td>
				<% if(purchaseVO.getTranCode() == null){ %>
				<td align="left"><a href="getProduct.do?prodNo=<%=productVO.getProdNo()%>&<%=url1%>">		<%=productVO.getProdName() %></a></td>
				<% }else{  %>
				<td><%=productVO.getProdName() %></a></td>
				<%} %>
		<td></td>
		<td align="left"><%=productVO.getPrice() %></td>
		<td></td>
		<td align="left"><%=productVO.getRegDate() %></td>
		<td></td>
		<td align="left">
		
				<%	
			
				
				
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<%} %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
	
				<%for (int i = 1; i<= totalpage; i++){ 	 	%>
	 
					<a href = "/listProduct.do?page=<%=i%>&<%=url1%>"><%=i%></a>	 
			
					<%}	 %>
					
					
				
	
		 	
	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>