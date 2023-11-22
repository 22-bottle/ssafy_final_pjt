<script setup>
import { ref, onMounted, watch } from 'vue';
import { isThere, isInTheme, whoCreated, deletePlace } from '@/api/place';
import { useRoute } from 'vue-router';
import { useEditorStore } from '@/stores/editor';

const props = defineProps({ place: Object });
const route = useRoute();
const editorStore = useEditorStore();
const { cEditorDto } = editorStore;

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

watch(
  () => props.place,
  (newPlace) => {
    place.value = newPlace;
  },
  { deep: true }
);

onMounted(() => {
  place.value = props.place;
  checkIsThere();
  if (route.name == 'detail') {
    getWhoCreated();
  }
  raise(0, 0);
});

const moveToKakao = () => {
  window.open('https://place.map.kakao.com/' + place.value.placeId);
};
/* ========> */
const emit = defineEmits(['detail', 'delete']);

const handlePlace = (event) => {
  emit('detail', place.value, event.target.id);
};

const editorId = ref('');
const getWhoCreated = () => {
  whoCreated(
    route.params.themeId,
    props.place.placeId,
    ({ data }) => {
      editorId.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
/* <======== */

const thereIs = ref(false); //place table에 있는지
const thereIn = ref(false); //place_in_theme table에 있는지
const checkIsThere = () => {
  isThere(
    props.place.placeId,
    ({ data }) => {
      thereIs.value = data;
      if (data) {
        checkInThere();
      }
    },
    (error) => {
      console.log(error);
    }
  );
};
const checkInThere = () => {
  isInTheme(
    route.params.themeId,
    props.place.placeId,
    ({ data }) => {
      thereIn.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const goDelete = () => {
  deletePlace(
    route.params.themeId,
    props.place.placeId,
    () => {
      emit('delete');
    },
    (error) => {
      console.log(error);
    }
  );
};

const raise = (param, count) => {
  starRating.value.rating =
    props.place.scoreCount === 0
      ? 0
      : ((Number(props.place.scoreSum) + Number(param)) / (Number(props.place.scoreCount) + Number(count))).toFixed(1);
  starRating.value.validRating =
    !isNaN(parseFloat(starRating.value.rating)) && isFinite(starRating.value.rating)
      ? parseFloat(starRating.value.rating)
      : 0;
  starRating.value.fullStars = Math.max(0, Math.min(5, Math.floor(starRating.value.validRating)));
  starRating.value.halfStar = starRating.value.validRating % 1 >= 0.5 ? 1 : 0;
  starRating.value.emptyStars = 5 - starRating.value.fullStars - starRating.value.halfStar;
};

const starRating = ref({
  rating: 0,
  validRating: 0,
  fullStars: 0,
  halfStar: 0,
  emptyStars: 0,
});
</script>

<template>
  <div id="item">
    <div id="placeName">{{ place.placeName }}</div>
    <span class="mr3" style="color: red">{{ place.scoreCount == 0 ? 0 : (place.scoreSum / place.scoreCount).toFixed(1)}}</span>
    <span class="star-rating mr3">
      <span v-for="n in Math.max(0, starRating.fullStars)" :key="n" class="star full">&#9733;</span>
      <span v-if="starRating.halfStar" class="star empty">&#9734;</span>
      <span v-for="n in starRating.emptyStars" :key="n" class="star empty">&#9734;</span>
    </span>
    <span style="color: black">({{ place.scoreCount == 0 ? 0 : place.scoreCount }}건)</span>
    <div class="mt5">{{ place.address }}</div>
    <div>{{ place.phone }}</div>
    <a href="" @click="moveToKakao">카카오맵에서 보기</a>
    <template v-if="route.name == 'keyword'">
      <template v-if="!thereIs">
        <button id="1" @click="handlePlace" class="addBtn">등록하기</button>
      </template>
      <template v-else>
        <template v-if="!thereIn">
          <button id="2" @click="handlePlace" class="addBtn">등록하기</button>
        </template>
        <template v-else>
          <template v-if="route.name != 'detail'">
            <button class="addBtn">등록된 장소</button>
          </template>
        </template>
      </template>
    </template>
    <template v-else>
      <button class="detailBtn" @click="handlePlace">자세히 보기</button>
    </template>
    <template v-if="route.name == 'detail' && editorId == cEditorDto.editorId">
      <button class="deleteBtn" @click="goDelete">삭제</button>
    </template>
  </div>
  <br />
</template>

<style scoped>
* {
  background-color: white;
}
#item {
  width: 90%;
  padding: 10px;
}
#item div {
  color: black;
  margin-bottom: 5px;
}
.mt5 {
  margin-top: 5px;
}
.mr3 {
  margin-right: 3px;
}
#placeName {
  font-size: 25px;
}
.star-rating {
  width: 100px;
  height: 20px;
}
.star {
  color: red;
}
.detailBtn {
  position: absolute;
  right: 5%;
  border: none;
  border-radius: 4px;
  color: white;
  background-color: #016ef5;
  cursor: pointer;
}
.deleteBtn {
  position: absolute;
  right: 30%;
  border: none;
  border-radius: 4px;
  color: white;
  background-color: red;
  cursor: pointer;
}
.addBtn {
  position: absolute;
  right: 6%;
  border: none;
  border-radius: 4px;
  color: white;
  background-color: green;
  cursor: pointer;
}
</style>
