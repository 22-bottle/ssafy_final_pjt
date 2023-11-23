<script setup>
import { ref, onMounted } from 'vue';
import { power } from '@/api/editor';
import EditorThemeList from '../components/editor/EditorThemeList.vue';

const editors = ref([]);
const themes = ref([[]]);

onMounted(() => {
  initialize();
});

const initialize = () => {
  getPowerEditors();
};

const getPowerEditors = () => {
  power(
    ({ data }) => {
      console.log(data);
      editors.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="power">
    <div class="title">파워 에디터</div>
    <div id="wrapper" v-for="(editor, index) in editors" :key="editor.editorId">
      <div class="editor">
        <button id="crown" v-if="index < 3"></button>
        <span>&nbsp;{{ editor.editorName }}</span>
        <span> ⭐{{ editor.likeSum }}</span>
      </div>
      <div class="theme-container">
        <editor-theme-list :editorId="editor.editorId" type="all"></editor-theme-list>
      </div>
    </div>
  </div>
</template>

<style scoped>
.power {
  margin-top: 3%;
  padding: 8%;
  height: 100%;
  background-color: white;
}

.title {
  font-size: 60px;
}
#wrapper {
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
}
.editor {
  font-size: 30px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 3%;
}
.theme-container {
  margin-top: 2%;
  width: 100%;
  height: 30%;
}
#crown {
  width: 45px;
  height: 45px;
  background-color: transparent;
  border: none;
  background-image: url(../assets/img/crown.png);
}
#themeItem {
  width: 20%;
}
</style>
