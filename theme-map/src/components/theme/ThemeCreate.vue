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
    <form class="inputform mt-1">
      <div class="inputContainer mt-3">
        <label for="themeName" class="section">테마 이름</label>
        <input type="text" id="themeName" name="themeName" class="input" v-model="theme.themeName" /><br />
      </div>
      <div class="inputContainer mt-3">
        <label for="description" class="section">테마 설명</label>
        <input type="text" id="description" name="description" class="input" v-model="theme.description" /><br />
      </div>
      <div class="inputContainer mt-3">
        테마 유형
        <input type="radio" id="public" name="type" value="1" v-model="theme.type" checked @click="checkPublic" />
        <label for="public">Public</label>
        <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
        <label for="private">Private</label><br />
      </div>
      <div class="inputContainer">
        공개 여부
        <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" :checked="isPublic" />
        <label for="visible">공개</label>
        <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
        <label for="invisible">비공개</label><br />
      </div>
    </form>
    <button class="btn mt-3" @click="onThemeCreate">등록하기</button>
  </div>
</template>

<style scoped>
#container {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 7%;
  background-color: #f5fffa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
#header {
  width: 73%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.title {
  position: relative;
  width: 100%;
  left: 5.5%;
  font-size: 50px;
  margin-bottom: 20px;
}
.inputform {
  width: 65%;
  height: 50%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
.inputContainer {
  position: relative;
  width: 80%;
  height: 100%;
  display: inline-block;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.section {
  width: 100%;
  height: 38%;
  text-align: start;
  color:  #016ef5;
  font-size: 20px;
  margin-bottom: 1px;
}
.input {
  position: relative;
  width: 85%;
  height: 30%;
  left: 1%;
  font-size: 20px;
  border: 1px solid #808080;
  border-radius: 10px;
}
.btn {
  width: 10%;
  height: 5%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
  color: #ffffff;
  font-size: 23px;
}
.mt-1 {
  margin-top: 1%;
}
.mt-3 {
  margin-top: 3%;
}
</style>
