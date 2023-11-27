<script setup>
import { useEditorStore } from '@/stores/editor';
import EditorThemeList from '@/components/editor/EditorThemeList.vue';

const editorStore = useEditorStore();
const { cEditorDto } = editorStore;
</script>

<template>
  <div class="mypage">
    <div class="editor">
      <span>
        <span class="editor-name title">{{ cEditorDto.editorName }}</span>
        <span class="editor-name title"> ⭐{{ cEditorDto.likeSum }}</span>
      </span>
      <router-link :to="{ name: 'modify' }" class="btn btn-data">내 정보 수정</router-link>
    </div>
    <div class="theme-container">
      <div class="theme-section">⭐ 내가 만든 테마</div>
      <editor-theme-list :editorId="cEditorDto.editorId" type="all"></editor-theme-list>
    </div>
    <div class="theme-container">
      <div class="theme-section">💘 내가 저장한 테마</div>
      <editor-theme-list :editorId="cEditorDto.editorId" type="like"></editor-theme-list>
    </div>
  </div>
</template>
<style scoped>
.mypage {
  padding: 8%;
  margin-top: 3%;
}

.editor {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.editor-name {
  font-size: 60px;
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
  height: 30%;
}

.theme-section {
  position: relative;
  color: black;
  font-size: 30px;
  margin-bottom: 3%;
}
.btn-data {
  font-size: 23px;
  text-decoration-line: none;
}
.themeItem {
  width: 20%;
}
</style>
