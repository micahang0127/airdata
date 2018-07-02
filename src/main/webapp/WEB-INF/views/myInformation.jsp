<%@page import="github.hangming.airdata.model.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<style>
#all, #content_table, #realContent_table{
	width: 100%;
}

#blank_tr {
	height: 50px;
}


#title_td{
	padding-left: 20%;
}



#container{
	width: 60%; 
	margin-left: 20%;
	margin-top: 50px;
}


ul.tabs {
	margin: 0px; 
	/* float: left; */
	padding-left: 0px;
	list-style: none;
 	height: 40px; 
	border-left: 1px solid #eee; 
	width: 60%;  
	/* font-family: "dotum"; */
	font-size: 15px;
}


ul.tabs li {
	float: left;
	text-align: center;
	cursor: pointer;
 	width: 35%; 
 	height: 100%;  
	line-height: 31px;
	border: 1px solid #eee;
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
	clear: both;
 	width: 100%; 
 	height: 250px;
	background: #FFFFFF;
}


.tab_content {
	height: 100%;
	font-size: 15px;
	display: none;
}

.tab_content ul{
	height: 100%;
 	padding: 0px; 
	margin: 0px; 
}

#realCotent_table{
	height: 100%;
	text-align: center;
}

#tab1 #tr0{
	border-bottom: 2px solid #eee; 
	height: 20%;
}

#tab1 #td0{
	width: 25%;
}

#tab1 #realContent_table td{
	padding: 20px;
}



#tab2 #realContent_table td{
	padding-left: 40px;
	padding: 25px;
}

#button_tr{
	padding-top: 0px;
	text-align: right;
}

#text{
	font-size: 12px;
	color: green;
	padding-top: 10px;
}


/* .tab_container .tab_content ul li {
	padding: 5px; 
	list-style: none
}
 */

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

	$(function() {

		var favorites = '${favorites}'.split(",");

		var template = '<button type="button" class="btn btn-default" value="{fav}" >{favor}</button>';

		if (favorites.length == 1) {

			var html = template.replace('{fav}', '-')
							.replace('{favor}', favorites);
			$('.btn-group').append(html);

		} else {

			for (i = 1; i < favorites.length; i++) {

				var favor = favorites[i];
				
				var html = template.replace('{fav}', favor)
								.replace('{favor}', favor);

				$('.btn-group').append(html);

			}

		}
		
		
			
			$('.btn-group > button').click(function(){
				
				var favStr = $(this).attr('value');
				console.log('favStr'+favStr);			// 클릭한 명 가져옴 ('관심지역 없을 때는 '-'으로 표시됨')

				
				// !!! 프로그램 처리 줄임 포인트 
				if(favStr == '-'){ ///ddd
					console.log('관심지역이 없습니다.');
				
				}else{
					
						
					var favSet = JSON.parse('${favSetjson}');	// favSet = ["360,중구","361,한강대로","..",..."];
					console.log('${favSetjson}');									
					 
					for(i=0; i < favSet.length; i++){		// favSet[0] = 360,중구
						
						//console.log('favSet.length'+favSet.length);
						//console.log('favSet.length'+favSet[i]);
						
						
						// !!! 코드가 불필요한 for문을 돌지 않도록 해당 문자열이 같을 때만 이중 for문을 돌도록 하여 프로그램 운영을 줄임. 
						if(favSet[i].indexOf( favStr ) ){		// 클릭한 명이 포함되어 있으면  해당 seq와 지역명을 가져옴 
							
							var favSet_divide = favSet[i].split(",");	
							
							for(j=0; j < favSet_divide.length; j++){		/*  favSet_divide.length = 2
																			 	favSet_divide[0] = 360   ...[1] = 중구*/
								
								//console.log('favSet_divide.length'+favSet_divide.length);
								console.log('favSet_divide[j]'+favSet_divide[j]);
								
								if( favStr == favSet_divide[j] ){
									
									
									var favSet_Seq = favSet_divide[j-1];
									console.log('favSet_Seq'+favSet_Seq);
									console.log('여기');
									
									
									return location.href = ctxpath + "/rt/" + favSet_Seq;
									
								}
								
						}					
						
						
					}		// if end
					else{
						
						console.log('관심목록 없음도 아니고 포함된것도 아닙니다.(코드수정필요)');
						
					}
					
					
				}	 	//for end
		

					
			}
				
		});		// click end
				
		
		
		
		

	});	
	

 

	function passwordCheck() {
		var pw = $('#pw').val();
		var pw2 = $('#pw2').val();

		if (pw == pw2) {
			$('#passwordCheckMessage').html('동일합니다.');
		}
		if (pw != pw2) {
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		}

	}

	function changePassword() {

		var email = $('#email').text();
		var pw = $('#pw').val();
		var pw2 = $('#pw2').val();

		console.log('email값확인:' + email);

		if (pw == '') {
			alert('새 비밀번호를 넣어주세요')
		} else if (pw2 == '') {
			alert('새 비밀번호를 확인해 주세요')
		} else {
			$.ajax({
				type : 'POST',
				url : ctxpath + '/changePassword',
				data : {
					password : pw,
					email : email
				},
				success : function(result) {
					var res = JSON.parse(result);

					if (res.success) {
						alert('변경이 완료되었습니다.');
						location.href = ctxpath +"/index";
					} else {
						alert('변경에 실패하였습니다.')
					}
				}

			})

		}// end else

		return false;
	}

	$(document).ready(function() {
		console.log('비번변경 OK?');
		$('#changePw').on('click', function(e) {
			e.preventDefault(); //기본동작 작동하지 않게.. 굳이 필요한가..? 일단함..
			changePassword();

		});
	});
