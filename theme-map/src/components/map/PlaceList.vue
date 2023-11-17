<script setup>
import { ref, onMounted } from 'vue';
import { hotPlace } from '@/api/place';
import PlaceItem from './PlaceItem.vue';

const hotPlaces = ref([]);

onMounted(() => {
  getHotPlace();
});

const getHotPlace = () => {
  hotPlace(
    ({ data }) => {
      console.log(data[0]);
      hotPlaces.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">내 주변 인기장소</div>
      <div class="items">
        <place-item v-for="(place, index) in hotPlaces" :key="place.placeId" :place="place"></place-item>
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
  width: 23%;
  height: 73%;
  background-color: #f5fffa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}

.name {
  position: relative;
  width: 90%;
  height: 10%;
  background-color: #016ef5;
  color: #ffffff;
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}

.items {
  position: relative;
  width: 90%;
  height: 84%;
  background-color: #016ef5;
  color: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
