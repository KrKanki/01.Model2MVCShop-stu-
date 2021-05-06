
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="com.model2.mvc.service.domain.User"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    

<%@page import = "java.util.HashMap" %>
<%@page import = "com.model2.mvc.common.Search" %>



 <% 
 Page resultPage=(Page)request.getAttribute("resultPage");
 HashMap<String, Object> map = (HashMap<String,Object>)request.getAttribute("map");
 Search search = (Search)request.getAttribute("search");
 User user = (User)session.getAttribute("user");
 HashMap<String, String> menu = (HashMap<String,String>)request.getAttribute("menu");
 List list = (List)request.getAttribute("list");
 List purList = (List)request.getAttribute("purList");
 System.out.println(user);
 System.out.println(map);
 
 int total = 0;

 %>
	
 
 
 
 
 
 
<!DOCTYPE html>
<html>
<head>
<title></title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetProductList(currentPage){
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();	
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?<%=menu.get("url")%>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							<%=menu.get("title") %>
							
					
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
		if(search.getSearchCondition() != null){
	%>
	
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				
				<%
				if(search.getSearchCondition().equals("0")){
				%>
				<option value ="0" selected>��ǰ��ȣ</option>
				<option value ="1">��ǰ��</option>
				<option value ="2">��ǰ����</option>
				<%
				}else if(search.getSearchCondition().equals("1")){
					%>
				<option value ="0" >��ǰ��ȣ</option>
				<option value ="1" selected>��ǰ��</option>
				<option value ="2">��ǰ����</option>
				<%
				}else if(search.getSearchCondition().equals("2")){
					%>
				<option value ="0" >��ǰ��ȣ</option>
				<option value ="1" >��ǰ��</option>
				<option value ="2" selected>��ǰ����</option>				
				<%
				}
				%>
				
			</select>
			<input type="text" name="searchKeyword" value="<%= search.getSearchKeyword() %>" 
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
		<td colspan="11" >��ü <%=resultPage.getTotalCount() %>  �Ǽ�, ���� <%=resultPage.getCurrentPage() %> ������</td>
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
		 
		
		
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		<%
			int no = list.size();
			for(int i= 0; i< list.size(); i++){
				Product product = (Product)list.get(i);
				Purchase purchase = (Purchase)purList.get(i);
		
		%>
	<tr class="ct_list_pop">
		<td align="center"><%=no-- %></td>
		<td></td>
				<% if(purchase.getTranCode() == null){ %>
				<td align="left"><a href="getProduct.do?prodNo=<%=product.getProdNo()%>&<%= menu.get("url")%>" ><%=product.getProdName() %></a></td>
				<%}else{  %>
				<td align="left"><%=product.getProdName() %></a></td>
				<%} %>
		
		<td></td>
		<td align="left"><%=product.getPrice() %></td>
		
		<td></td>
		<td align="left"><%=product.getRegDate() %></td>
		
		<td></td>
		<td align="left">
				
		<%if(request.getParameter("menu").equals("manage")){	
				if(purchase.getTranCode() == null){%>
				�Ǹ� ��
		
				<%}else if(	purchase.getTranCode().trim().equals("1") ){%>  
				���� �Ϸ� <a href="/updateTranCode.do?tranNo=<%=product.getProdNo()%>&tranCode=2">��� ����</a>
				<%}else if(purchase.getTranCode().trim().equals("2") ){%>
				����� 
				<%}else if(purchase.getTranCode().trim().equals("3") ){%>			
				��ۿϷ�
				<%} 
		}else{
			
			if(purchase.getTranCode() == null){%>
		�Ǹ� ��
		
		<%}else if(	purchase.getTranCode().trim().equals("1") ){%>  
		���� �Ϸ� 
		<%}else if(purchase.getTranCode().trim().equals("2") ){%>
		����� 
		<%}else if(purchase.getTranCode().trim().equals("3") ){%>			
		��ۿϷ�		
		<%}	}%>
		
				
				
		
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
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i%>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
				
	
		 	
	
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>