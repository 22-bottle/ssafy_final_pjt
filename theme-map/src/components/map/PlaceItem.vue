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
});

onMounted(() => {
  place.value = props.place;
});
</script>

<template>
  <div id="container">
    <div>{{ place.placeName }}</div>
    <div>{{ place.scoreSum / place.scoreCount }}</div>
  </div>
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
</style>
