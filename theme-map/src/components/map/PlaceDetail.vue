<script setup>
import { ref, onMounted, watch } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from '@/stores/editor';
import { themesOfPlace } from '@/api/theme';
import { registComment, commentsOfPlace } from '@/api/comment';
import { keepScore } from '@/api/place';

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
  raise(0, 0);
}

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

const handelComment = (event) => {
  console.log('Enter handelComment method');
  event.preventDefault();
  registComment(
    comment.value,
    () => {
      console.log('댓글 등록 완료!!');
      commentInfos.value.push({ ...comment.value });
      getCommentsOfPlace();
    },
    (error) => {
      console.log(error);
    }
  );
};

const scoring = (event) => {
  evaluated.value = true;
  scoreDto.value.placeId = place.value.placeId;
  scoreDto.value.score = event.target.id;
  keepScore(
    scoreDto.value,
    () => {
      updateScore();
      // router.go(0);
    },
    (error) => {
      console.log(error);
    }
  );
};

const emit = defineEmits(['updateScore']);

const updateScore = () => {
  console.log('Enter updateScore method');
  raise(Number(scoreDto.value.score), 1);
  emit('updateScore', true);
};

const evaluated = ref(false);
</script>

<template>
  <div id="detail">
    <div class="name pl20">
      <div>
        {{ place.placeName }}
        <span style="font-size: 18px">
          <span style="color: aliceblue">(</span>
          <span class="star full">&#9733;</span>
          <span style="color: aliceblue">{{ starRating.validRating.toFixed(1) }})</span>
        </span>
      </div>
    </div>
    <div class="scrollbar pl20" id="placeInfo">
      <div class="mb3">🚩 {{ place.address }}</div>
      <div class="mb3 phone">📞 {{ place.phone }}</div>
      <template v-if="isLogin">
        <div class="score">
          <template v-if="!evaluated">
            <span style="color: white">후기를 남겨주세요!</span>
            <span>
              <span class="star empty" id="1" @click="scoring">&#9734;</span>
              <span class="star empty" id="2" @click="scoring">&#9734;</span>
              <span class="star empty" id="3" @click="scoring">&#9734;</span>
              <span class="star empty" id="4" @click="scoring">&#9734;</span>
              <span class="star empty" id="5" @click="scoring">&#9734;</span>
            </span>
          </template>
          <template v-else>
            <span style="color: white">후기를 남겨주세요!</span>
            <span>
              <span v-for="n in Number(scoreDto.score)" :key="n" class="star full">&#9733;</span>
              <span v-for="n in 5 - Number(scoreDto.score)" :key="n" class="star empty">&#9734;</span>
            </span>
          </template>
        </div>
      </template>
      <span style="color: white">여기는 이런 곳이에요!</span><br />
      <div class="themes">
        <template v-for="theme in themeInfos" :key="theme.themeId">
          <router-link :to="{ name: 'detail', params: { themeId: theme.themeId } }">{{ theme.themeName }}</router-link>
        </template>
      </div>
      <span style="color: white">이런 후기들이 있어요!</span><br />
      <div class="comments">
        <template v-for="comment in commentInfos" :key="comment.commentId">
          <div class="comment">↳ {{ comment.content }}</div>
        </template>
      </div>
      <span style="color: white">의견을 남겨주세요!</span><br />
      <template v-if="isLogin">
        <form>
          <textarea name="" id="content" cols="42" rows="5" v-model="comment.content"></textarea>
          <button id="comment-btn" @click="handelComment">저장하기</button>
        </form>
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
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 9;
  border-radius: 5px;
  box-shadow: 10px 10px 20px rgba(0, 0, 0, 0.25);
}
#placeInfo {
  position: relative;
  width: 80%;
  height: 80%;
  background-color: #016ef5;
  position: relative;
  background-color: #016ef5;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
  padding-top: 0;
}
#placeInfo div {
  color: white;
}
.name {
  position: relative;
  width: 80%;
  height: 5%;
  background-color: #016ef5;
  font-size: 28px;
  display: flex;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}
.pl20 {
  padding: 20px;
}
.name div {
  color: white;
}
.mb3 {
  margin-bottom: 3px;
}
.phone {
  margin-bottom: 30px;
}
.score {
  font-size: 20px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.themes {
  margin-top: 15px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.themes a {
  padding: 5px;
  background-color: white;
  color: black;
  border-radius: 5px;
  border: solid 1px black;
}
.comments {
  margin-top: 15px;
  margin-bottom: 30px;
}
textarea {
  resize: none;
  border-radius: 5px;
  margin-top: 10px;
}
#comment-btn {
  width: 98%;
  font-size: 15px;
  background-color: white;
  border: none;
  border-radius: 5px;
  margin-top: 10px;
}
</style>
