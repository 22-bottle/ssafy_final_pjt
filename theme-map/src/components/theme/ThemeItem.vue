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
  <div id="themeItem">
    <router-link class="theme" :to="{ name: 'detail', params: { themeId: theme.themeId } }">
      <div>
        <div class="theme-name">{{ theme.themeName }}</div>
        <div class="theme-detail">{{ name }}  {{ placeNum }}개의 장소</div>
        <div class="theme-likes">
          <span>{{ theme.likeSum }}</span>
          <span class="like">⭐</span>
        </div>
      </div>
    </router-link>
  </div>
  <br />
</template>

<style scoped>
#themeItem {
  width: 100%;
  height: 120px;
  background-color: white;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.theme{
  position: relative;
  width: 90%;
  height: 100%;
  background-color: #ffffff;
  text-decoration-line: none;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 15px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.25);
}
.theme-name{
  position: relative;
  color: black;
  font-size: 25px;
  margin-bottom: 3%;
}
.theme-detail{
  position: relative;
  color: black;
}
.theme-likes {
  position: relative;
  top: 10%;
  width: 100%; 
  text-align: end;
}
.like {
  margin-left: 5%;
}
.mt-3 {
  margin-top: 3%;
}
</style>
