<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" /> 
    <meta name="format-detection" content="telephone=no" />  
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>订单结算页 -淘淘商城</title>
	<!--结算页面样式-->	
	<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
    <link type="text/css" rel="stylesheet"  href="/css/order-commons.css" source="widget"/>	
	<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="/js/base.js"></script>	
	<script type="text/javascript" src="/js/order.common.js"></script>
	<script type="text/javascript" src="/js/jquery.checkout.js"></script>
</head>	

<body id="mainframe">
<jsp:include page="commons/shortcut.jsp" />
<!--shortcut end-->

<div class="w w1 header clearfix">
    <div id="logo"><a href="/"><img src="/images/taotao-logo.gif" alt="淘淘商城"></a></div>
</div>


<h3 align="center">新增收货地址　电话号码、手机号选填一项, 其余均为必填项</h3><br/>
<div align="center">
<form action="/user/add.html" method="post">
			   <input type="hidden" name="userId"/>
	收货人详细地址：<input type="text" name="address" maxlength="100" size="58"/><br/><br/>
                    收货人姓名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="username" maxlength="100" size="58"/><br/><br/>
            收货人手机号码：<input type="text" name="phone" maxlength="100" size="58"/><br/><br/>
               <input type="checkbox" name="isDefault" value="1" checked="checked"/>设置为默认地址<br/><br/>
			   <input type="submit" value="保存"/>
</form>
</div>



			
	



				



	

<!-- /main -->
	<jsp:include page="commons/footer.jsp" />
	
</body>
	
</html>