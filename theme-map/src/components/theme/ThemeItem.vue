<script setup>
import { ref, onMounted } from 'vue';
import { themePlace } from '@/api/place';
import { editorName } from '@/api/editor';

const props = defineProps({ theme: Object });
const placeNum = ref(null);
const name = ref(null);

onMounted(() => {
  initialize();
});

const initialize = () => {
  themePlace(
    theme.themeId,
    ({ data }) => {
      placeNum.value = data.length;
    },
    (error) => {
      console.log(error);
    }
  );
  editorName(
    theme.editorId,
    ({ data }) => {
      name.value = data.name.editorName;
    },
    (error) => {
      console.log(error);
    }
  );
};

const theme = props.theme;
</script>

<template>
  <div id="container">
    <router-link class="theme mt-3" :to="{ name: 'detail', params: { themeId: theme.themeId } }">
      <div class="router">
          <label class="theme-name">{{ theme.themeName }}</label><br/>
          <label class="theme-detail">{{ name }}  {{ placeNum }}개의 장소</label><br/>
          <div class="theme-likes">
            <label>{{ theme.likeSum }}</label>
            <span class="like">⭐</span>
          </div>
        </div>
    </router-link>
    <br>
  </div>
  <br />
</template>

<style scoped>
#container {
  width: 100%;
  height: 40%;
  background-color: #f5fffa;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-top: 3%;
}
.theme{
  width: 90%;
  height: 100%;
  background-color: #ffffff;
  text-decoration-line: none;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.25);
}
.router {
  width: 100%; height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.theme-name{
  color: black;
  font-size: 25px;
}
.theme-detail{
  color: black;
}
.theme-likes {
  width: 63%; 
  margin-top: 50%;
  text-align: end;
}
.like {
  margin-left: 5%;
}
.mt-3 {
  margin-top: 3%;
}
</style>
