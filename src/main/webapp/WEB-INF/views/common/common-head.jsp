<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link href="${pageContext.request.contextPath }/resources/css/fontawesome-all.css" rel="stylesheet">
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script>

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


</script>