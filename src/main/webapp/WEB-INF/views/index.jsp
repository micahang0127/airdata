<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common-head.jsp"></jsp:include>
<script type="text/javascript">
	var ctxpath = '${pageContext.request.contextPath}';

	var main_data = '${datajson}'; // return index.jsp를 하는 Controller부분에서 model에 addAttribute 한 데이터 (스크립트로)가져오기

	var srcData = JSON.parse(main_data);
	// controller 에서 json 값을 가져올때  여기 script 에서도 가져와서 따로 json.parse 를 해주어야 한다.!!
	var sido = [ "서울", "경기", "강원", "대전", "광주", "부산", "충북", "충남", "전북", "전남",
			"경북", "경남" ];




	/* 	
	 * 
	 // => rt-by-station.jsp 에서 사용하던  실시간(24시간) 데이터쿼리문으로만 사용하여 스크립트에서 데이터들을 받아 평균을 내려고 처음에 생각하였으나,
	 하다보니 코드가 길어지고 복잡해지는 현상이 생김. 
	 특히, sido별로 실시간(24시간) 데이터를 가져오는 것에서는 where절로 각 sido별로 가져오기는 하나, select문에는 sido명을 뽑아 내지 않고, 
	 이를 controller에서 결국 ArrayList로 합쳐져  결국 통합데이터가 되어버리기 때문에 script에서 다시 데이터를 맞추며 나누어 줘야 함. 2.각각의 관측소의 pm10, pm25데이터들을 for문으로 다시 뽑아
	 평균까지 내어줘야 한다. 이로써  중복일과, 코드 복잡 현상이 
	 생긴다고 판단. => pm10, pm25 데이터 평균을 내는 index페이지에 맞는 쿼리문을 새로 생성하였다.
	
	
	 for(i=0; i < sido.length; i++){
	
	 if(sidoOne == sido[i]){
	 for(j=0; j < srcData[i].length; j++){
		var pm10 = 0;
	 	pm10 = srcData[i][j].pm10 + pm10
	 }	 }	 }	 

	 */


	$(function() {

		
		$.ajax({

			url : ctxpath + '/index',
			method : 'GET',
			success : function(aa) { // aa(맘대로 지정)에는 해당 controller에서 return값이 들어있다.
				var loc = $('.all').empty();
				var templet = '<div class="thumbnail" style="width:150px;height:150px; float:left; margin:20px; text-align: center; "><h4><a href="/airdata/region/rt/[sido]" id="sido">{sido}</a></h4><p id="grade" style="color:{color}">{grade}</p><p id="pm10"><b>{pm10}</b>[10]</p><p id="pm25"><b>{pm25}</b>[2.5]</p></div>';
				var tem = aa;
				for (i = 0; i < sido.length; i++) {

					for (j = 0; j < srcData.length; j++) {

						if (sido[i] == srcData[j].region) {

							var html = templet.replace('[sido]',sido[i])
												.replace('{sido}',sido[i])
												.replace('{color}', gradePm10(srcData[j].avgPm10).color)
												.replace('{grade}',gradePm10(srcData[j].avgPm10).msg)
												.replace('{pm10}',srcData[j].avgPm10)
												.replace('{pm25}',srcData[j].avgPm25);

							loc.append(html);

						}
					}

				}
			}
		});

	});
</script>
<title>미세먼지 홈페이지 입니다.</title>
</head>
<body>
	<form action="/index" method="GET">
		<!-- 곹통 네비게이션 -->
		<jsp:include page="/WEB-INF/views/common/nav-header.jsp"></jsp:include>
		<table style="margin: 20px; ">
			<tr>
				<td style="width:60%; text-align: center; "><h2><span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>&nbsp;실시간 현황</h2></td>
				<td>
					<ul style="margin-top:50px;">
					<li><h5>PM10(미세먼지)</h5>
					<div class="progress" style="width: 400px; height: 5px; margin-bottom: 0px;">
						<div class="progress-bar progress-bar-success" style="width: 25%; background-color: blue;">
							<span class="sr-only">0-30(좋음)</span>
						</div>
						<div class="progress-bar progress-bar-warning progress-bar-striped"
							style="width: 25%; background-color: green;">
							<span class="sr-only">31-80(보통)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width:25%; background-color: yellow;">
							<span class="sr-only">81-150(나쁨)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width:25%; background-color: #FF0000;">
							<span class="sr-only">151-(매우나쁨)</span>
						</div>
					</div>
					<table style="width:400px; margin-bottom: 20px;"><tr><td style="width:25%; color:blue;">0(좋음)</td><td style="width:25%; color:green;">31(보통)</td><td style="width:25%; color:#C9AE00;">81(나쁨)</td><td style="width:25%; color:#FF0000;">151~(매우나쁨)</td></tr></table>
					</li>
					<li><h5>PM2.5(초미세먼지)</h5>
					<div class="progress" style="width: 400px; height: 5px; margin-bottom: 0px;">
						<div class="progress-bar progress-bar-success" style="width: 25%; background-color: blue;">
							<span class="sr-only">0-15(좋음)</span>
						</div>
						<div class="progress-bar progress-bar-warning progress-bar-striped"
							style="width: 25%; background-color: green;">
							<span class="sr-only">16-35(보통)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width:25%; background-color: yellow;">
							<span class="sr-only">36-75(나쁨)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width:25%; background-color: #FF0000;">
							<span class="sr-only">76-(매우나쁨)</span>
						</div>
					</div>
					<table style="width:400px;"><tr><td style="width:25%; color:blue;">0</td><td style="width:25%; color:green;">16</td><td style="width:25%; color:#C9AE00;">36</td><td style="width:25%; color:#FF0000;">76~</td></tr></table>
					</li>
					</ul>
				</td>
			</tr>
		</table>
		<p style="margin-left: 50px; margin-bottom: 0px;">※각 지역의 평균값으로 표시됩니다.(등급 pm10기준)</p>
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
		</div>
	<!-- 	<p style="width:100%; margin:20px;">
			<h4>미세먼지 여기보세요!</h4><br/>
			오늘날의 생활 속에서 <b>미세먼지</b>는 이제 결코, 무시할 수 없는 존재가 되었습니다. <br />
				황사가 불어오는 봄철, 북서풍의 겨울철, 그리고 여름까지도 여러 요인으로 인한 극심한 미세먼지에 우리들의 삶에 큰 영향을
				미치고 있습니다.<br /> <b>그렇기에, 저는 이 문제에 관심을 기울여 미세먼지를 나타내는 홈페이지를 만들어
					보고자 했습니다. </b><br />
		</p>
 -->

	</form>
</body>
</html>