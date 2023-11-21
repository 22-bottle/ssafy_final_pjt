<script setup>
import { ref, onMounted } from 'vue';
import { isThere, isInTheme } from '@/api/place';
import { useRoute } from 'vue-router';

const props = defineProps({ place: Object });
const route = useRoute();

const place = ref({
  placeId: '',
  placeName: '',
  latitude: '',
  longitude: '',
  scoreSum: '',
  scoreCount: '',
  address: '',
  phone: '',
});

onMounted(() => {
  place.value = props.place;
  checkIsThere();
});

const moveToKakao = () => {
  window.open('https://place.map.kakao.com/' + place.value.placeId);
};
/* ========> */
const emit = defineEmits(['detail']);

const handlePlace = () => {
  emit('detail', place.value);
};
/* <======== */

const thereIs = ref(false); //place table에 있는지
const thereIn = ref(false); //place_in_theme table에 있는지
const checkIsThere = () => {
  isThere(
    props.place.placeId,
    ({ data }) => {
      thereIs.value = data;
      if (data) {
        checkInThere();
      }
    },
    (error) => {
      console.log(error);
    }
  );
};
const checkInThere = () => {
  isInTheme(
    route.params.themeId,
    props.place.placeId,
    ({ data }) => {
      thereIn.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div id="container">
    <div>
      {{ place.placeName }} | {{ place.scoreCount == 0 ? 0 : (place.scoreSum / place.scoreCount).toFixed(1) }} ({{
        place.scoreCount
      }})
    </div>
    <div>{{ place.address }}</div>
    <div>{{ place.phone }}</div>
    <a href="" @click="moveToKakao">카카오맵에서 보기</a>
    <template v-if="route.name == 'keyword'">
      <template v-if="!thereIs">
        <button @click="handlePlace">등록11</button>
      </template>
      <template v-else>
        <template v-if="!thereIn">
          <button @click="handlePlace">등록22</button>
        </template>
        <template v-else>
          <template v-if="route.name != 'detail'">
            <button>이미 등록됨</button>
          </template>
        </template>
      </template>
    </template>
    <template v-else>
      <button @click="handlePlace">상세보기</button>
    </template>
  </div>
  <br />
</template>

<style scoped>
* {
  background-color: white;
}
#container {
  width: 87%;
  padding: 10px;
  font-size: 25px;
}
#container div {
  color: black;
}
</style>
