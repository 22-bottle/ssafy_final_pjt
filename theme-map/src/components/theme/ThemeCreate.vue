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
  type: '',
  visible: '',
});

const onThemeCreate = (event) => {
  event.preventDefault();
  createTheme(
    theme.value,
    () => {
      router.replace({ name: 'detail' });
      router.replace({ name: 'keyword', params: { themeId: 1 } });
    },
    (error) => {
      console.log(theme.value);
      console.log(error);
    }
  );

  // 그냥 이동 테스트 themeID = 1
  //  router.replace({ name: 'keyword', params: { themeId: 1 } });
};

const isPublic = ref(false);

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
      <input type="radio" id="public" name="type" value="1" v-model="theme.type" @click="checkPublic" />
      <label for="public">Public</label>
      <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
      <label for="private">Private</label><br />
      공개 여부 :
      <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" />
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
