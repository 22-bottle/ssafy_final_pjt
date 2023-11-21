<script setup>
import { ref, onMounted } from 'vue';
import { themesOfEditor } from '@/api/theme';
import { power } from '@/api/editor';
import EditorThemeList from '@/components/editor/EditorThemeList.vue';

const editors = ref([]);
const themes = ref([[]]);

onMounted(() => {
  initialize();
});

const initialize = () => {
  console.log('Enter initialize method');
  getPowerEditors();
  // getThemesOfEditor();
};

const getPowerEditors = () => {
  console.log('Enter getPowerEditors method');
  power(
    ({ data }) => {
      console.log(data);
      editors.value = data;
      for (let index = 0; index < data.length; index++) {
        getThemesOfEditor(editors.value[index].editorId, index);
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const getThemesOfEditor = (editorId, index) => {
  themesOfEditor(
    editorId,
    ({ data }) => {
      themes.value[index] = data;
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
    <template v-for="editor in editors" :key="editor.editorId">
      <div class="editor">
        <label class="editor-name">{{ editor.editorName }}</label>
        <label class="editor-name"> &#9734;{{ editor.likeSum }}</label>
      </div>
      <template v-for="(themess, index) in themes" :key="index">
        <div class="theme-container">
          <div>
            <editor-theme-list :themes="themess"></editor-theme-list>
          </div>
        </div>
      </template>
    </template>
  </div>
</template>

<style scoped>
.power {
  position: absolute;
  top: 20%;
  left: 15%;
  width: 100%;
  height: 100%;
  background-color: #f5fffa;
}

.title {
  font-size: 70px;
  margin-bottom: 20px;
}

.editor {
  position: relative;
}

.editor-name {
}

.theme-container {
  margin-bottom: 3%;
  width: 72%;
  height: 30%;
  background-color: tan;
}
</style>
