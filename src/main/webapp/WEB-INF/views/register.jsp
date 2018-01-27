<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>
<h3>애플리케이션 경로 : ${ctxpath }</h3>
<form action="${ctxpath }/doRegister" method="POST">
ID : <input type="text" name="id" />
이메일 : <input type="text" name="email" />
비밀번호 <input type="text" name="pw" />
<input type="submit" name="가입하기"/>
</form>

</body>
</html>