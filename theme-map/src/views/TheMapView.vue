<script setup>
import { ref, onMounted } from 'vue';

const mapContainer = ref(null);

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

let map = ref(null);

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
      center: new window.kakao.maps.LatLng(37.5665, 126.978), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };
    // 지도를 생성합니다.
    map.value = new window.kakao.maps.Map(mapContainer.value, options);
  });
}
</script>

<template>
  <div>
    <!-- 카카오 맵 -->
    <div class="map" ref="mapContainer" style="width: 100%; height: 100vh"></div>
    <router-view></router-view>
  </div>
</template>

<style scoped></style>
