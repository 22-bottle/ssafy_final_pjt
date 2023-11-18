<script setup>
import { ref, onMounted } from 'vue';
import { hotPlace, themePlace, kakaoToDto, createPlace, linkPlace } from '@/api/place';
import PlaceItem from './PlaceItem.vue';
import ThemeItem from '@/components/theme/ThemeItem.vue';
import KeywordItem from './KeywordItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';
const hotPlaces = ref([]);
const themePlaces = ref([]);

onMounted(() => {
  getHotPlace();
  getThemePlace();
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

const getThemePlace = () => {
  themePlace(
    1
    ,
    ({ data }) => {
      console.log(data);
      themePlaces.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
}

/* =============> */
const placeList = defineProps(['placeList']);
const emit = defineEmits(['keyword']);

const keywordPlaces = placeList.placeList;
const keyword = ref("");
const theme = ref(false);
const keywordPlace = ref(true);
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
const keywordSearch = () => {
  changeState();
}
const handleAdd = (place) => {
  console.log('Enter handleAdd method');
  // create place
  createPlace(
    kakaoToDto(place)
    ,
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
      themeId: "1",
      placeId: place.id,
      editorId: "1"
    }
    ,
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
          <template v-if="keywordPlace">
            <theme-item v-for="(place, index) in themePlaces" :key="index" :place="place"></theme-item>
            <button type="button" @click="keywordSearch" style="width: 100px; height: 35px;position: absolute; top: 100%;">장소등록</button>
          </template>
          <template v-else>
            <div>
              <input type="text" v-model="keyword" @keyup.enter="handleKeywordSearch" style="width: 250px; height: 30px;"/>
              <button type="button" @click="handleKeywordSearch" style="width: 50px; height: 35px;">검색</button>
            </div>
            <keyword-item v-for="(place, index) in keywordPlaces" :key="index" :place="place" @add="handleAdd"></keyword-item>
          </template>
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
