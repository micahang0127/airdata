<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript">
var ctxpath = '${pageContext.request.contextPath}';
/* function gradePm25( value ) {
	// gradePm25(34);
	// 없음 : 0 [0, 0]
	// 좋음 : 1 [1, 15]
	// 보통 : 2 [16, 50]
	// 나쁨 : 3 [51, 100]
	// 매우나쁨 : 4 [101 ~ )
	
	var pm25 = value;
	var level;
	var msg;
	
	if(pm25 ==0){
		level = 0;
		msg = "측정불가";
	}
	else if(pm25 <=15 && pm25 >= 0){
		level = 1;
		msg = "좋음";
	}else if(pm25 <=50 && pm25 >=16){
		level = 2;
		msg = "보통";
	}else if(pm25 <=100 && pm25 >=51){
		level = 3;
		msg = "나쁨";
	} else if ( pm25 >= 101 ) {
		level = 4;
		msg = "매우나쁨";		
	} else{
		throw Error('이상한 값:' + value);
	}
	return { level : level , msg : msg };
}
function gradePm10 ( value ) {
	// gradePm10(34);
	// 없음 : 0 [0 , 0]
	// 좋음 : 1 [0, 30]
	// 보통 : 2 [31, 80]
	// 나쁨 : 3 [81, 150]
	// 매우나쁨 : 4 [151 ~ )
	
	var level;
	var msg;
	if(value ==0){
		level = 0;
		msg = "측정불가";
	} else if(value <=30 && value >= 0){
		level = 1;
		msg = "좋음";
	}else if(value <=80 && value >=31){
		level = 2;
		msg = "보통";
	}else if(value <=150 && value >=81){
		level = 3;
		msg = "나쁨";
	} else if ( value >= 151 ) {
		level = 4;
		msg = "매우나쁨";		
	} else{
		throw Error('이상한 값:' + value);
	}
	return { level : level , msg : msg };
	
}
 */
 function loadSidoData ( sido ) {
	$.ajax({
    	url : ctxpath + '/api/region/rt/' + sido ,
    	method : 'GET',
    	success : function ( res ) {
    		console.log ( res );
    		var loc = $('#location > tbody').empty();
    		var template = '<tr><td><a href="/airdata/rt/{seq}">{name}</a></td><td>{pm25}</td><td>{grade_pm25}</td><td>{pm100}</td><td>{grade_pm100}</td></tr>';
    		var tmp = res;
    		for ( var i = 0 ; i < res.length ; i ++ ) {
    			var html = template.replace('{seq}', tmp[i].station)
    			                   .replace('{name}', tmp[i].stationName)
    			                   .replace('{pm25}', tmp[i].pm25)
    			                   .replace('{grade_pm25}', gradePm25(tmp[i].pm25).msg)
    			                   .replace('{pm100}', tmp[i].pm10 )
    			                   
    			                   .replace('{grade_pm100}', gradePm10(tmp[i].pm10).msg );
    			loc.append ( html );
    		}
    	}, 
    	error : function ( xhr, state, error ) {
			console.log ( xhr );        		
    	}
    });
}
$(function() {
	$('#sido').val('${station.region}');
    // $("#location").val("${station.seq}");

    // $("select[name=sido]").change(function() {
    $("#sido").change(function() {
        var temp1 = $("select[name=location]");
        var sido_val = $(this).val();
        loadSidoData(sido_val);
    }); // end region
    
    
    $("#location").change(function(){
    	var stationId = $(this).val() ;
    	var url = '/airdata/rt/' + stationId ;
    	/*
    	 * 자바스크립트로  페이지 이동할때 사용하는 코드
    	 */ 
    	location.href = url;
    });
    
    loadSidoData ( '${sidoName}' );
});
</script>






<title>[시도명]관측소</title>
</head>
<body>
<!-- 곹통 네비게이션 -->
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12 col-sm-6">
		<!--
		[시도명] - [관측소] 
		 -->
 		<select name="sido" id="sido">
			<option value="">카테고리1</option>
			<!-- <option value="서울">서울</option> -->
			<c:forEach items="${sido}" var="region" >
			<option value="${region}">${region}</option>
			</c:forEach>
		</select>	
		
		
		<h3>${region}</h3>
		<table class="table" id="location">
		<thead>
			<tr>
				<th>관측소</th>
				<th>PM2.5</th>
				<th>등급(pm2.5)</th>
				<th>PM10</th>
				<th>등급(pm10)</th>
			</tr>
		</thead>
		<tbody>
			<%--
			<c:forEach items="${data}" var="pm">
			<tr>
				<td><a href="${pageContext.request.contextPath}/rt/${pm.station}">${pm.stationName}</a></td>
				<td>${pm.pm25}</td>
				<td>XX</td>
				<td>${pm.pm10}</td>
				<td>XX</td>
			</tr>
			</c:forEach>
			 --%>
		</tbody>
		</table>
		</div>
	</div>
</div>
</body>
</html>