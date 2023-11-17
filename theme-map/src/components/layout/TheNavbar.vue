<script setup>
import { computed } from 'vue';
import { useEditorStore } from "@/stores/editor";

const editorStore = useEditorStore();

const { cIsLogin } = editorStore;

const editorStatus = computed(() => cIsLogin.value ? "마이페이지" : "로그인");

const routerName = computed(() => cIsLogin.value ? "mypage" : "login");

</script>

<template>
  <div class="flex fixed" id="navbar">
    <span>
      <router-link :to="{ name: 'home' }" class="title" id="logo">갈만할지도</router-link>
    </span>
    <div class="flex" id="nav_btns">
      <router-link :to="{ name: 'home' }" id="nav_btn">지도로</router-link>
      <router-link :to="{ name: 'theme' }" id="nav_btn">테마별로</router-link>
      <router-link :to="{ name: 'editor' }" id="nav_btn">에디터별로</router-link>
      <router-link :to="{ name: routerName }" class="log_btn">{{ editorStatus }}</router-link>
      <template v-if="cIsLogin">
        <router-link>{{ cIsLogin }}</router-link>
      </template>
    </div>
  </div>
</template>

<style scoped>
body,
html {
  width: 100%;
}
a {
  text-decoration: none;
}
.flex {
  display: flex;
}
.fixed {
  position: fixed;
}
#navbar {
  top: 0;
  left: 0;
  width: 96%;
  flex-direction: row;
  justify-content: space-between;
  padding: 2%;
  box-shadow: 0 0 28px rgba(0, 0, 0, 0.25);
  background-color: white;
  z-index: 11;
}
#logo {
  font-size: 40px;
}
#nav_btns {
  width: 60%;
  flex-direction: row;
  justify-content: space-around;
}
.router-link-active#nav_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  border-bottom: 5px solid;
  color: #016ef5;
  display: flex;
  justify-content: center;
  align-items: center;
}
#nav_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  color: black;
  display: flex;
  justify-content: center;
  align-items: center;
}
.nav_btn:hover {
  color: #016ef5;
  border-bottom: 5px solid;
}
.log_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: solid;
  border-radius: 15px;
  border-color: #016ef5;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
