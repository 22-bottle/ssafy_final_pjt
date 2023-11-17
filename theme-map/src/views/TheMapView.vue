<<<<<<< HEAD
<script setup>
import { ref, onMounted } from 'vue';

const mapContainer = ref(null);

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

let map = ref(null);
const text = ref("내 주변 인기장소");

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

        <!-- 리스트 -->
        <div class="list">
          <div class="name">{{ text }}</div>
          <div class="items">
          </div>
        </div>
    </div>
</template>

<style scoped>
.list {
    position: absolute;
    top: 20%;
    left: 5%;
    z-index: 10;
    width: 23%; height: 73%;
    background-color: #F5FFFA;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 15px;
    box-shadow:0 10px 20px rgba(0,0,0,0.25);
}

.name {
  position: relative;
  width: 90%; height: 10%;
  background-color: #016EF5;
  color: #FFFFFF;
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}

.items {
  position: relative;
  width: 90%; height: 84%;
  background-color: #016EF5;
  color: #FFFFFF;
}

=======
<script setup>
import { ref, onMounted } from 'vue';

const mapContainer = ref(null);

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

let map = ref(null);
const text = ref("내 주변 인기장소");

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

        <!-- 리스트 -->
        <div class="list">
          <div class="name">{{ text }}</div>
          <div class="items">
          </div>
        </div>
    </div>
</template>

<style scoped>
.list {
    position: absolute;
    top: 20%;
    left: 5%;
    z-index: 10;
    width: 23%; height: 73%;
    background-color: #F5FFFA;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 15px;
    box-shadow:0 10px 20px rgba(0,0,0,0.25);
}

.name {
  position: relative;
  width: 90%; height: 10%;
  background-color: #016EF5;
  color: #FFFFFF;
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}

.items {
  position: relative;
  width: 90%; height: 84%;
  background-color: #016EF5;
  color: #FFFFFF;
}

>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
</style>