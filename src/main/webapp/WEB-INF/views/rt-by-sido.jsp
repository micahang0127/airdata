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
    		var loc = $('#location > tbody').empty(); /* $('')에   html소스부분 씀 */
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
    		drawStation(res);
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
    
    
function drawStation ( stations ) {
	// station.lat, station.lng;
	// map.setCenter(station.lat, station.lng);
	// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
	var bounds = new daum.maps.LatLngBounds();  
	
	var container = document.getElementById('map');
	var options = {
		center: new daum.maps.LatLng(stations[0].lat, stations[0].lng),
		level: 7
	};
	
	var map = new daum.maps.Map(container, options);
	
	var infowin =  new daum.maps.InfoWindow({ 
		    content : '',
		    removable : true
	});
	
	for(var i=0; i<stations.length; i++){
		// 공공API에서 위도가 경도보다 큰 데이터가 들어옴. 보정해줌		
		var lat = Math.min(stations[i].lat, stations[i].lng);
		var lng = Math.max(stations[i].lat, stations[i].lng);
		if(lat==0 && lng==0){
			continue;
		}
		var marker = new daum.maps.Marker({ 
		    position: new daum.maps.LatLng(lat, lng),
		    clickable : true,
		    title : stations[i].stationName
		}); 
		// 지도에 마커를 표시합니다
		marker.setMap(map);
		
		// LatLngBounds 객체에 좌표를 추가합니다
	    bounds.extend(marker.getPosition());
		
	 // 마커에 클릭이벤트를 등록합니다
	 // javascript 클로저가 문제를 일으킴!
	 	/*
	 	 ( function( m, s ) {
		    daum.maps.event.addListener(m, 'click', function() {
		    	infowin.setContent( s.stationName );
		    	infowin.open(map, m);
		    });
		 }) ( marker, stations[i] );
	 */
	     addMarker ( map,infowin, marker, stations[i] );
	}
	map.setBounds(bounds);
}

function addMarker  (map, infowin, marker, station ) {
    daum.maps.event.addListener(marker, 'click', function() {
    	infowin.setContent( station.stationName );
    	infowin.open(map, marker);
    });
}

    
</script>






<title>[시도명]관측소</title>
</head>
<body>  <!-- 각 시도까지만 전체적으로 파악페이지  -->
<!-- 곹통 네비게이션 -->
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12 col-sm-6">
		<!--
		[시도명] - [관측소] 
		 -->
 		<select name="sido" id="sido">
			<option value="">지역</option>
			<!-- <option value="서울">서울</option>      sido = 서울,경기,강원,... / region = (서울안)중구, 은평구...같은 관측소-->
			<c:forEach items="${sido}" var="region" >
			<option value="${region}">${region}</option>
			</c:forEach>
		</select>	
		
		
    	<div id="map" style="width:100%;height:300px;"></div>
		
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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=89650c8b86f387b1efdedfc796012e1d"></script>
</html>