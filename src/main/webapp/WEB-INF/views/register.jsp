<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<title>로그인</title>

<style>
 .table{
 	width: 600px;
	margin: auto;
 	height: 200px;
	/* margin-top: 10px;  */
 	/* border: 1px; */
 	/* background-color: #C6FFFF; */
 	/* width: 700px; */
 	/* text-align: right; */ 	
 }
 #checkMessage, #passwordCheckMessage{
 	color: red;
 }

/* .te_td{
	width: 200px;
}
.in_td{
	width: 400px;
} */


</style>

<script type="text/javascript">
 	var ctxpath = '${pageContext.request.contextPath}';
 	var email_overlab_check = 0; 
 	var email_form_check = 0;
 	var password_check = 0;
 	
 	
 	
 	function email_form(email){
 		
 		var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		var email = document.getElementById("email").value; //= $('#email').val();
 		//alert( email);
 			
		if(exptext.test(email) == false){ 
 			  //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우 
 			alert("이메일 형식이 올바르지 않습니다.")
 			$('#checkMessage').html('이메일 형식에 맞춰 입력해 주세요.(xxx@xxx.xxx)')
 			return false;
 			}
		else{
			email_form_check = 1;
			overlabCheck();
		}
		
 	}
 	//* 아래 html에서 호출할 때 onkeyup으로 호출하면 안됌.  onkeyup="email_form()" => X
 	//  onkeyup 속성은 사용자가 키를 사용할 때 ( ★ 키를 클릭한 시점에! ) 스크립트를 실행한다. 
 	//  so, onkeyup으로 이메일유효성 검사를 하면, 한글자씩 입력받아 한글자씩만 유효성검사함.. 
 	//  ★ 즉, 이메일 유효성을 검사할 땐 클릭 이벤트를 쓴다 ( 1. 버튼에다가 함수삽입 2. onclick이벤트 사용 )
  	
 	
 	function overlabCheck(){
 		
 		var email = $('#email').val(); // id가 email인 variable을 저장
 		
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
 			alert('중복확인을 다시 확인해주세요');
 		}else if(email == ''){
 			alert('아이디를 넣어주세요');
 		}else if(pw ==''){
 			alert('비밀번호를 입력해주세요');
 		}else if(pw2 == ''){
 			alert('비밀번호를 확인해주세요');
 		}else if(password_check == 0){
 			alert('비밀번호 확인 값이 다릅니다');
 		}
 		else{
	 		$.ajax({
				type : 'POST',
				url : ctxpath + '/succRegister', /* 콘트롤러에 @RequestMapping */
				data : {
					email : email,
					password : pw
				},
				success : function(res){
					// var res = JSON.parse(result);
					
					if( res.success ){
						alert('회원가입이 완료되었습니다');
						location.href = ctxpath;
					}
					else {
						alert('회원가입을 실패하였습니다.');
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
 			password_check = 1;
 		}
 		if(pw != pw2){
 			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
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
<form>
<h2 style="margin-top: 100px;  text-align: center;">회원가입</h2>
<br/>
<table class="table">
<tr>
	<td class="te_td">이메일</td>
	<td class="in_td_first"> 
		<input type="email" name="email" id="email" style="width: 300px;" placeholder="아이디를 입력해주세요"/>&nbsp;&nbsp;&nbsp; 
		<input type="button" style="margin-right: 0px;;" onclick="email_form();"  value="중복확인"/>
		<br/><span id="checkMessage"></span>
	</td>
</tr>
<tr>
	<td class="te_td">비밀번호</td>
	<td class="in_td"><input type="password" name="pw" id="pw" style="width:300px; " onkeyup="passwordCheck()" placeholder="비밀번호를 입력해주세요"/></td>
</tr>
<tr>
	<td class="te_td">비밀번호 확인 </td>
	<td class="in_td">
		<input type="password" name="pw2" id="pw2" style="width:300px;" onkeyup="passwordCheck()" placeholder="비밀번호를 입력해주세요" />
		<br/><h5 id="passwordCheckMessage"></h5>
	</td>
</tr>
<tr>
	<td colspa="3" style="text-align: Left" colspan="3">
	<input id="btnRegister" type="button" style="float: right;" value="가입하기"/>
</td>
</tr>
</table>
</form>
</body>
</html>