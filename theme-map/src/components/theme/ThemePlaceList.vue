<script setup>
import { ref, onMounted, inject } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from '@/stores/editor';
import { useRoute, useRouter } from 'vue-router';
import { themePlace } from '@/api/place';
import { curTheme } from '@/api/theme';
import PlaceItem from '@/components/map/PlaceItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';

const editorStore = useEditorStore();
const { isLogin } = storeToRefs(editorStore);
const router = useRouter();

const themePlaces = ref([]);
const theme = ref({
  themeId: '',
  themeName: '',
  description: '',
  editorId: '',
  type: '',
  visible: '',
  likeSum: '',
});

const route = useRoute();

onMounted(() => {
  getTheme();
  getThemePlace();
});

const getTheme = () => {
  curTheme(
    route.params.themeId,
    ({ data }) => {
      theme.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getThemePlace = () => {
  themePlace(
    route.params.themeId,
    ({ data }) => {
      console.log(data);
      themePlaces.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

/* =============> */
const emit = defineEmits(['clickPlace']);

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
  console.log(visibility.value, clicked.value);
  emit('clickPlace', place);
};

const clicked = inject('clicked');

const goBack = () => {
  router.go(-1);
}
/* <============= */
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <button @click="goBack">뒤로가기</button>
      <div class="name">{{ theme.themeName }}</div>
      <div class="items">
        <div>{{ theme.description }}</div>
        <!-- <place-item v-for="(place, index) in hotPlaces" :key="place.placeId" :place="place"></place-item> -->
        <!-- =============> -->
        <div class="items scrollbar">
          <place-item v-for="(place, index) in themePlaces" :key="index" :place="place" @detail="handleDetail"></place-item>
        </div>
        <template v-if="isLogin && theme.type == 1">
          <button style="width: 100px; height: 35px; position: absolute; top: 100%">
            <router-link :to="{ name: 'keyword' }">장소등록</router-link>
          </button>
        </template>
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
