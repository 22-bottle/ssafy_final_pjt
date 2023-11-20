<script setup>
import { ref, onMounted, watch, inject } from 'vue';
import { hotPlace } from '@/api/place';
import PlaceItem from './PlaceItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';

const props = defineProps({ selectedPlace: String , hoveredPlace: String});
const selected = ref('');
const hovered = ref('');

onMounted(() => {
  getHotPlace();
});

watch(
  () => props.selectedPlace,
  () => {
    selected.value = props.selectedPlace;
  },
  { deep: true }
);

watch(
  () => props.hoveredPlace,
  () => {
    hovered.value = props.hoveredPlace;
  },
  { deep: true }
);

const hotPlaces = ref([]);
const getHotPlace = () => {
  hotPlace(
    ({ data }) => {
      hotPlaces.value = data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};

/* =============> */
// const props = defineProps({ placeList: Array });
const emit = defineEmits(['clickPlace']);
const clicked = inject('clicked');

// const hotPlaces = props.placeList;
const visibility = ref(false);
const placeToView = ref(null);

const handleDetail = (place) => {
  console.log('Enter handleDetail method');
  if (placeToView.value === place) {
    visibility.value = !visibility.value;
  } else {
    visibility.value = true;
    placeToView.value = place;
  }
  emit('clickPlace', place);
};
/* <============= */
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">내 주변 인기 장소</div>
      <div class="items">
        <!-- =============> -->
        <div class="items scrollbar">
          <template v-for="(place, index) in hotPlaces" :key="place.placeId">
            <!-- selected -->
            <template v-if="hovered == place.placeId">
              <div style="background-color: yellow;">
                <place-item :place="place" @detail="handleDetail"></place-item>
              </div>
            </template>
            <template v-else>
              <place-item :place="place" @detail="handleDetail"></place-item>
            </template>
          </template>
        </div>
        <template v-if="visibility && !clicked">
          <place-detail :place="placeToView"></place-detail>
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
.scrollbar::-webkit-scrollbar {
  width: 10px; /* width of the entire scrollbar */
}

.scrollbar::-webkit-scrollbar-track {
  background: #f1f1f1; /* color of the tracking area */
}

.scrollbar::-webkit-scrollbar-thumb {
  background: #888; /* color of the scroll thumb */
}

.scrollbar::-webkit-scrollbar-thumb:hover {
  background: #555; /* color of the scroll thumb on hover */
}
</style>
