<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">

 @media (min-width: 768px){    /*  최대 768px, 768px가 안되는 경우, 768px보다 작은 경우   */
	button > .icon-bar{
	background-color : #EAEAEA!important;
	}
 } 
/* .button-menu .icon-bar {
	background-color: #000000;
}
 */

</style>
<nav class="navbar navbar-default navbar-fixed-top drop-shadow">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="navbar-brand">
				<button type="button" id="btn-menu" class="button-menu" data-target="#menu-body" >
			        <span id="icon-bar" class="icon-bar"></span>
			        <span id="icon-bar" class="icon-bar"></span>
			        <span id="icon-bar" class="icon-bar"></span>                        
			      </button>
				<div class="navbar-brand-elem"><a href="<c:url value="/"/>">맑은 하늘</a></div>
			</div>
		</div>
		<c:if test="${not empty LOGIN_USER }">
		<div class="navbar-login">
			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="<c:url value="/myInfo"/>"><span class="glyphicon glyphicon-user" aria-hidden="true"></span><span class="link-text">내정보</span></a></li>
			</ul>
		</div>
		</c:if>
		<div class="navbar-collapse collapse" id="menu-body">
			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="<c:url value="/"/>"><span class="glyphicon" style="width:16px;height:16px;display:inline-block;" aria-hidden="true"></span><span class="link-text">Home</span></a></li>
				<li class=""><a href="<c:url value="/region/rt/서울"/>"><span class="glyphicon" style="width:16px;height:16px;display:inline-block;" aria-hidden="true"></span><span class="link-text">구동관측소</span></a></li>				
				<li class=""><a href="<c:url value="/rt/360"/>"><span class="glyphicon" style="width:16px;height:16px;display:inline-block;" aria-hidden="true"></span><span class="link-text">시도</span></a></li>
				<c:if test="${empty LOGIN_USER }">
				<li><a href="<c:url value="/register"/>"><span class="glyphicon glyphicon-cloud" aria-hidden="true"></span><span class="link-text">가입하기</span></a></li>
				<li><a href="<c:url value="/login"/>"><span class="glyphicon glyphicon-log-in" aria-hidden="true"></span><span class="link-text">로그인</span></a></li>
				</c:if>
				<c:if test="${not empty LOGIN_USER }">
				<li><a href="<c:url value="/logout"/>"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span><span class="link-text">로그아웃</span></a></li>
				</c:if>
				<li><a href="<c:url value="/region/rt/서울"/>"><span class="glyphicon glyphicon-cloud" aria-hidden="true"></span><span class="link-text">미세먼지현황(36분 전 같이시간표시))</span></a></li>
			</ul>
		</div>
	</div>
</nav>
<div style="height:50px"></div>
<script type="text/javascript">
$(document).ready( function () {
	
	/* 
	[1]
	오른쪽 상단 button(#btn-menu) 는 이하에 글씨는 창의 크기에 따라 자리를 이동 (bootstrap css로 조절. 창이 작을땐 버튼 클릭밑으로 들어가고 창이 커지면 옆으로 옮겨 상단에 나영됨)
	  							이때, 창이 커지면서 옆으로 메뉴들이 이동하면 버튼(#btn-menu)는 작동이 안되는 식으로 구현 된듯 하다. ( #menu-body ::before로 바뀜,  .nav navbar-nav navbar-right ::before로 바뀜.( 창이 커질때 , 작을 땐 둘다 after 이였다가. )") 
																										( -> 이것이 글씨가 보일 때의 코드인듯.)	
	=> 클릭으로 메뉴가 보일때 : 윗줄 처럼 그냥 메뉴글씨들이 옆으로 이동  (창크기에 상관 없이  #menu-body ::before,  .nav navbar-nav navbar-right ::before) 
	=> 클릭으로 메뉴가 안보일 때 ( .collapse 가 붙은 경우 ) : 1. 창이 클 때 (메뉴들이 상단에 나열) -> 윗줄처럼 before, before  -> (가 써져야 메뉴글씨들이 보이는 듯) 
												2. 창이 작을 때 (메뉴들이 아무데도 안보이는 상황) -> 두부분에  아무것도 없다. (before,after도 안써짐) -> 그러다 창이 다시 커지면 다시 before,before 이 생김. 
												
	
	
	[2]							
	 밑 JQuery 로  button(#btn-menu)을 클릭하고 할때 마다 toggleClass()[-> 이는 해당 클래스명이 있으면 지워지고, 없어면 반대로 써주는 메소드이다.] 를 이용해, 
	.collapse  (붕괴하다) 를  div#mevu-body 에 붙였다 떼어 
				[ div#mevu-body.navbar-collapse /  div#mevu-body.navbar-collapse.collapse  ]
	 로 바꿔주어 실행 
	 그리고, bootstrap css 부분에서 아래처럼 해서 색깔 바탕 내려옴(길이)와 내용(메뉴글씨들안보이게)사라짐을 사용하여 구현함. 

	 .collapse {			// -> button(#btn-menu)버튼을 클릭했을 때 메뉴들이 다시 안보이도록 
     display: none;       	// -> 상단 바 하늘색이 다시 올라가게(짧게, 얇게) 해준다. (창이 작을 때 글씨를 내리면 넓게 하늘색 바탕이 길어지므로)
     visibility: hidden; 	// -> 메뉴(글씨)들이 안보이게 함. 
	}						
	 
	
	=>  즉,  before,before 이면 메뉴 글씨들이 보이는 것!!! 											
												
	*/
	 var color = $('button > .icon-bar').css('background-color');
	
	$('#btn-menu').on('click', function(e) { // $()  ==  $(document).ready(function(){   } ); 
		var clickedButton = e.target;  			/* e.target 은  클릭한 것 ( 이벤트를 발생 시킨것 ) 을 가리킨다  */
		var menu = $( $(e.target).data('target'));
		 // toggleClass : 붙어있으면 떼고, 없으면 붙이고!
			menu.toggleClass('collapse');
		

		// $('button > .icon-bar').css('background-color','red');
		console.log(color);
		
		if(color == 'rgb(0, 0, 0)'){		// 검은색 
			
		
			$('button > .icon-bar').css('background-color','#EAEAEA');
			
			color = $('button > .icon-bar').css('background-color');
			console.log(color);


		}else if(color == 'rgb(234, 234, 234)'){			// 회색
			$('button > .icon-bar').css('background-color','rgb(0, 0, 0)');
		
			color = $('button > .icon-bar').css('background-color');
			
			
		} 
		
		
			
		//if($('.container-fluid').hasClass('navbar-collapse collapse') == true){
			
	
		
	});
		
	
	
});
</script>