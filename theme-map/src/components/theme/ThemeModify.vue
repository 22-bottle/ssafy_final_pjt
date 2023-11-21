<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { curTheme, updateTheme } from '@/api/theme';

const route = useRoute();
const router = useRouter();

const theme = ref({
  themeId: route.params.themeId,
  themeName: '',
  description: '',
  editorId: '',
  type: '',
  visible: '',
  likeSum: '',
});

onMounted(() => {
  getTheme();
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

const isPublic = ref(true);

const checkPublic = () => {
  isPublic.value = true;
};
const checkPrivate = () => {
  isPublic.value = false;
};

const onThemeModify = (event) => {
  event.preventDefault();
  updateTheme(
    theme.value,
    ({}) => {
      router.replace({ name: 'detail', parmas: { themeId: theme.value.themeId } });
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">테마 수정하기</div>
    </div>
    <form action="">
      <label for="themeName">테마 이름 : </label>
      <input type="text" id="themeName" name="themeName" v-model="theme.themeName" /><br />
      <label for="description">테마 설명 : </label>
      <input type="text" id="description" name="description" v-model="theme.description" /><br />
      테마 유형 :
      <input type="radio" id="public" name="type" value="1" v-model="theme.type" checked @click="checkPublic" />
      <label for="public">Public</label>
      <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
      <label for="private">Private</label><br />
      공개 여부 :
      <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" :checked="isPublic" />
      <label for="visible">공개</label>
      <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
      <label for="invisible">비공개</label><br />
      <button @click="onThemeModify">수정하기</button>
    </form>
  </div>
</template>

<style scoped>
#container {
  margin-top: 3%;
  padding: 8%;
}
#header {
  font-size: 40px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
