<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="Client.GUI.RestaurantSearch"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>키워드로 장소검색하기</title>
</head>
<body>
	<div id="map" style="width: 900px; height: 650px;"></div>
	
	<label>주소 입력: </label><input id="address" type="text" style="width: 250px">
	<button onclick="locate()">검색</button>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=50575735961367195259016e181d959a&libraries=services"></script>
	<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new daum.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 2 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();

// 주소 목록 객체
var listData=[];

function locate(){
listData.push(document.getElementById('address').value);

listData.forEach(function(addr, index) {
    geocoder.addressSearch(addr, function(result, status) {
        if (status === daum.maps.services.Status.OK) {
            var coords = new daum.maps.LatLng(result[0].y, result[0].x);

            var marker = new daum.maps.Marker({
                map: map,
                position: coords
            });
            var infowindow = new daum.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">' + listData[index] + '</div>',
                disableAutoPan: true
            });
            infowindow.open(map, marker);
            
            //지도 중심 이동
            map.setCenter(coords);
        }
    });
});
}
</script>
</body>
</html>