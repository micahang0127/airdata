<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript">

	var ctxpath = '${pageContext.request.contextPath}';

	function login(){
		
		var email = $('#email').val();
		var pw = $('#pw').val();
	
		$.ajax({ 
			type : 'POST',
			url : ctxpath + '/succLogin',
			data : {
				email : email,
				pw : pw
			},
			success : function(result){
				var res = JSON.parse(result);
				
				console.log('dologin'+result);
				
				if(res.success == true){
					location.href = ctxpath;
				}
				else{
					$('#login-error').html('로그인에 실패했습니다.');
				}
			}
		})
	}

 $(document).ready(function(){
	$('#btnlogin').on('click', function(e){
		e.preventDefault();
		login();
	});
		
	
 });
 

</script>
<title>로그인</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<div id="login-error"></div>
<form action="/login" method="POST">
<table>
<tr>
	<td>Email : <input type="text" name="email" id="email"/></td>
</tr>
<tr>
	<td>비밀번호 : <input type="password" name="pw" id="pw"/></td>
</tr>
<tr>
	<td><input id="btnlogin" type="submit" name="login" value="로그인"/></td>
</tr>
</table>
</form>

</body>
</html>