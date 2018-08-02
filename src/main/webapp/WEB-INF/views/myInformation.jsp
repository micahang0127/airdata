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


#tab2 #realContent_table{
	text-align: center;
}

#tab2 #realContent_table td{
	padding: 35px;
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


#pw, #pw2{
	width: 300px;
}



.modal-dialog{
	width: 300px;
}

.modal-body{
	padding-bottom: 0px;
}
.modal-footer{
	padding: 10px;
	text-align: left;
}

.dataFail{
	width: 80%;
	text-align: center;
	padding-top: 30px;
	color: #ca3016;
}


.slidecontainer {
    width: 100%;
    padding-top: 7px;
    padding-bottom: 10px;
}



.slider {
    -webkit-appearance: none;
    width: 100%;
    height: 15px;
    border-radius: 5px;
    background: #d3d3d3;
/*  background-image:-webkit-linear-gradient(to right, rea, black); */
     outline: none;
    opacity: 0.7;
    -webkit-transition: .2s;
    transition: opacity .2s;
}

.slider:hover {
    opacity: 1;
}

.slider::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 25px;
    height: 25px;
    border-radius: 50%;
    background: #4CAF50;
    cursor: pointer;
}

.slider::-moz-range-thumb {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    background: #4CAF50;
    cursor: pointer;
}

#station_M{
	padding-left: 90px;
}

.btn-remove{
	margin-left: 10px;
	margin-right: 75px;
}


/* .tab_container .tab_content ul li {
	padding: 5px; 
	list-style: none
}
 */

