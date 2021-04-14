<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@page import = "java.util.ArrayList"%>  
<%@page import = "java.util.HashMap" %>
<%@page import = "com.model2.mvc.common.SearchVO" %>
<%@page import = "com.model2.mvc.service.product.vo.ProductVO"%> 


 <% 
 HashMap<String, Object> map = (HashMap<String,Object>)request.getAttribute("map");
 SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
 PurchaseVO purchaseVO = (PurchaseVO)request.getAttribute("purchaseVO");
 
 int total = 0;
 ArrayList<ProductVO> list = null;
 if(map != null){
	 total = ((Integer)map.get("count")).intValue();
	 list = (ArrayList<ProductVO>)map.get("list");
	
	 
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
	 
	if( request.getParameter("menu").equals("manage")) {
			 title = "��ǰ ����";
		 	 url1 = "menu=manage";
		 	 url2 = searchVO.getSearchCondition();
 			 url3 = searchVO.getSearchKeyword();
		}else{
			 title = "��ǰ �����ȸ";
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
				<option value ="0" selected>��ǰ��ȣ</option>
				<option value ="1">��ǰ��</option>
				<option value ="2">��ǰ����</option>
				<%
				}else if(searchVO.getSearchCondition().equals("1")){
					%>
				<option value ="0" >��ǰ��ȣ</option>
				<option value ="1" selected>��ǰ��</option>
				<option value ="2">��ǰ����</option>
				<%
				}else if(searchVO.getSearchCondition().equals("2")){
					%>
				<option value ="0" >��ǰ��ȣ</option>
				<option value ="1" >��ǰ��</option>
				<option value ="2" selected>��ǰ����</option>				
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
				<option value="0" selected>��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
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
						<a href="javascript:fncGetProductList();">�˻�</a>
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
		<td colspan="11" >��ü <%=total %>  �Ǽ�, ���� <%=currentPage %> ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
		<% if(purchaseVO.getTranCode().equals("0")){
			
		}
		
		%>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		<%
			int no = list.size();
			for(int i= 0; i< list.size(); i++){
				ProductVO productVO = (ProductVO)list.get(i);
		%>		
	<tr class="ct_list_pop">
		<td align="center"><%=no-- %></td>
		<td></td>
				
				<td align="left"><a href="getProduct.do?prodNo=<%=productVO.getProdNo()%>&<%=url1%>"><%=productVO.getProdName() %></a></td>
		
		<td></td>
		<td align="left"><%=productVO.getPrice() %></td>
		<td></td>
		<td align="left"><%=productVO.getRegDate() %></td>
		<td></td>
		<td align="left">
		
				���ſϷ�<%-- =productVO.getProTranCode() --%>
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<%-- 
			int set=0; //  �� �Ʈ
			int ps = 5;// �ѹ��� ���� ��������
			int a =0 ; // ������������
			int b = 0; //  �����������
			int c = 0;
			int i = 0;	
				 if(totalpage> 0){
					set = totalpage / ps;
					 if(totalpage%ps  >0){
					a =	totalpage%ps ;
						set++;			
					 }
				 }
				 
				 for(b = 0; b< set  ; b++){	
					
					 if(currentPage> 0){
							c = currentPage / ps;
							 if(currentPage%ps  >0){
							a =	currentPage%ps ;
								set++;			
							 }
						 }
					 for( i = 1+(set*b); i<=ps*set+b*a; i++){--%>	
				<%for (int i = 1; i<= totalpage; i++){ 	 
				 
					%>
					 
					<a href = "/listProduct.do?page=<%=i%>&<%=url1%>"><%=i%></a>	 
					
					<% 	 
				}	 
		%>
		 	
	
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>