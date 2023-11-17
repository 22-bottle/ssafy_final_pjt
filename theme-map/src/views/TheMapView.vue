<script setup>
import { ref, onMounted } from 'vue';

const mapContainer = ref(null);

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

var map = ref(null);

onMounted(() => {
  setMap();
});

function setMap() {
  // 카카오 맵 스크립트를 동적으로 로드합니다.
  const script = document.createElement('script');
  script.onload = () => initializeMap(); // 스크립트 로드가 완료되면 지도를 초기화합니다.
  script.src = key;
  document.head.appendChild(script);
}

function initializeMap() {
  // 카카오 맵 API가 로드된 후 호출될 함수입니다.
  window.kakao.maps.load(() => {
    const options = {
      center: new window.kakao.maps.LatLng(36.35483, 127.2978), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };
    // 지도를 생성합니다.
    map.value = new window.kakao.maps.Map(mapContainer.value, options);
  });
}

/* =============> */
var markers = [];

const placeList = ref([]);

const searchKeyWord = (keyword) => {
<<<<<<< HEAD
  console.log("Enter searchKeyWord method:",keyword);
  searchPlaces(keyword);
}

function searchPlaces(keyword) {
  var ps = new window.kakao.maps.services.Places(); 

  if (!keyword.replace(/^\s+|\s+$/g, '')) {
      window.alert('키워드를 입력해주세요!');
      return false;
  }

  ps.keywordSearch(keyword, placesSearchCB); 
}

function placesSearchCB(data, status) {
    if (status === kakao.maps.services.Status.OK) {
      console.log(data);
      displayPlaces(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      window.alert('검색 결과가 존재하지 않습니다.');
    } else if (status === kakao.maps.services.Status.ERROR) {
      window.alert('검색 결과 중 오류가 발생했습니다.');
    }
}

function displayPlaces(places) {
  var bounds = new window.kakao.maps.LatLngBounds(); 

  removeMarker();

  for ( var i=0; i<places.length; i++ ) {
      var placePosition = new window.kakao.maps.LatLng(places[i].y, places[i].x);
      var marker = addMarker(placePosition, i);
      
      setListItem(i, places[i]);
      bounds.extend(placePosition);
=======
  console.log('Enter searchKeyWord method:', keyword);
  searchPlaces(keyword);
};

function searchPlaces(keyword) {
  var ps = new window.kakao.maps.services.Places();

  if (!keyword.replace(/^\s+|\s+$/g, '')) {
    window.alert('키워드를 입력해주세요!');
    return false;
  }

  ps.keywordSearch(keyword, placesSearchCB);
}

function placesSearchCB(data, status) {
  if (status === kakao.maps.services.Status.OK) {
    console.log(data);
    displayPlaces(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    window.alert('검색 결과가 존재하지 않습니다.');
  } else if (status === kakao.maps.services.Status.ERROR) {
    window.alert('검색 결과 중 오류가 발생했습니다.');
  }
}

function displayPlaces(places) {
  var bounds = new window.kakao.maps.LatLngBounds();

  removeMarker();

  for (var i = 0; i < places.length; i++) {
    var placePosition = new window.kakao.maps.LatLng(places[i].y, places[i].x);
    var marker = addMarker(placePosition, i);

    setListItem(i, places[i]);
    bounds.extend(placePosition);
>>>>>>> 16a7c8b5ca91089bd7c8c344f54cc2146c35dd3b
  }

  map.value.setBounds(bounds);
}

function setListItem(index, places) {
  placeList.value.push({
    placeName: places.place_name,
    address: places.address_name,
<<<<<<< HEAD
    phone: places.phone
=======
    phone: places.phone,
>>>>>>> 16a7c8b5ca91089bd7c8c344f54cc2146c35dd3b
  });
}

function addMarker(position, idx) {
  var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
<<<<<<< HEAD
      imageSize = new window.kakao.maps.Size(36, 37),
      imgOptions =  {
          spriteSize : new window.kakao.maps.Size(36, 691),
          spriteOrigin : new window.kakao.maps.Point(0, (idx*46)+10),
          offset: new window.kakao.maps.Point(13, 37)
      },
      markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
          marker = new window.kakao.maps.Marker({
          position: position,
          image: markerImage 
      });
=======
    imageSize = new window.kakao.maps.Size(36, 37),
    imgOptions = {
      spriteSize: new window.kakao.maps.Size(36, 691),
      spriteOrigin: new window.kakao.maps.Point(0, idx * 46 + 10),
      offset: new window.kakao.maps.Point(13, 37),
    },
    markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
    marker = new window.kakao.maps.Marker({
      position: position,
      image: markerImage,
    });
>>>>>>> 16a7c8b5ca91089bd7c8c344f54cc2146c35dd3b

  marker.setMap(map.value);
  markers.push(marker);

  return marker;
}

// 마커를 삭제하는 함수입니다.
function removeMarker() {
<<<<<<< HEAD
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    markers = [];
=======
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
>>>>>>> 16a7c8b5ca91089bd7c8c344f54cc2146c35dd3b
}
/* <============= */
</script>

<template>
  <div>
    <!-- 카카오 맵 -->
    <div class="map" ref="mapContainer" style="width: 100%; height: 100vh"></div>
    <!-- <router-view></router-view> -->
    <!-- =============> -->
<<<<<<< HEAD
    <router-view @keyword="searchKeyWord" :placeList="placeList"></router-view>
=======
    <router-view @keyword="searchKeyWord" :place="placeList"></router-view>
>>>>>>> 16a7c8b5ca91089bd7c8c344f54cc2146c35dd3b
    <!-- <============= -->
  </div>
</template>

<style scoped></style>
