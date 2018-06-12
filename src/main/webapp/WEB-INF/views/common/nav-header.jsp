<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-fixed-top drop-shadow">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="navbar-brand">
				<button type="button" id="btn-menu" class="button-menu" data-target="#menu-body" >
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
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
	
	$('#btn-menu').on('click', function(e) { // $()  ==  $(document).ready(function(){   } ); 
		var clickedButton = e.target;
		var menu = $( $(e.target).data('target'));
		 // toggleClass : 붙어있으면 떼고, 없으면 붙이고!
			menu.toggleClass('collapse');
		
	});
});
</script>