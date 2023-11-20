<script setup>
import { ref, onMounted, inject, watch } from 'vue';
import { hotPlace, kakaoToDto, dtoToKakao, createPlace, linkPlace } from '@/api/place';
import PlaceItem from './PlaceItem.vue';
import KeywordItem from './KeywordItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';
const hotPlaces = ref([]);
const markers = ref([]);

onMounted(() => {
  getHotPlace();
});

const getHotPlace = () => {
  hotPlace(
    ({ data }) => {
      console.log(data[0]);
      hotPlaces.value = data;
      for (let i = 0; i < hotPlaces.value.length; i++) {
        markers.value.push(dtoToKakao(data[i]));
      }
      markerUpdate(markers);
    },
    (error) => {
      console.log(error);
    }
  );
};

/* =============> */
const props = defineProps({ placeList: Array });
const emit = defineEmits(['keyword', 'makeInfos', 'clickPlace']);

const keywordPlaces = props.placeList;
const keyword = ref('');
const keywordPlace = ref(true);
const visibility = ref(false);
const placeToView = ref(null);

const handleKeywordSearch = async () => {
  console.log('Enter handleKeywordSearch method');
  emit('keyword', keyword.value);
};

const handleDetail = (place) => {
  console.log('Enter handleDetail method');
  if (placeToView.value === place) {
    visibility.value = !visibility.value;
  } else {
    visibility.value = true;
    placeToView.value = place;
  }
  emit('clickPlace', false);
};
const keywordSearch = () => {
  changeState();
};
const handleAdd = (place) => {
  console.log('Enter handleAdd method');
  // create place
  createPlace(
    kakaoToDto(place),
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  // link place
  linkPlace(
    {
      themeId: '1',
      placeId: place.id,
      editorId: '1',
    },
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  changeState();
};

function changeState() {
  keywordPlace.value = !keywordPlace.value;
}

const markerUpdate = () => {
  console.log('Enter markerUpdate method');
  emit('updateMarkers', markers.value);
};

const clicked = inject('clicked');

/* <============= */
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">내 주변 인기 장소</div>
      <div class="items">
        <!-- <place-item v-for="(place, index) in hotPlaces" :key="place.placeId" :place="place"></place-item> -->
        <!-- =============> -->
        <div class="items scrollbar">
          <place-item
            v-for="(place, index) in hotPlaces"
            :key="place.placeId"
            :place="place"
            @detail="handleDetail"
          ></place-item>
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
</style>
