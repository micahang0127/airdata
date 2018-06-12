<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">


function gradePm25( value ) {
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


	
var src = '${pmjson}';

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	var srcData = JSON.parse ( src );
	/*
	 srcData.data = [ { ....}, { ....}, {....} ]
	*/
	var pmData = [
	    ['Time', 'PM2.5', 'PM10'],
	 
	 ];
	
	for(i= 0; i< srcData.data.length; i++){
		// 2018-03-31 19:00:00.0
		var hh = srcData.data[i].time.substring(11, 16);
		var pm10 = parseInt(srcData.data[i].pm10);
		var pm25 = parseInt(srcData.data[i].pm25);
		pmData.push ( [hh, pm25, pm10] );	
	}
	
	pmData.push ( ['ddd', 32, 44 ] );
	/*
	   ['11:00',  45,      40],
	    ['10:00',  11,      46],
	    ['09:00',  66,      12]
	*/
	
  var data = google.visualization.arrayToDataTable(pmData);

  var options = {
    title: '미세먼지',
    curveType: 'function',
    legend: { position: 'bottom' } ,
    chartArea: { width : '100%', height: '80%'}
  };

  var chart = new google.visualization.LineChart(document.getElementById('pm_chart'));
  console.log(document.getElementById('pm_chart'));
  
  chart.draw(data, options);
}

function resize () { /*  차트 사이즈 맞추기 (인터넷 창 줄였다 최대화 할 때마다 차트크기를 알맞은 크기로 자동 변환되어 차트도 같이 늘어졌다 줄어들었다 하도록) */
    //var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    // chart.draw(data, options);
    drawChart();
}
window.onresize = resize;
</script>
<script type="text/javascript">
var ctxpath = '${pageContext.request.contextPath}';
$(function() {
	$('#sido').val('${station.region}');
    $("#location").val("${station.seq}");

    // $("select[name=sido]").change(function() {
    $("#sido").change(function() {
        var temp1 = $("select[name=location]");
        var sido_val = $(this).val();
        $.ajax({
        	url : ctxpath + '/stations/' + sido_val ,
        	method : 'GET',
        	success : function ( res ) {
        		console.log ( res );
        		var loc = $('#location').empty()
        		              .append('<option value="">[관측소]</option>');
        		var template = '<option value="@v">@t</option>';
        		for ( var i = 0 ; i < res.length ; i ++ ) {
        			var html = template.replace('@v', res[i].seq)
        			                   .replace('@t', res[i].location);
        			loc.append ( html );
        		}
        	}, 
        	error : function ( xhr, state, error ) {
				console.log ( xhr );        		
        	}
        });
        
    }); // end region
    
    
    $("#location").change(function(){
    	var stationId = $(this).val() ;
    	var url = ctxpath + '/rt/' + stationId ;
    	/*
    	 * 자바스크립트로  페이지 이동할때 사용하는 코드
    	 */ 
    	location.href = url;
    });
    
    $('.nav-tabs a[href="#station-map"]').on('shown.bs.tab', function() {
    	
    	var p = document.location.href.lastIndexOf('/'); /* 마지막 /(슬래쉬) */
    	var sseq = document.location.href.substring(p+1); /* (p+1) -> 마지막슬래쉬 다음(+1) 부터 substring */
    	
    	$.ajax ({
    		url : ctxpath + '/api/station/' + sseq ,
    		method : 'GET',
    		success : function( res ){
    			console.log ( res );
				drawStation( res );
    		}
    	});
    })

});
function drawStation ( station ) {
	// station.lat, station.lng;
	// map.setCenter(station.lat, station.lng);
	var container = document.getElementById('map');
	var options = {
		center: new daum.maps.LatLng(station.lat, station.lng),
		level: 6
	};
	var map = new daum.maps.Map(container, options);
	
	// 지도를 클릭한 위치에 표출할 마커입니다
	var marker = new daum.maps.Marker({ 
	    // 지도 중심좌표에 마커를 생성합니다 
	    position: map.getCenter() 
	}); 
	// 지도에 마커를 표시합니다
	marker.setMap(map);
}
/* 
function loadSidoData( res ){
	$.ajax({
		url : ctxpath + 'api/region/rt' + sido, 
		method : 'GET',
		success : function ( res ){
			console.log(res);
			var loc = $('#location_val > tbody').empty();   $('')에   html소스부분 씀 
			var template = '<tr><td>{time}</td><td>{pm25}</td><td>{grade_pm25}</td><td>{pm10}</td><td>{grade_pm10}</td></tr>';
			var tmp = res; 
			for ( var i = 0; i < res.length ; i++){
				var html = template.replace('{time}', tmp[i].time)
								 	.replace('{pm25}', tmp[i].pm25)
								 	.replace('{grade_pm25}', gradePm25(tmp[i].pm25).msg)
								 	.replace('{pm10}', tmp[i].pm10)
								 	.replace('{grade_pm10}', gradePm10(tmp[i].pm10).msg);
				loc.append(html);
			}
		},
		error : function ( xhr, state, error ){
			console.log( xhr );		
			
		}
	});
	
	
}
 */
</script>

<title>[시도명]관측소</title>
</head>
<body>	<!-- 각 시도별 중 관측소까지 세부파악 페이지. -->
<!-- 곹통 네비게이션 -->
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
		<!--
		[시도명] - [관측소] 
		 -->
 		<select name="sido" id="sido">
			<option value="">지역</option>
			<!-- <option value="서울">서울</option> -->
			<c:forEach items="${sido}" var="region" >
			<option value="${region}">${region}</option>
			</c:forEach>
		</select>
	<select name="location" id="location">
		<option value="">[관측소]</option>
		<c:forEach items="${stations}" var="s">
			<option value="${s.seq}">${s.location}</option>
		
		</c:forEach>
	</select>
	
	<ul class="nav nav-tabs">
	    <li class="active"><a data-toggle="tab" href="#pm-chart">차트</a></li>
	    <li><a data-toggle="tab" href="#station-map">지도</a></li>
  	</ul>
	<div class="tab-content">
    <div id="pm-chart" class="tab-pane fade in active">
      <%-- ${station.region} > ${station.location} --%>
	  <div id="pm_chart" style="height: 300px"></div>
    </div>
    <div id="station-map" class="tab-pane fade">
    	<div id="map" style="width:100%;height:300px;"></div>
    </div>
  </div>
		<table class="table" id="location_val">
		<thead>
			<tr>
				<th>관측시간</th>
				<th>PM2.5</th>
				<th>등급(pm2.5)</th>
				<th>PM10</th>
				<th>등급(pm10)</th>
			</tr>
		</thead>
		<tbody>
 		<c:forEach items="${pmdata}" var="pm">
		<tr>
			<td>${fn:substring(pm.time, 11,16)}</td>
			<td>${pm.pm25}</td>
			<td>xx</td>
			<td>${pm.pm10}</td>
			<td>xx</td> 
		</tr>
		</c:forEach>
		</tbody>
		</table>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=89650c8b86f387b1efdedfc796012e1d"></script>
<script>


</script>
</html>