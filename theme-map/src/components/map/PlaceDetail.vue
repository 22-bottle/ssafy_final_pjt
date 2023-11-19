<script setup>
import { ref, onMounted, computed } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from '@/stores/editor';

const editorStore = useEditorStore();

const props = defineProps({ place: Object });
const { isLogin } = storeToRefs(editorStore);

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

const starRating = computed(() => {
  const rating = props.place.scoreCount === 0 ? 0 : (props.place.scoreSum / props.place.scoreCount).toFixed(1);
  const validRating = !isNaN(parseFloat(rating)) && isFinite(rating) ? parseFloat(rating) : 0;

  const fullStars = Math.max(0, Math.min(5, Math.floor(validRating)));
  const halfStar = validRating % 1 >= 0.5 ? 1 : 0;
  const emptyStars = 5 - fullStars - halfStar;
  return { validRating, fullStars, halfStar, emptyStars };
});
</script>

<template>
  <div id="detail">
    <div id="placeInfo">
      <div>{{ place.placeName }}</div>
      <div>{{ place.address }}</div>
      <div style="color: aliceblue;">{{ starRating.validRating }} 점</div>
      <div class="star-rating">
        <span v-for="n in Math.max(0, starRating.fullStars)" :key="n" class="star full">&#9733;</span>
        <span v-if="starRating.halfStar" class="star empty">&#9734;</span>
        <span v-for="n in starRating.emptyStars" :key="n" class="star empty">&#9734;</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.star-rating {
  width: 100px; height: 100px;
}
.star {
  color: red;
}
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
