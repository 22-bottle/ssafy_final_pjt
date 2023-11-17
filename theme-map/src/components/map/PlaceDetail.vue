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
</script>

<template>
  <div id="detail">
    <div id="placeInfo">
      <div>{{ place.placeName }}</div>
    </div>
  </div>
</template>

<style scoped>
#detail {
  position: fixed;
  top: 20%;
  left: 22%;
  width: 21%;
  height: 73%;
  background-color: #f5fffa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 9;
  border-radius: 5px;
}
#placeInfo {
  position: relative;
  width: 90%;
  height: 94%;
  background-color: #016ef5;
  border-radius: 5px;
}
#placeInfo div {
  color: white;
}
</style>
