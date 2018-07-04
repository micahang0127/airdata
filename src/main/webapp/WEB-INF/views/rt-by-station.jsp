<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<style type="text/css">

select{
	height: 25px;
}
.select_div{
	margin: 10px;

}
a > i, a.no_f{
	color: #DDD;
}
a.favorite > i{
	color : #F00;
}

.dataFail{
	width: 80%;
	text-align: center;
	padding-top: 30px;
	color: #ca3016;
}

</style>

<script type="text/javascript">
/* ★★★   html페이지가 먼저 로드 된 후 !!!! script 실행된다  ( 데이터 삽입할 때 참고. )*/


	
var src = '${pmjson}';
	/*src = 
		{ "data": [{"pm10":"16","pm25":"9","time":"2018-06-13 02:00:00.0","station":360,
					"stationName":null,"lat":0.0,"lng":0.0,"pm10Value":16.0,"pm25Value":9.0}, {....}, ....
				   ]}
	*/
var srcData = JSON.parse ( src );
	/*
	srcData.data = [ { ....}, { ....}, {....} ]
	
	*/

	
	
function addStation ( stationId, anchor ) {
	var pm10 = 80;
	var pm25 = 40;
	
	$.ajax({
		url : ctxpath + '/favorstation/add',
		method : 'POST',
		data : {
			station: stationId
		},
		success : function(res){
			console.log ( res );
		//	 [res:success, station : { ... }, pm10 : 80, pm25:35 ]
			if( res.success){
				console.log('anchor확인'+ anchor);
				anchor.removeClass('no_f');
				anchor.addClass('favorite');
				alert('관심지역에 추가되었습니다.');
			}else{
				alert('추가에 실해 하였습니다 . \n 로그인 여부를 확인해 주세요.');
			}
		}
	});
}	
	
function removeStation ( stationId, anchor ) {
	 
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
				console.log('anchor확인'+ anchor);
				anchor.removeClass('favorite').addClass('no_f');
				alert('관심지역이 취소되었습니다.');
			}else{
				alert('관심등록 해제에 실패했습니다.\n 로그인 여부를 확인해 주세요.');
				
			}		
		}
	 });
}	
	
if(srcData.data[0] == null){
	
	console.log('차트에 필요한 데이터가 없습니다');

	
}
else{
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawChart); 
}
function drawChart() {
	
	
	var pmData = [
	    ['Time', 'PM2.5', 'PM10'],
	 
	 ];
	

		for(i= srcData.data.length-1; i >= 0; i--){
			// 2018-03-31 19:00:00.0
			var hh = srcData.data[i].time.substring(11, 16);
			var pm10 = parseInt(srcData.data[i].pm10);
			var pm25 = parseInt(srcData.data[i].pm25);
			pmData.push ( [hh, pm25, pm10] );	
		}
		
		pmData.push ( ['-', 0, 1 ] );
		
		 /*   ['11:00',  45,      40],
		    ['10:00',  11,      46],
		    ['09:00',  66,      12] 
		
		 */
	
	
  var data = google.visualization.arrayToDataTable(pmData);
	

  var options = {
    title: '미세먼지',
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
    	 //loadSidoData( stationId );
		
    	
    	location.href = url;
    });
    
    
	    //관심등록 여부 확인 
	    var favors = ${favorites};
	    console.log('favorites확인'+favors);
	    
		 // ★★★  배열 안(을 돌며) 특정 값이 있는지? => script의 배열 for문.
	    var find = favors.find(function(elem){	
	    	return '${station.seq}' == elem;		// true / false
	    });
	    if( find ){
	    	$('.select_div > a').attr('class','favorite');
	    }else{
	    	$('.select_div > a').attr('class','no_f');	
	    }
    
	    
	    // 관심등록 클릭 제거
	    $('.select_div > a > i').on('click',function(e){
	    	e.preventDefault(); 					// a태그로 새로운 url로 페이징 로딩이 안되게 해줌. 
			// console.log('별클릭');
	    	var icon = $(e.target);
	    	var anchor = icon.parent();
	    	if( anchor.hasClass('favorite')){
	    		
	    		var id = anchor.attr('id');
	    		removeStation( id.substring(0), anchor );
	    	} else {
	    		var id = anchor.attr('id');
	    		addStation( id.substring(0), anchor );
	    	}
	    	
	    	
	    	
	    });
	     

	    
	    
	    
   		
	    // 데이터  table ajax로 불러옴.
	    loadSidoData( '${station.seq}' );
		
    

    
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


