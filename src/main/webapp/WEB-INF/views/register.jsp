<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<title>로그인</title>

<style>
 .table{
 	border: 1px;
 	/* background-color: #C6FFFF; */
 	width: 800px;
 }
 thead h3{
 	color: #5D5D5D;
 }


</style>

<script type="text/javascript">
 	var ctxpath = '${pageContext.request.contextPath}';
 	var email_overlab_check = 0; 
 	
 	function overlabCheck(){
 		
 		var email = $('#email').val(); // id가 emaildls variable을 저장
 		
 			//ajax : jquery안에 포함 되어 있는 것
 			$.ajax({
 				type : 'GET',
 				url : ctxpath + '/checkEmail', 	/* 콘트롤러에 @RequestMapping */
 				data : {
 					email : email
 				},
 				success : function(result){	// result를 받아올꺼임
 					// console.log ( result );
 					var res = JSON.parse ( result );
 					if( res.dup ){
 						$('#checkMessage').html("사용할 수 없는 아이디입니다.");
 					} else{
 						$('#checkMessage').html("사용할 수 있는 아이디입니다.");
 						email_overlab_check = 1;
 					}
 				}
 			
 			})
 	}

 	function registerConfirmCheck(){
 		
 		var email = $('#email').val();
 		var pw = $('#pw').val();
 		var pw2 = $('#pw2').val();
 		
 		if(email_overlab_check ==0){
 			alert('중복확인을 해주세요');
 		}else if(email == ''){
 			alert('아이디를 넣어주세요');
 		}else if(pw ==''){
 			alert('비밀번호를 입력해주세요');
 		}else if(pw2 == ''){
 			alert('비밀번호를 확인해주세요');
 		}
 		else{
	 		$.ajax({
				type : 'POST',
				url : ctxpath + '/succRegister', /* 콘트롤러에 @RequestMapping */
				data : {
					email : email,
					password : pw
				},
				success : function(result){
					var res = JSON.parse(result);
					
					if( res.success ){
						alert('회원가입이 완료되었습니다');
						location.href = ctxpath;
					}
					else {
						alert('회원가입을 실패하였습니다. 잠시 후 다시 시도해주세요.');
					}
				}
	 		})
 		}// end if-else
 		return false;
 	}
 	
 	function passwordCheck(){
 		var pw = $('#pw').val();
 		var pw2 = $('#pw2').val();
 		if(pw == pw2){
 			$('#passwordCheckMessage').html('');
 		}
 		if(pw != pw2){
 			$('passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
 		}
 		
 	}
 	
 $(document).ready ( function() {
	 console.log ( 'ok????');
	 $('#btnRegister').on('click', function(e) {
		 e.preventDefault();// 기본 동작 작동하지 않게...
		 registerConfirmCheck();
	 });
 });
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<h3>애플리케이션 경로 : ${ctxpath }</h3>
<form>
<table class="table">
<thead>
	<tr>
		<td>
			<h3 >회원가입</h3>
		</td>
	</tr>
</thead>
<tbody>
<tr>
	<td>이메일</td>
	<td> <input type="email" name="email" id="email" placeholder="아이디를 입력해주세요"/> <input type="button" onclick="overlabCheck()" value="중복확인"/></td>
	<td><span id="checkMessage"></span></td>
</tr>
<tr>
	<td>비밀번호</td>
	<td><input type="password" name="pw" id="pw" onkeyup="passwordCheck()" placeholder="비밀번호를 입력해주세요"/></td>
	<td></td>
</tr>
<tr>
	<td>비밀번호 확인 </td>
	<td><input type="password" name="pw2" id="pw2" onkeyup="passwordCheck()" placeholder="비밀번호를 입력해주세요" /></td>
	<td></td>
</tr>
<tr>
	<td colspa="3" style="text-align: Left" colspan="3">
	<h5 style="color: red:" id="passwordCheckMessage"></h5>
	<input id="btnRegister" type="button" value="가입하기"/>
	</td>
</tr>
</tbody>
</table>
</form>
</body>
</html>