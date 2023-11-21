<script setup>
import { useEditorStore } from '@/stores/editor';
import EditorThemeList from '@/components/editor/EditorThemeList.vue';

const editorStore = useEditorStore();
const { cEditorDto } = editorStore;
</script>

<template>
  <div class="mypage">
    <div class="editor">
      <label class="editor-name">{{ cEditorDto.editorName }}</label>
      <label class="editor-name"> &#9734;{{ cEditorDto.likeSum }}</label>
      <div>
        <router-link :to="{ name: 'modify' }" class="btn">내 정보 수정</router-link>
      </div>
    </div>
    <div class="theme-container">
      <span class="theme-section">내가 만든 테마</span>
      <div>
        <editor-theme-list :editorId="cEditorDto.editorId" type="all"></editor-theme-list>
      </div>
    </div>
    <div class="theme-container">
      <span class="theme-section">내가 저장한 테마</span>
      <div>
        <editor-theme-list :editorId="cEditorDto.editorId" type="like"></editor-theme-list>
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
  background-color: #ffffff;
  color: #016ef5;
  font-size: 30px;
  border-style: solid;
  border-radius: 30px;
  border-color: #016ef5;
  border-width: 2px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
}

.btn:hover {
  color: #ffffff;
  background-color: #016ef5;
  box-shadow: none;
}

.theme-container {
  margin-top: 3%;
  width: 72%;
  height: 30%;
  background-color: tan;
}

.theme-section {
  position: relative;
  color: #016ef5;
  font-size: 30px;
}
</style>
