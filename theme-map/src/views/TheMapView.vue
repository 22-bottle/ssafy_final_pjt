<script setup>
import { ref, onMounted, provide, toRaw } from 'vue';
import { useRoute } from 'vue-router';
import PlaceList from '../components/map/PlaceList.vue';
import ThemePlaceList from '../components/theme/ThemePlaceList.vue';

var map;
const temp = ref([{x: 126.570667, y: 33.450701}]);
const positions = ref([]);
const markers = ref([]);
const clicked = ref(false);

provide('clicked', clicked);

const route = useRoute();

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script');
    script.src = key;
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
    obj.latlng = new kakao.maps.LatLng(t.y, t.x);
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
  markers.value.push(marker);

  return marker;
}

// 마커를 삭제하는 함수입니다.
function removeMarker() {
  for (var i = 0; i < markers.value.length; i++) {
    markers[i].setMap(null);
  }
  markers.value = [];
}
/* <============= */
const updateMarkers = (places) => {
  console.log('Enter updateMarkers method:', places);
  temp.value = places;
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
    <div id="map" class="map" @mousedown="clickMap" style="width: 100%; height: 100vh"></div>
    <!-- <router-view></router-view> -->
    <!-- =============> -->
    <template v-if="route.name === 'place'">
      <place-list
        @keyword="searchKeyWord"
        @click="clickPlace"
        @updateMarkers="updateMarkers"
        :placeList="placeList"
        :map-loaded="mapLoaded"
      ></place-list>
    </template>
    <template v-else>
      <theme-place-list></theme-place-list>
    </template>
    <!-- <============= -->
  </div>
</template>

<style scoped></style>
