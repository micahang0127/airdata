<%@page import="github.hangming.airdata.model.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<style>
ul.tabs {
	margin: 0;
	padding: 0;
	float: left;
	list-style: none;
	height: 32px;
	border-bottom: 1px solid #eee;
	border-left: 1px solid #eee;
	width: 100%;
	font-family: "dotum";
	font-size: 12px;
}

ul.tabs li {
	float: left;
	text-align: center;
	cursor: pointer;
	width: 82px;
	height: 31px;
	line-height: 31px;
	border: 1px solid #eee;
	border-left: none;
	font-weight: bold;
	background: #fafafa;
	overflow: hidden;
	position: relative;
}

ul.tabs li.active {
	background: #FFFFFF;
	border-bottom: 1px solid #FFFFFF;
}

.tab_container {
	border: 1px solid #eee;
	border-top: none;
	clear: both;
	float: left;
	width: 248px;
	background: #FFFFFF;
}

.tab_content {
	padding: 5px;
	font-size: 12px;
	display: none;
}

.tab_container .tab_content ul {
	width: 100%;
	margin: 0px;
	padding: 0px;
}

.tab_container .tab_content ul li {
	padding: 5px;
	list-style: none
}

;
#container {
	width: 249px;
	margin: 0 auto;
}
</style>
<script>
	var ctxpath = '${pageContext.request.contextPath}';

	$(function() {

		$(".tab_content").hide();
		$(".tab_content:first").show(); // :first == $().first()  ==>첫번째 객체를 반환한다

		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active").css("color", "#333");
			//$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
			$(this).addClass("active").css("color", "darkred");
			$(".tab_content").hide()
			var activeTab = $(this).attr("rel");
			$("#" + activeTab).fadeIn()
		});
	});
	

	
	
	
	
	function passwordCheck(){
		var pw = $('#pw').val();
		var pw2 = $('#pw2').val();
		
		if(pw == pw2){
			$('#passwordCheckMessage').html('동일합니다.');
		}if(pw != pw2){
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		}
		
	}
	
	function changePassword(){
		
		var email = $('#email').text();
		var pw = $('#pw').val();
		var pw2 = $('#pw2').val();
		
		console.log('email값확인:'+ email);
		
		if(pw == ''){
			alert('새 비밀번호를 넣어주세요')
		}else if(pw2 == ''){
			alert('새 비밀번호를 확인해 주세요')
		}
		else{
			$.ajax({
				type : 'POST',
				url : ctxpath + '/changePassword',
				data : {
					password : pw,
					email : email
				},
				success : function(result){
					var res = JSON.parse(result);
					
					if (res.success ){
						alert('변경이 완료되었습니다.');
						location.href = ctxpath;
					}
					else{
						alert('변경에 실패하였습니다.')
					}
				}
	
			})
			
		}// end else
		
		return false;
	}
	
	
	
	$(document).ready ( function(){
		console.log('비번변경 OK?');
		$('#changePw').on('click', function(e){
			e.preventDefault();	//기본동작 작동하지 않게.. 굳이 필요한가..? 일단함..
			changePassword();
			
		});
	});
	

</script>

<%
	String email = (String)session.getAttribute("email");
	String password = (String)session.getAttribute("password");

%>



<title>로그인</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
	<form action="/myInfo" method="POST">

		<table>
			<tr>
				<td><h2>내정보</h2></td>
			</tr>
			<tr>
				<td>
					<div id="container">
						<ul class="tabs">
							<li class="active" rel="tab1">나의 정보</li>
							<li rel="tab2">비밀번호 변경</li>
						</ul>
						<div class="tab_container">
							<div id="tab1" class="tab_content">
								<ul>
									<table>
										<tr>
											<td>아이디</td>
											<td id="email"><%= email %></td>
										</tr>
										<tr>
											<td>나이 관심지역</td>
											<td>관심지역 넣기</td>
										</tr>
									</table>
								</ul>
							</div>
							<!-- #tab1 -->
							<div id="tab2" class="tab_content">
								<table>
									<tr>
										<td>현재 비밀번호</td>
										<td><%= password %></td>
									</tr>
									<tr>
										<td>변경할 비밀번호</td>
										<td><input type="password" name="pw" id="pw" onkeyup="passwordCheck()" placeholder="변경할 비밀번호를 입력해 주세요"></td>
									</tr>
									<tr>
										<td>변경할 비밀번호 확인</td>
										<td><input type="password" name="pw2" id="pw2" onkeyup="passwordCheck()" placeholder="다시 한번 입력해주세요"></td>
									</tr>
									<tr>
										<td>
										<h5 style="coloer:red;" id="passwordCheckMessage"></h5>
										</td>
									</tr>
									<tr>
										<td>
										<input type="Button" id="changePw" name="changePw" value="변경하기"/>
										</td>
									</tr>
								</table>
							</div>
							<!-- #tab2 -->
							<!-- .tab_container -->
						</div>
						<!-- #container -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>