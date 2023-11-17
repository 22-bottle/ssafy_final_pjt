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
  <div>{{ place.placeName }}</div>
</template>

<style scoped>
* {
  background-color: white;
}
</style>
