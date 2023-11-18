<script setup>
import { ref, onMounted } from 'vue';

const props = defineProps({ place: Object });

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
});

const moveToKakao = () => {
  window.open('https://place.map.kakao.com/' + place.value.placeId);
};
/* ========> */
const emit = defineEmits(['detail']);

const handlePlace = (

) => {
  console.log('Enter handlePlace method');
  console.log(place);
  emit('detail', place.value);
};

/* <======== */
</script>

<template>
  <div id="container" @click="handlePlace">
    <div>{{ place.placeName }} | {{ place.scoreCount == 0 ? 0 : (place.scoreSum / place.scoreCount).toFixed(1) }} ({{ place.scoreCount }})</div>
    <div>{{ place.address }}</div>
    <div>{{ place.phone }}</div>
    <a href="" @click="moveToKakao">카카오맵에서 보기</a>
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
