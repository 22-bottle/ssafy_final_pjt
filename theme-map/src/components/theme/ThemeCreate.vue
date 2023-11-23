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
    <div class="title" id="header">새 테마 등록하기</div>
    <form class="inputform mt-1">
      <div class="inputContainer mt-3">
        <label for="themeName" class="section">테마 이름</label>
        <input type="text" id="themeName" name="themeName" class="input" v-model="theme.themeName" />
      </div>
      <div class="inputContainer mt-3">
        <label for="description" class="section">테마 설명</label>
        <input type="text" id="description" name="description" class="input" v-model="theme.description" />
      </div>
      <div class="inputContainer mt-3">
        <span class="section">테마 유형</span>
        <div class="radios">
          <div>
            <input type="radio" id="public" name="type" value="1" v-model="theme.type" checked @click="checkPublic" />
            <label for="public">Public</label>
          </div>
          <div>
            <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
            <label for="private">Private</label><br />
          </div>
        </div>
      </div>
      <div class="inputContainer mt-3">
        <span class="section">공개 여부</span>
        <div class="radios">
          <div>
            <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" :checked="isPublic" />
            <label for="visible">공개</label>
          </div>
          <div>
            <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
            <label for="invisible">비공개</label><br />
          </div>
        </div>
      </div>
      <button class="btn mt-3" @click="onThemeCreate">등록하기</button>
    </form>
  </div>
</template>

<style scoped>
#container {
  width: 100%;
  margin-top: 3%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
#header {
  margin-top: 125px;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.title {
  font-size: 60px;
  margin-bottom: 20px;
}
.inputform {
  width: 40%;
  height: 60%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
.inputContainer {
  width: 43%;
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.section {
  width: 100%;
  height: 30%;
  text-align: start;
  color: #808080;
  font-size: 20px;
  margin-bottom: 1px;
}
.input {
  width: 100%;
  height: 30%;
  font-size: 20px;
  border: 1px solid #808080;
  border-radius: 10px;
}
.btn {
  width: 43%;
  height: 50%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
  color: #ffffff;
  font-size: 23px;
  margin-bottom: 5%;
}
.mt-1 {
  margin-top: 1%;
}
.mt-3 {
  margin-top: 3%;
}
.radios label {
  color: #808080;
}
.radios {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  font-size: 18px;
  margin-top: 5%;
}
.mb-3 {
  margin-bottom: 3%;
}
</style>
