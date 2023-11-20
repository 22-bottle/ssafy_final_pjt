<script setup>
import { ref, onMounted, watch, computed, defineProps } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from '@/stores/editor';
import { themesOfPlace } from '@/api/theme';
import { registComment, commentsOfPlace } from '@/api/comment';
import { keepScore } from '@/api/place';
import router from '../../router';

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

const comment = ref({
  placeId: '',
  content: '',
});

const themeInfos = ref([]);
const commentInfos = ref([]);

const scoreDto = ref({
  placeId: '',
  score: '',
});

onMounted(() => {
  initialize();
});

// placeToView 객체가 변경될 때마다 initialize 함수를 호출
watch(
  () => props.place,
  (newPlace, oldPlace) => {
    if (newPlace !== oldPlace) {
      initialize();
    }
  },
  { deep: true }
);

function initialize() {
  place.value = props.place;
  comment.value.placeId = place.value.placeId;
  getThemesOfPlace();
  getCommentsOfPlace();
}
const starRating = computed(() => {
  const rating = props.place.scoreCount === 0 ? 0 : (props.place.scoreSum / props.place.scoreCount).toFixed(1);
  const validRating = !isNaN(parseFloat(rating)) && isFinite(rating) ? parseFloat(rating) : 0;

  const fullStars = Math.max(0, Math.min(5, Math.floor(validRating)));
  const halfStar = validRating % 1 >= 0.5 ? 1 : 0;
  const emptyStars = 5 - fullStars - halfStar;
  return { validRating, fullStars, halfStar, emptyStars };
});

const getThemesOfPlace = () => {
  themesOfPlace(
    place.value.placeId,
    ({ data }) => {
      console.log(data);
      themeInfos.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getCommentsOfPlace = () => {
  commentsOfPlace(
    place.value.placeId,
    ({ data }) => {
      console.log(data);
      commentInfos.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const handelComment = () => {
  console.log('Enter handelComment method');
  registComment(
    comment.value,
    () => {
      console.log('댓글 등록 완료!!');
    },
    (error) => {
      console.log(error);
    }
  );
};

const scoring = (event) => {
  scoreDto.value.placeId = place.value.placeId;
  scoreDto.value.score = event.target.id;
  keepScore(
    scoreDto.value,
    () => {
      router.go(0);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div id="detail">
    <div id="placeInfo">
      <div>{{ place.placeName }}</div>
      <div>{{ place.address }}</div>
      <div style="color: aliceblue">{{ starRating.validRating }} 점</div>
      <div class="star-rating">
        <span v-for="n in Math.max(0, starRating.fullStars)" :key="n" class="star full">&#9733;</span>
        <span v-if="starRating.halfStar" class="star empty">&#9734;</span>
        <span v-for="n in starRating.emptyStars" :key="n" class="star empty">&#9734;</span>
      </div>
      <div>
        <span class="star empty" id="1" @click="scoring">&#9734;</span>
        <span class="star empty" id="2" @click="scoring">&#9734;</span>
        <span class="star empty" id="3" @click="scoring">&#9734;</span>
        <span class="star empty" id="4" @click="scoring">&#9734;</span>
        <span class="star empty" id="5" @click="scoring">&#9734;</span>
      </div>
      <span style="color: violet">일단 장소 테마들: </span><br />
      <template v-for="theme in themeInfos" :key="theme.themeId">
        <div style="background-color: blueviolet">
          <div>{{ theme.themeName }}</div>
        </div>
      </template>
      <span style="color: violet">일단 댓글: </span>
      <template v-for="comment in commentInfos" :key="comment.commentId">
        <div style="background-color: aquamarine">
          <div>{{ comment.content }}</div>
          <hr />
        </div>
      </template>
      <template v-if="isLogin">
        <div style="width: 100%; height: 50%; background-color: black">
          <span>평가/댓글</span>
          <form>
            <div class="baseContainer mt-3">
              <label for="content" class="">후기: </label>
              <input type="text" id="content" class="" v-model="comment.content" />
            </div>
            <button type="submit" class="btn" @click="handelComment">
              <label for="btn" class="btndata">댓글 등록</label>
            </button>
          </form>
        </div>
      </template>
    </div>
  </div>
</template>

<style scoped>
.star-rating {
  width: 100px;
  height: 20px;
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
