<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { createPlace, linkPlace } from '@/api/place';
import { useEditorStore } from '@/stores/editor';
import PlaceItem from './PlaceItem.vue';
import router from '../../router';

const editorStore = useEditorStore();

const props = defineProps({ selectedPlace: String, hoveredPlace: String, placeList: Array });
const { cEditorDto } = editorStore;

const route = useRoute();
const keywordPlaces = ref([]);
const selected = ref('');
const hovered = ref('');
const keyword = ref('');
const editorId = ref('');

onMounted(() => {
  // getHotPlace();
  editorId.value = cEditorDto.value.editorId;
  keywordPlaces.value = props.placeList;
});

watch(
  () => props.hoveredPlace,
  () => {
    hovered.value = props.hoveredPlace;
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

watch(
  () => props.placeList,
  () => {
    keywordPlaces.value = [];
    keywordPlaces.value = { ...props.placeList };
  },
  { deep: true }
);

/* =============> */
const emit = defineEmits(['detail']);

const handleKeywordSearch = async () => {
  emit('keyword', keyword.value);
};
const handleAdd = (place, id) => {
  if (id == '1') {
    // 장소를 생성
    createPlace(
      place,
      () => {
        linkPlace(
          {
            themeId: route.params.themeId,
            placeId: place.placeId,
            editorId: editorId.value,
          },
          () => {
            router.push({ name: 'detail', params: { themeId: route.params.themeId } });
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    // 테마와 장소를 연결
    linkPlace(
      {
        themeId: route.params.themeId,
        placeId: place.placeId,
        editorId: editorId.value,
      },
      () => {
        router.push({ name: 'detail', params: { themeId: route.params.themeId } });
      },
      (error) => {
        console.log(error);
      }
    );
  }
};
/* <============= */

const goBack = () => {
  router.go(-1);
};

const onKeyDown = (event) => {
  if (event.keyCode == 13) {
    handleKeywordSearch();
  }
};
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">등록할 장소 찾아보기</div>
      <button id="goBackBtn" @click="goBack"></button>
      <div class="items">
        <!-- =============> -->
        <input type="text" v-model="keyword" id="searchBox" @keydown="onKeyDown" />
        <button @click="handleKeywordSearch" id="searchBtn">검색</button>
        <div class="items2 scrollbar">
          <template v-for="(place, index) in keywordPlaces" :key="index">
            <place-item :place="place" @detail="handleAdd"></place-item>
          </template>
        </div>
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
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}
.items2 {
  position: relative;
  width: 90%;
  height: 84%;
  background-color: #016ef5;
  color: #ffffff;
  display: flex;
  flex-direction: column;
  align-content: center;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}
#goBackBtn {
  position: fixed;
  top: 14%;
  left: 2%;
  width: 50px;
  height: 50px;
  background-color: transparent;
  background-image: url(../../assets/img/goback.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
}
#searchBox {
  display: inline-block;
  width: 70%;
  height: 30px;
  font-size: 18px;
  color: black;
}
#searchBtn {
  display: inline-block;
  width: 18%;
  height: 35px;
  margin-left: 1%;
  font-size: 18px;
  background-color: white;
  border: none;
}
</style>
