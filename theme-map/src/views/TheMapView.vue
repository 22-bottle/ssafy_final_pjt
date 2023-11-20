<script setup>
import { ref, onMounted, provide, toRaw } from 'vue';

const mapContainer = ref(null);
var map;
const temp = ref([{ lat: 37.499590490909185, lng: 127.0263723554437 }]);
const positions = ref([]);
const markers = ref([]);
const mapLoaded = ref(false);
const clicked = ref(false);
provide('mapLoaded', mapLoaded);
provide('clicked', clicked);

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=c1c453654e5d10e503f508c9e7179df7&autoload=false&libraries=services`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  temp.value.forEach((t) => {
    let obj = {};
    obj.latlng = new kakao.maps.LatLng(t.lat, t.lng);
    obj.title = 'aasdfasfsf';

    positions.value.push(obj);
  });

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

function setMap() {
  // 카카오 맵 스크립트를 동적으로 로드합니다.
  var script = document.createElement('script');
  script.onload = () => initializeMap(); // 스크립트 로드가 완료되면 지도를 초기화합니다.
  script.src = key;
  document.head.appendChild(script);

  // 마커가 표시될 위치입니다
  var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

  // 마커를 생성합니다
  var marker = new kakao.maps.Marker({
    position: markerPosition,
  });

  // 마커가 지도 위에 표시되도록 설정합니다
  marker.setMap(map);
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

const placeList = ref([]);

const searchKeyWord = (keyword) => {
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
  console.log('>>M', places);
  var bounds = new window.kakao.maps.LatLngBounds();
  console.log('여기있어요!!!!!');
  removeMarker();

  for (var i = 0; i < places.length; i++) {
    console.log(places[i]);
    var placePosition = new window.kakao.maps.LatLng(places[i].y, places[i].x);
    var marker = addMarker(placePosition, i);

    setListItem(i, places[i]);
    bounds.extend(placePosition);
  }

  map.value.setBounds(bounds);
}

function setListItem(index, places) {
  placeList.value.push(places);
}

function addMarker(position, idx) {
  var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
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

  marker.setMap(map.value);
  markers.push(marker);

  return marker;
}

// 마커를 삭제하는 함수입니다.
function removeMarker() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}
/* <============= */
const updateMarkers = (places) => {
  console.log('Enter updateMarkers method:', places);
  displayPlaces(places);
};

const clickMap = () => {
  console.log('Enter clickMap method:');
  clicked.value = true;
  console.log(clicked.value);
};

const clickPlace = () => {
  console.log('Enter clickPlace method:');
  clicked.value = false;
  console.log(clicked.value);
};
</script>

<template>
  <div>
    <!-- 카카오 맵 -->
    <div id="map" class="map" ref="mapContainer" @mousedown="clickMap" style="width: 100%; height: 100vh"></div>
    <!-- <router-view></router-view> -->
    <!-- =============> -->
    <router-view
      @keyword="searchKeyWord"
      @click="clickPlace"
      @updateMarkers="updateMarkers"
      :placeList="placeList"
      :map-loaded="mapLoaded"
    ></router-view>
    <!-- <============= -->
  </div>
</template>

<style scoped></style>
