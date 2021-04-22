SELECT 
pd.prod_no, pd.prod_name, pd.prod_detail, pd.manufacture_day, pd.price, pd.image_file, pd.reg_date, ta.tran_status_code
FROM product pd, transaction ta WHERE pd.prod_no = ta.prod_no(+)



UPDATE transaction set tran_status_code = ?
			 WHERE prod_no IN (?) 