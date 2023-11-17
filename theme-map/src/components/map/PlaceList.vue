<script setup>
import { ref, onMounted } from 'vue';
import { hotPlace } from '@/api/place';
import PlaceItem from './PlaceItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';
import ThemeItem from './ThemeItem.vue';
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
/* =============> */
const placeList = defineProps(['placeList']);
const emit = defineEmits(['keyword']);

const themePlaces = placeList.placeList;
const keyword = ref("");
const theme = ref(true);
const visibility = ref(false);
const placeToView = ref(null);

const handleKeywordSearch = async () => {
  console.log('Enter handleKeywordSearch method');
  emit('keyword', keyword.value);
};
const handleDetail = (place) => {
  console.log('Enter handleDetail method');
  visibility.value = !visibility.value;
  placeToView.value = place;
};
/* <============= */
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">내 주변 인기장소</div>
      <div class="items">
        <!-- <place-item v-for="(place, index) in hotPlaces" :key="place.placeId" :place="place"></place-item> -->
        <!-- =============> -->
        <template v-if="theme">
          <div>
            <input type="text" v-model="keyword" @keyup.enter="handleKeywordSearch" style="width: 250px; height: 30px;"/>
            <button type="button" @click="handleKeywordSearch" style="width: 50px; height: 35px;">검색</button>
          </div>
          <theme-item v-for="(place, index) in themePlaces" :key="index" :place="place"></theme-item>
        </template>
        <template v-else>
          <div class="items scrollbar">
            <place-item
              v-for="(place, index) in hotPlaces"
              :key="place.placeId"
              :place="place"
              @detail="handleDetail"
            ></place-item>
          </div>
          <template v-if="visibility">
            <place-detail :place="placeToView"></place-detail>
          </template>
        </template>
        <!-- <============= -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.list {
  position: absolute;
  top: 20%;
  left: 2%;
  z-index: 10;
  width: 21%;
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
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}
.scrollbar {
  overflow: auto;
}
</style>
