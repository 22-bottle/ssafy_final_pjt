<script setup>
import { ref, onMounted } from 'vue';
import { themesOfEditor, themesOfLike } from '@/api/theme';
import { useEditorStore } from "@/stores/editor";
import EditorThemeList from '@/components/editor/EditorThemeList.vue';

const editorStore = useEditorStore();
const { cEditorDto } = editorStore;

const themes = ref([]);
const likeThemes = ref([]);

const editor = ref({
  editorId: "",
  id: "",
  editorName: ""
});

onMounted(()=> {
  initialize();
});

function initialize() {
  console.log("Enter initialize method");
  editor.value = cEditorDto.value;

  themesOfEditor(
        cEditorDto.value.editorId,
        ({ data }) => {
        themes.value = data;
        console.log(themes.value);
        },
        (error) => {
        console.log(error);
        }
    );
    themesOfLike(
        cEditorDto.value.editorId,
        ({ data }) => {
        likeThemes.value = data;
        console.log(likeThemes.value);
        },
        (error) => {
        console.log(error);
        }
    );

}

</script>

<template>
  <div class="mypage">
    <div class="editor">
      <label class="editor-name">{{ editor.editorName }}</label>
      <label class="editor-name"> &#9734;{{ editor.likeSum }}</label>
      <div>
        <router-link :to="{ name: 'modify' }" class="btn">내 정보 수정</router-link>
      </div>
    </div>
    <div class="theme-container">
      <span class="theme-section">내가 만든 테마</span>
      <div>
        <editor-theme-list :themes="themes"></editor-theme-list>
      </div>
    </div>
    <div class="theme-container">
      <span class="theme-section">내가 저장한 테마</span>
      <div>
        <editor-theme-list :themes="likeThemes"></editor-theme-list>
      </div>
    </div>
  </div>
</template>
<style scoped>
.mypage {
  position: absolute;
  top: 20%;
  left: 15%;
  width: 100%;
  height: 100%;
  background-color: #f5fffa;
}

.editor {
  position: relative;
}

.editor-name {
  font-size: 70px;
  margin-bottom: 20px;
}

.btn {
  width: 10%;
  height: 45px;
  display: inline-block;
  text-align: center;
  text-decoration: none;
  line-height: 45px;
  background-color: #FFFFFF;
  color: #016ef5;
  font-size: 30px;
  border-style: solid;
  border-radius: 30px;
  border-color: #016ef5;
  border-width: 2px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
}

.btn:hover {
  color: #FFFFFF;
  background-color: #016ef5;
  box-shadow: none;
}

.theme-container {
  margin-top: 3%;
  width: 72%; height: 30%;
  background-color: tan;
}

.theme-section {
  position: relative;
  color: #016ef5;
  font-size: 30px;
}

</style>