</script>

<%
	String email = (String) session.getAttribute("email");
	String password = (String) session.getAttribute("password");
%>



<title>로그인</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
	<form action="/myInfo" method="POST">
		<table id="all">
			<tr id="blank_tr">
				<td></td>
			</tr>
			<tr id="content_tr">
				<td>
					<table id="content_table">
						<tr id="title_tr">
							<td id="title_td"><h3><%=email%>님의 정보
								</h3></td>
						</tr>
						<tr id="menu_tr">
							<td>
								<div id="container">
									<ul class="tabs">
										<li class="active" rel="tab1">나의 정보</li>
										<li rel="tab2">비밀번호 변경</li>
									</ul>
									<div class="tab_container">
										<div id="tab1" class="tab_content">
											<ul>
												<table id="realContent_table">
													<tr id="tr0">
														<td id="td0">아이디</td>
														<td id="email"><%=email%></td>
													</tr>
													<tr id="tr0_f">
														<td id="td0">나의 관심지역</td>
														<td><br/><div class="btn-group" role="group"
																aria-label="...">
																<!-- <button type="button" class="btn btn-default">Left</button> -->
															</div><div id="text">※클릭하면 해당 지역데이터 보기로 갑니다.</div></td>
													</tr>
												</table>
											</ul>
										</div>
										<!-- #tab1 -->
										<div id="tab2" class="tab_content">
											<table id="realContent_table">
												<tr id="tr1">
													<td id="td1">현재 비밀번호</td>
													<td><%=password%></td>
												</tr>
												<tr id="tr1">
													<td id="td1">변경할 비밀번호</td>
													<td><input type="password" name="pw" id="pw"
														onkeyup="passwordCheck()" placeholder="변경할 비밀번호를 입력해 주세요"></td>
												</tr>
												<tr id="tr2">
													<td id="td1">변경할 비밀번호 확인</td>
													<td><input type="password" name="pw2" id="pw2"
														onkeyup="passwordCheck()" placeholder="다시 한번 입력해주세요">
														<div><h5 style="color: red;" id="passwordCheckMessage"></h5></div>
														
													</td>
												</tr>
												<tr id="button_tr">
													<td></td>
													<td><input type="Button" id="changePw" name="changePw"
														value="변경하기" /></td>
												</tr> 
										</table>
									</div> <!-- #tab2  -->
									</div> <!-- .tab_container  -->
								</div> <!-- #container -->
							</td>
						</tr>
					</table> <!-- #tab2 --> <!-- .tab_container -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>