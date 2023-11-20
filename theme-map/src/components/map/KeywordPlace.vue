<script setup>
import { ref, onMounted } from 'vue';
import KeywordList from '@/components/map/KeywordList.vue';
import KeywordItem from '@/components/map/KeywordItem.vue';

var map;
const key = import.meta.env.VITE_KAKAO_MAP_KEY;

const placeList = ref([]);

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
};

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
    console.log(">result",data);
    placeList.value.push(data);
    console.log(">local", placeList.value);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    window.alert('검색 결과가 존재하지 않습니다.');
  } else if (status === kakao.maps.services.Status.ERROR) {
    window.alert('검색 결과 중 오류가 발생했습니다.');
  }
}

</script>

<template>
    <div>
        <!-- 카카오 맵 -->
        <div id="map" class="map" style="width: 100%; height: 100vh"></div>
        <keyword-list @keyword="searchKeyWord" @clickPlace="clickPlace"></keyword-list>
        <keyword-item :place="placeList"></keyword-item>
        <!-- <template v-for="place in placeList" :key="place.placeId">
            <div>
                오잉
            </div>
        </template> -->
    </div>
</template>

<style scoped>

</style>