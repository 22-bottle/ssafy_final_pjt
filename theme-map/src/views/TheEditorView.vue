<script setup>
import EditorLogin from '@/components/editor/EditorLogin.vue';
import EditorMyPage from '@/components/editor/EditorMyPage.vue';
import EditorRegister from '@/components/editor/EditorRegister.vue';
import EditorLogout from '@/components/editor/EditorLogout.vue';

</script>

<template>
  <div class="container text-center mt-3">
    <div>TheEditorView.vue</div>
    <EditorRegister />
    <EditorLogin />
    <EditorLogout />
    <EditorMyPage />
    <!-- <router-view></router-view> -->
  </div>
</template>

<style>
mark.orange {
  background: linear-gradient(to top, rgb(243, 164, 18) 20%, transparent 30%);
}
</style>
