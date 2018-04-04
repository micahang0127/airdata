<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	
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
  chart.draw(data, options);
}

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
    	var url = '/airdata/rt/' + stationId ;
    	/*
    	 * 자바스크립트로  페이지 이동할때 사용하는 코드
    	 */ 
    	location.href = url;
    });
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
	<select name="location" id="location">
		<option value="">[관측소]</option>
		<c:forEach items="${stations}" var="s">
			<option value="${s.seq}">${s.location}</option>
		
		</c:forEach>
	</select>
	
		
		
		${station.region} > ${station.location}
		<div id="pm_chart" style="height: 300px"></div>
		<table class="table">
		<tr>
			<td>관측시간</td>
			<td>PM2.5</td>
			<td>PM10</td>
			<td>등급</td>
		</tr>
		<c:forEach items="${pmdata}" var="pm">
		<tr>
			<td>${fn:substring(pm.time, 11,16)}</td>
			<td>${pm.pm25}</td>
			<td>${pm.pm10}</td>
			<td>XX</td>
		</tr>
		</c:forEach>
		</table>
		</div>
	</div>
</div>
</body>
</html>