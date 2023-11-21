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
    keywordPlaces.value = {...props.placeList};
    console.log("바뀌었습니다.",keywordPlaces.value);
  },
  { deep: true }
);

/* =============> */
const emit = defineEmits(['detail']);

const handleKeywordSearch = async () => {
  console.log('Enter handleKeywordSearch method');
  emit('keyword', keyword.value);
};
const handleAdd = (place) => {
  // 장소를 생성
  createPlace(
    place,
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
  // 테마와 장소를 연결
  linkPlace(
    {
      themeId: route.params.themeId,
      placeId: place.placeId,
      editorId: editorId.value,
    },
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
/* <============= */

const goBack = () => {
  router.go(-1);
};
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <div class="name">키워드 검색 테스트</div>
      <div class="items">
        <button @click="goBack">뒤로가기</button>
        <!-- =============> -->
        <div>
          <input type="text" v-model="keyword" style="width: 200px; height: 30px" />
          <button type="button" @click="handleKeywordSearch" style="width: 50px; height: 35px">검색</button>
        </div>
        <div class="items scrollbar">
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
