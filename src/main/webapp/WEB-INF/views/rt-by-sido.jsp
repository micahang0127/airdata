<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>

<style type="text/css">
a > i , a.no_f {
	color: #DDD;
}

a.favorite > i {
	color : #F00;
}
select{
	width: 55px;
	height: 25px;
}
.select_div{
	
	margin: 10px;
}
</style>
<script type="text/javascript">
var ctxpath = '${pageContext.request.contextPath}';

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
 function loadSidoData ( sido ) {
	$.ajax({
    	url : ctxpath + '/api/region/rt/' + sido ,
    	method : 'GET',
    	success : function ( res ) {
    		console.log ( res );
    		console.log(res.data);
    		var loc = $('#location > tbody').empty(); /* $('')에   html소스부분 씀 */
    		var template = '<tr><td><a id="s{sido}" class="{f}" href="#"><i class="fas fa-star"></i></a> <a href="/airdata/rt/{seq}">{name}</a></td><td>{pm25}</td><td>{grade_pm25}</td><td>{pm100}</td><td>{grade_pm100}</td></tr>';
    		var tmp = res.data; // 39개 
    		var favors = res.favorites; // [ 360, 362]
    		for ( var i = 0 ; i < res.data.length ; i ++ ) {
    			var html = template.replace('{sido}', tmp[i].station)
    								.replace('{seq}', tmp[i].station)
    			                   .replace('{name}', tmp[i].stationName)
    			                   .replace('{pm25}', tmp[i].pm25)
    			                   .replace('{grade_pm25}', gradePm25(tmp[i].pm25).msg)
    			                   .replace('{pm100}', tmp[i].pm10 )
    			                   .replace('{grade_pm100}', gradePm10(tmp[i].pm10).msg );
    			// 배열안에 특정 값(원소)가 있는지 없는지? (배열favors를  돌면서 특정 값을 찾아낸다. 즉 , 배열 for문 돌기의 script식 )
    			var find =  favors.find(function( elem){  
    				return tmp[i].station == elem;
    			});
    			if( find ) {   /*  find가 true이면 favorite이라 넣어줌 (즉, 관측소중 관심으로 등록된건 class= "favorite" ) */
    				html = html.replace('{f}', 'favorite');
    			} else {
    				html = html.replace('{f}', 'no_f');   /* 관심등록 아직 안된건 class=" " 로 됨.  */
    				
    			}
    			
    			loc.append ( html );
    		}
    		  		
    		drawStation(res.data);
    		
    		$('#location a > i').on('click',function(e){ // ★★★★ 클릭되면 오는 것이 아니라, 페이지가 로딩 될 때 부터 와서 준비 함!! => 클릭이 되었을 때, 여기로 오는게 아닌, 바로 아래  var icon = $(e.target) 부터 실행 된다. !!!
    			e.preventDefault(); 					// a태그로 새로운 url로 페이징 로딩이 안되게 해줌. 
    			// console.log('별클릭');
    			var icon = $(e.target); 				//(지금)클릭 된 애가 누군지(이벤트를 발생시킨 녀석) 
    			var anchor = icon.parent(); 			// icon에 있는 부모태그를 찾아간다. => 여기선 a태그
    			if ( anchor.hasClass('favorite') ) {  	// a태그의 class명이 = "fovorite"( 관심등록 된 상태) 이면,  
    				
   					/*	$('#location a > i').on('click', function(e) { ... } 
   						★★★ (X) => 관심등록 된것 한번더 클릭하면 해제되는 함수로 이동하는게 아님!!!
   						위에서도 설명 했듯, 이 코드는 클릭하면 오는게 아니라 처음부터 페이지가 로딩 될때 부터 준비되고 , 클릭이 되면 이 부분 바로 밑부분으로 들어가 실행된다.!!!
					*/
				
   					var id = anchor.attr('id'); 
   					removeStation( id.substring(1), anchor ); // substring(1) => 문자열(id) 둘째자리부터 끝까지를 가져온다.(앞에  s가 있음으로) 
   	
    			
    			}else {
    				var id = anchor.attr('id');
    				addStation(id.substring(1), anchor);
    				
    			}
    			
    			
    			
    			
    			
    			/*
    			if( anchor.hasClass('favorite') ){
    				anchor.removeClass('favorite');
    			}else{
    				anchor.addClass('favorite');    				
    			}
    			*/
    			// anchor.toggleClass ( 'favorite');
    			
    		});
    		
    	}, 
    	error : function ( xhr, state, error ) {
			console.log ( xhr );        		
    	}
    	
    });
}
$(function() {
	$('#sido').val('${sidoName}');  /* ★★★★★  이것을 해야 페이지가 로드 될때 바로 select option에 
													기본 서울로 나옴. url에 /서울.. 이런식으로 주었음으로 !!! */

    // $("select[name=sido]").change(function() {
    $("#sido").change(function() {
        var sido_val = $(this).val();
        var url = ctxpath + '/region/rt/'+ sido_val; 
       
       // loadSidoData(sido_val);
        
        /*
    	 * 자바스크립트로  페이지 이동할때 사용하는 코드
    	 */ 
       location.href = url;  /* ★★★★★ 여기서 새로고침 해줌으로(페이지이동) url주소가 선택한 sido에 맞게 바뀐다. */
    }); // end region
    
   
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
		 <!-- <i class="fas fa-star"></i> -->
		<div class="select_div">
	 		<select name="sido" id="sido">
				<option value="">지역</option>
				<!-- <option value="서울">서울</option>      sido = 서울,경기,강원,... / region = (서울안)중구, 은평구...같은 관측소-->
				<c:forEach items="${sido}" var="region" >
				<option value="${region}">${region}</option>
				</c:forEach>
			</select>	
		</div>
		
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