<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>

<title>로그인</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<form action="/login" method="POST">
<table>
<tr>
	<td>Email : <input type="text" name="email"/></td>
</tr>
<tr>
	<td>비밀번호 : <input type="password" name="pw"/></td>
</tr>
<tr>
	<td><input type="submit" name="login" value="로그인"/></td>
</tr>
</table>
</form>

</body>
</html>