</style>
<script>
	var ctxpath = '${pageContext.request.contextPath}';
	var favAll = JSON.parse('${favAllbyUser}');

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

		//var favorites = '${favorites}'.split(",");

		var template = '<button type="button" class="btn btn-default" value="{fav}" >{favor}</button>';
		
		if(favAll.length == 0){
			var html = template.replace('{fav}', '-')
								.replace('{favor}', '관심지역이 없습니다');
			$('.btn-group').append(html);


		}else{	
			
			for(i=0; i < favAll.length; i++){
				
				var favor = favAll[i].location;
				var html = template.replace('{fav}', favor)
									.replace('{favor}', favor);
		
				$('.btn-group').append(html);
				
			}
		}
		

		
			
			var dbclick = false;
			$('.btn-group > button').bind({
				 
				
				click:function(event){				// single click 
					
					var favStr = $(this).attr('value');		// 클릭한 this 는 여기서(로직 위에서) 가져와야 하나봄 . 밑에선 값이 안 들어옴.
					
					setTimeout(function(){	
						if(dbclick == false){	

						// 여기서  single 로직 실행 
						
						
							for(i=0; i< favAll.length; i++){
								if( favAll[i].location == favStr ){
									
									return location.href = ctxpath + "/rt/" + favAll[i].station;
									
								}
							}
						
						
							//	if(favSet[i].indexOf( favStr ) ){		// 클릭한 명이 포함되어 있으면  해당 seq와 지역명을 가져옴 
						// single click 로직 끝
						
						event.stopPropagation();
						}	// if end (dbclick == false) 
					},200);	// setTimeout end 
				},		//  single click end
				
				
			
				dblclick:function(event){       	// double click 
					dbclick = true;
					var favStr = $(this).attr('value');	

					setTimeout(function(){
						dbclick = false;
					},300);	// setTimeout end

					//  여기서  double click 로직 구현
					
					
					for(i=0; i < favAll.length; i++){

						
						if( favAll[i].location == favStr){
						
							// modal 로 기준치 받기
		    				$('#Modal').modal("toggle");
		    	    		
		    				var slider10 = document.getElementById("Range10");
		    				var slider25= document.getElementById("Range25");
		
		    				var Scanf10= document.getElementById("Pm10Scanf");
		    				var Scanf25= document.getElementById("Pm25Scanf");
		    		
							$('#station_M').html(favAll[i].location);
		    				$(Scanf10).val(favAll[i].pm10Limit);
		    				$(Scanf25).val(favAll[i].pm25Limit);
		    				$(slider10).val(favAll[i].pm10Limit);
		    				$(slider25).val(favAll[i].pm25Limit);
		    				
		    				
		    				
		    				
		    				// !!! 모달창 range slider 사용함으로 사용자릂 편리하게.

		    				/*  바가 움직이는 값을 실시간으로 써줌(움직이는대로) */
		    				  Scanf10.oninput = function() {	
		    					 $(slider10).val(Scanf10.value);				 
		    				 } 
		    				  slider10.oninput = function() {	
		    					 $(Scanf10).val(slider10.value);
		    				 } 
		    				 slider25.oninput = function() {
		    					 $(Scanf25).val(slider25.value);
		    			 	}
		    				 /*  빈칸에 입력받는 값을 실시간으로 range slider(바) 값으로 표현해줌  */
		    				 Scanf25.oninput = function() {
		    				 	$(slider25).val(Scanf25.value);				 
		    				 }
		    				 
		    			//	var pm10Limit = favAll[i].pm10Limit; 
		    			//	var pm25Limit = favAll[i].pm25Limit; 
		    				var station = favAll[i].station; 
	    				 	
		    			
		    		
		    			 	$('#updataFav').click(function(){
		    			 		
								if(station != null){
		    						pmUpdata( Scanf10.value , Scanf25.value , station );
								}
								station = null;
								
		    					$('#Modal').modal('hide');
		    			 	
		    				});
		    			 	
		    			

		    				$('.btn-remove').click(function(){
		    				
		    					if(station != null){
	    				 			removeStation( station );
		    					}
	    				 		$('#Modal').modal('hide');
	    				 		window.location.reload();
	    				 	})
	    				 	 
	    				 
					
						}
					}
					
					
					
					
					event.stopPropagation();
					
				}		// dbclick end
				
			}); // bind end
			
				
		

	});	
	
	
	
	/*  수정으로 변경하기!!   controller에도 추가하기 */
	function pmUpdata ( pm10Limit, pm25Limit , station ) {
		
		console.log('upupup');
		
		$.ajax({
			url : ctxpath + '/pmUpdata',
			method : 'POST', 
			data : {
				pm10Limit: pm10Limit,
				pm25Limit: pm25Limit,
				station: station
			},
			success : function(res){
				console.log ( res );
			//	 [res:success, station : { ... }, pm10 : 80, pm25:35 ]
				if( res.success){
					alert('설정된 값 초과 시 메일로 알려드립니다.');
				}else{
					alert('추가에 실해 하였습니다 . \n 로그인 여부를 확인해 주세요.');
				}
			}
		});
	}	
		
	function removeStation ( stationId ) {
		 
		 console.log('removeStation진입 '+ stationId);
		 
		 $.ajax({
			url : ctxpath + '/favorstation/remove',
			method : 'POST',
			data : {
				station: stationId
			},
			success : function(res){
				console.log(res);
				if(res.success){
					console.log('관심등록 해제 성공 진입');
					alert('관심지역이 취소되었습니다.');
				}else{
					alert('관심등록 해제에 실패했습니다.\n 로그인 여부를 확인해 주세요.');
					
				}		
			}
		 });
	}	
	
	
	

 

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
															</div>
															<div id="text">※<b style="color: blue;">클릭</b>하면 해당 지역 24시 데이터를 볼 수 있습니다</div>
															<div id="text">※<b style="color: blue;">더블클릭</b>하면 메일 받는 기준 값을 수정할 수 있습니다.</div></td>
													</tr>
												</table>
											</ul>
										</div>
										
										
										<!-- 모달 창으로 기준값 받기 -->
										<div class="modal fade" id="Modal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true"> 
										  <div class="modal-dialog"> <!-- 모달 창 넓이 css -->
										    <div class="modal-content"> <!-- 모달 창 투명도 제로 css -->
										      <div class="modal-body">
										        <div class="form-group">
										            <label class="control-label">PM10(미세먼지)</label><b id="station_M"></b>
														<div class="slidecontainer">
 															 <input type="range" min="0" max="210" value="151" class="slider" id="Range10">
														</div> 
										           		<input type="text" class="form-control" id="Pm10Scanf"  placeholder="입력한 값 초과시 메일로 알려드립니다." value="">
										        </div>
										        <div class="form-group">
										        	<label class="control-label">PM2.5(초미세먼지)</label>
										         		<div class="slidecontainer">
										  					<input type="range" min="0" max="110" value="76" class="slider" id="Range25">
														</div>
										        	    <input type="text" class="form-control" id="Pm25Scanf"  placeholder="입력한 값 초과시 메일로 알려드립니다." value="">
										         </div>
										      </div>
										      	<div class="modal-footer">
										        	<button type="button" class="btn btn-remove" >삭제</button>
										        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										        	<button type="button" class="btn btn-primary" id="updataFav">수정</button>
										      	</div>
										    </div>
										  </div>
										</div>
										
										
										
										
										
										
										<!-- #tab1 -->
										<div id="tab2" class="tab_content">
											<table id="realContent_table">
												<%-- <tr id="tr1">
													<td id="td1">현재 비밀번호</td>
													<td id="PPass"><%=password%></td>
												</tr> --%>
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
													<td style="padding-top: 0px; padding-right: 100px;"><input type="Button" id="changePw" name="changePw"
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