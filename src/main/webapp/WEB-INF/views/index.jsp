<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<title>TITLE_HERE</title>
<script type="text/javascript">
	
var ctxpath = '${pageConext.request.contextPath}'; 

var main_data = '${datajson}';	// return index.jsp를 하는 Controller부분에서 model에 addAttribute 한 데이터 (스크립트로)가져오기
/*
 "${data}" = [Pmdata [pm10=44, pm25=30, time=2018-06-17 18:00:00.0]중구, Pmdata[..]..,.... ]  
 	-> main_data[1] => P ,  main_data[2] => m ....
 	
   main_data =  {"data" : [{"pm10":"44","pm25":"30","time":"2018-06-17 18:00:00.0","station":360,"stationName":"중구","lat":37.564639,"lng":126.975961,"pm10Value":44.0,"pm25Value":30.0},...]}	
 */
 
 //var srcData = JSON.parse( main_data); 
 var sido = '${sido}';
 //var sido = ["서울", "경기", "강원", "대전", "광주", "부산", "충북", "충남", "전북", "전남", "경북", "경남"];
 var sido_OneData = '';


function realTime_data_avg(sido_OneData){
	
	
	main_data.pm10;
	
	
	console.log("main_data는 " + main_data);
	//console.log("인덱스 "+ main_data.data[1].pm10);
	
	

	
	
}

$(function(){
	realTime_data_avg();
});


/* function getSido(sido_OneData){
	

	
	$.ajax({
		url : "/sidoOne" + sido_OneData ,
		method : 'GET',
		success :  function( sidoOne){
			console.log('sido_OneData도 확인(스크립트쪽)'+sido_OneData);
			console.log('sidoOne 확인(controller 리턴값)'+ sidoOne);
			
		}
			
		
	});
	
	return sido_OneData;
	
	 */
}	



function main ( ){
	
	$.ajax({
		
		url : ctxpath + '/main_real_time_data',
		method : 'GET',
		success : function( aa ){		// aa(맘대로 지정)에는 해당 controller에서 return값이 들어있다.
			var loc = $('.all').empty();
			var templet = '<div class="thumbnail" style="width:100px;height:100px; float:left; margin-right:10px;"><h4><a href="#" id="sido">{sido}</a></h4><p id="grade">{grade}</p><p id="pm10">{pm10}(pm10)</p><p id="pm25">{pm25}(pm2.5)</p></div>';
			var tem = aa;
			for(i=0; i < sido.length; i++ ){
				
				var html = templet.replace('{sido}', getSido(sido[i]))
									.replace('{grade}',realTime_data_avg(sido[i]).msg )
									.replace('{pm10}', realTime_data_avg(sido[i]).pm10)
									.replace('{pm25}', realTime_data_avg(sido[i]).pm25)
				
				loc.append(html);
				
			}
			
			
		}
	});
	
}
 

</script>
</head>
<body>
<!-- 곹통 네비게이션 -->
<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>

<h3>실시간 현황</h3> 
※각 지역의 평균값으로 표시됩니다.(등급  pm10기준)
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
		 <div class="all">
			<!--
			<div class="thumbnail" style="width:100px;height:100px; float:left; margin-right:10px;">
				<h4><a href="#" id="sido">도명</a></h4>
				<p id="grade">grade</p>
				<p id="pm10">pm10</p>
				<p id="pm25">pm25</p>
			</div> 
				-->
		</div>
	</div>
</div>
</body>
</html>