function loadSidoData( stationId ){
	$.ajax({
		url : ctxpath + '/api/station/rt/' + stationId, 
		method : 'GET',
		success : function ( res ){
			console.log('res.data'+ res.data);
			var loc = $('#location_val > tbody').empty();   /*   $('')에   html소스부분 씀     */
			var template = '<tr><td>{time}</td><td>{pm25}</td><td style="color:{color25G}">{grade_pm25}</td><td>{pm10}</td><td style="color: {color10G}">{grade_pm10}</td></tr>';
			var tmp = res.data; 
			
			if(tmp[0]== null){
				
				
				var locCh = $('.nav-tabs').empty();
				var templateCh = '<li class="active"><a data-toggle="tab" href="#station-map">지도</a></li>';
				locCh.append(templateCh);
				
				 
				console.log('관측소 데이터 없음');
				//alert('해당 관측소의 데이터가 확인 되어지지 않습니다.');
				template = '<tr><td></td><td></td><td class="dataFail"><br/>해당 관측소의 데이터가 확인 되어지지 않습니다.</td><td></td><td></td></tr>'
				//var html = '<tr style="text-align: center;"><td>관측소의 데이터가 확인 되어지지 않습니다.</td></tr>'
				loc.append(template);
				
				
			}else{
				for ( var i = 0; i < res.data.length ; i++){
						
						var html = template.replace('{time}', tmp[i].time.substring(0,16))
										 	.replace('{pm25}', tmp[i].pm25)
										 	.replace('{color25G}', gradePm25(tmp[i].pm25).color)
										 	.replace('{grade_pm25}', gradePm25(tmp[i].pm25).msg)
										 	.replace('{pm10}', tmp[i].pm10)
										 	.replace('{color10G}', gradePm10(tmp[i].pm10).color)
										 	.replace('{grade_pm10}', gradePm10(tmp[i].pm10).msg);
						loc.append(html);
					}
			}
		},
		error : function ( xhr, state, error ){
			console.log( xhr+','+state+'.'+error );		
			
		}
	});
	
	
}
 
/* ★★★   [등급구현  ]
	ajax로 등급만 재 로드 하면 안됨. html테이블과 pm25,pm10 데이터가 다 로드가 된 후 ajax로 등급만 하니 등급의 한 값씩 td에 넣지 못하고 한꺼번에(좋음,보통,좋음...전체가) 하나의 td 칸에 들어감 ㅠㅠ 
	console.log("html에 나열된 pm25데이터"+$('#pm25').text());  => 이렇게 가져오면 (X). td첫줄 값에 있는 데이터만 가저옴	
	$("#grade_25").html(gradePm25_msg); //=> (역시나 X). html 첫출(td)에만 들어감.ㅠ
	 ★★★  javaScript 변수를 < % % > 로 자바코드로 넘겨 줄 수 없음!!! ( 반대로는 가능  )
	 ★★★ $('#grade_25') 로 하면 첫줄만 나옴 ㅠ 왠지 모름 ㅠㅠ 
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
		 <div class="select_div">
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
		&nbsp;&nbsp;
		<a id="${station.seq}" class="{f}" href="#"><i class="fas fa-star"></i></a> 
		</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#pm-chart" >차트</a></li>
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
		<%--  		
			<c:forEach items="${pmdata}" var="pm">
		<tr>
			<td>${fn:substring(pm.time, 11,16)}</td>
			<td >${pm.pm25}</td>
			<td id="grade_25"></td> 	 <!--	0.DB에 등급칼럼 데이터가 없음으로 => 1. 콘트롤러에서 for문으로 정의(but, for문이 길고, sido페이지에서도 쓰이므로 common-head.jsp에서 따로 정의해 incluce사용. 2. 등급부분만 따로 빼어 ajax이용함.(X) // ajax로 등급만 재 로드 하면 안됨. html테이블과 pm25,pm10 데이터가 다 로드가 된 후 ajax로 등급만 하니 등급의 한 값씩 td에 넣지 못하고 한꺼번에(좋음,보통,좋음...전체가) 하나의 td 칸에 들어감 ㅠㅠ  -> 이문제로  rt-by-sido.jsp  에서는 테이블 전체(전체 데이터)를 한꺼번에 ajax로 처리함.   -->
			<td >${pm.pm10}</td>
			<td id="grade_10"></td>
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
<script>


</script>
</html>