SELECT 
pd.prod_no, pd.prod_name, pd.prod_detail, pd.manufacture_day, pd.price, pd.image_file, pd.reg_date, ta.tran_status_code
FROM product pd, transaction ta WHERE pd.prod_no = ta.prod_no(+)



UPDATE transaction set tran_status_code = ?
			 WHERE prod_no IN (?) 
			 
			 
			 
UPDATE transaction set tran_status_code = 2	 WHERE prod_no IN (10000)

SELECT
ta.prod_no, ta.buyer_id, ta.payment_option, ta.receiver_name, ta.receiver_phone,
ta.demailaddr, ta.dlvy_request, ta.tran_status_code, ta.order_data, ta.dlvy_date, pd.prod_name
FROM transaction ta, product pd
WHERE ta.prod_no = pd.prod_no AND
BUYER_ID IN ('user01')

[PurchaseVO [buyer=UserVO : [userId] user01 [userName] null [password] null [role] null [ssn] null [phone] null [email] null [regDate] null, divyAddr=null, divyDate=null, divyRequest=null, orderDate=2021-04-25, paymentOption=1  , purchaseProd=ProductVO : [fileName]null[manuDate]null[price]0[prodDetail]null[prodName]보르도[prodNo]10002[proTranCode]null, receiverName=SCOTT, receiverPhone=null, tranCode=2  , tranNo=0], 
PurchaseVO [buyer=UserVO : [userId] user01 [userName] null [password] null [role] null [ssn] null [phone] null [email] null [regDate] null, divyAddr=null, divyDate=null, divyRequest=null, orderDate=2021-04-25, paymentOption=1  , purchaseProd=ProductVO : [fileName]null[manuDate]null[price]0[prodDetail]null[prodName]보르도[prodNo]10002[proTranCode]null, receiverName=SCOTT, receiverPhone=null, tranCode=2  , tranNo=0], 
PurchaseVO [buyer=UserVO : [userId] user01 [userName] null [password] null [role] null [ssn] null [phone] null [email] null [regDate] null, divyAddr=null, divyDate=null, divyRequest=null, orderDate=2021-04-25, paymentOption=1  , purchaseProd=ProductVO : [fileName]null[manuDate]null[price]0[prodDetail]null[prodName]보르도[prodNo]10002[proTranCode]null, receiverName=SCOTT, receiverPhone=null, tranCode=3  , tranNo=0]]