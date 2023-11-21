<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { createTheme } from '@/api/theme';
import { useEditorStore } from '@/stores/editor';

const router = useRouter();

const editorStore = useEditorStore();

const { cEditorDto } = editorStore;

const theme = ref({
  themeName: '',
  description: '',
  editorId: cEditorDto.value.editorId,
  type: '1',
  visible: '1',
});

const onThemeCreate = (event) => {
  event.preventDefault();
  createTheme(
    theme.value,
    ({ data }) => {
      router.replace({ name: 'keyword', params: { themeId: data } });
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
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">새 테마 등록하기</div>
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
      <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" checked />
      <label for="visible">공개</label>
      <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
      <label for="invisible">비공개</label><br />
      <button @click="onThemeCreate">등록하기</button>
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
