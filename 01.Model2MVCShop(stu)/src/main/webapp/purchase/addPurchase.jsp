<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@page import="com.model2.mvc.service.user.vo.UserVO"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%

		PurchaseVO purchaseVO = (PurchaseVO)session.getAttribute("purchaseVO");
		
		String payment;
		if(request.getParameter("paymentOption").equals("1")){
			payment = "���ݱ���";
		}else{
			payment = "�ſ뱸��";
		}
		
%>




<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>s
		<td>��ǰ��ȣ</td>
		<td><%= purchaseVO.getPurchaseProd().getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>s
		<td><%=purchaseVO.getBuyer().getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td><%=payment %>
		
			
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=purchaseVO.getReceiverName()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=purchaseVO.getReceiverPhone()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=purchaseVO.getDivyAddr()%></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=purchaseVO.getDivyRequest()%></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=purchaseVO.getDivyDate()%></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>