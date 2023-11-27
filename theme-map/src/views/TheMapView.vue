<script setup>
import { ref, onMounted, provide } from 'vue';
import { hotPlace, dtoToKakao, themePlace } from '@/api/place';
import { useRoute } from 'vue-router';
import PlaceList from '../components/map/PlaceList.vue';
import ThemePlaceList from '../components/theme/ThemePlaceList.vue';
import router from '../router';

var map;
var selectedMarker = ref(null);
var hoveredPlace = ref('');
var selectedPlace = ref('');
const temp = ref([]);
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

  if (route.name === 'place') {
    getHotPlace();
  } else {
    getThemePlace();
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
    obj.title = t.place_name;
    obj.placeId = t.category_group_code;
    obj.score = t.distance;

    positions.value.push(obj);
  });

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position, index) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      score: position.distance,
    });

    // 커스텀 오버레이 내용
    var overlayContent = `<div class="custom-overlay"
        style=
        "
        color: black;
        background-color: #FFFFFF;
        padding: 3px;
        border: 1px solid #016ef5;
        border-radius: 5px
        ";
  >${position.title} <span style="color: black">(<span style="color: red">★</span>${position.score})</span></div>`;

    // 커스텀 오버레이 생성
    var customOverlay = new kakao.maps.CustomOverlay({
      content: overlayContent,
      position: position.latlng,
      xAnchor: 0.5,
      yAnchor: 3.0,
      /* customize */
    });

    // 마커에 mouseover 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseover', function () {
      if (!selectedMarker.value || selectedMarker.value !== marker) {
        hoveredPlace.value = position.placeId;
        customOverlay.setMap(map);
      }
    });

    // 마커에 mouseout 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseout', function () {
      if (!selectedMarker.value || selectedMarker.value !== marker) {
        hoveredPlace.value = '';
        customOverlay.setMap(null);
      }
    });

    // 마커에 click 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function () {
      if (!selectedMarker.value || selectedMarker.value !== marker) {
        selectedPlace.value = position.placeId;
      }

      // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
      selectedMarker.value = marker;
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
    placeList.value = data;
    loadMarkers(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    window.alert('검색 결과가 존재하지 않습니다.');
  } else if (status === kakao.maps.services.Status.ERROR) {
    window.alert('검색 결과 중 오류가 발생했습니다.');
  }
}

/* <============= */
const hotPlaces = ref([]);

const getHotPlace = () => {
  hotPlace(
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        hotPlaces.value.push(dtoToKakao(data[i]));
      }
      temp.value = hotPlaces.value;
      loadMarkers();
    },
    (error) => {
      console.log(error);
    }
  );
};

const themePlaces = ref([]);
const getThemePlace = () => {
  themePlace(
    route.params.themeId,
    ({ data }) => {
      if (data.length == 0) {
        router.replace({ name: 'keyword', params: { themeId: route.params.themeId } });
      }
      for (let i = 0; i < data.length; i++) {
        themePlaces.value.push(dtoToKakao(data[i]));
      }
      temp.value = themePlaces.value;
      loadMarkers();
    },
    (error) => {
      console.log(error);
    }
  );
};

const clickMap = () => {
  clicked.value = true;
};

const clickPlace = (param) => {
  // 이동할 위도 경도 위치를 생성합니다
  var moveLatLon = new kakao.maps.LatLng(param.latitude, param.longitude);

  // 지도 중심을 이동 시킵니다
  map.setCenter(moveLatLon);
  map.setLevel(3);

  clicked.value = false;
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
        @clickPlace="clickPlace"
        :selectedPlace="selectedPlace"
        :hoveredPlace="hoveredPlace"
      ></place-list>
    </template>
    <template v-else>
      <theme-place-list @clickPlace="clickPlace"></theme-place-list>
    </template>
    <!-- <============= -->
  </div>
</template>

<style scoped></style>
