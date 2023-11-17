<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useEditorStore } from "@/stores/editor";

const loginCheck = ref(false);
const router = useRouter();
const editorStore = useEditorStore();

const { isLogin } = editorStore;

onMounted(() => {
  initialize();
});


const initialize = () => {
  console.log("Enter init method");
  loginCheck.value = storeToRefs(isLogin);
}

const editorStatus = computed(() => {
  return isLogin.value ? "마이페이지" : "로그인";
});

const routerName = computed(() => {
  return isLogin.value ? "mypage" : "login";
});

function handelEditorStatus() {
  console.log("Enter handelEditorStatus method");
  router.push(`${routerName.value}`);
}

</script>

<template>
  <div class="flex fixed" id="navbar">
<<<<<<< HEAD
    <span class="title" id="logo">갈만할지도</span>
    <div class="flex" id="nav_btns">
      <button class="selected_btn">지도로</button>
      <button class="nav_btn">테마별로</button>
      <button class="nav_btn">에디터별로</button>
      <button class="log_btn" @click="handelEditorStatus">{{ editorStatus }}</button>
=======
    <span>
      <router-link :to="{ name: 'home' }" class="title" id="logo">갈만할지도</router-link>
    </span>
    <div class="flex" id="nav_btns">
      <router-link :to="{ name: 'home' }" id="nav_btn">지도로</router-link>
      <router-link :to="{ name: 'theme' }" id="nav_btn">테마별로</router-link>
      <router-link :to="{ name: 'editor' }" id="nav_btn">에디터별로</router-link>
      <router-link :to="{ name: '' }" class="log_btn">로그인</router-link>
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
    </div>
  </div>
</template>

<style scoped>
<<<<<<< HEAD
body, html {
  width: 100%;
}
=======
body,
html {
  width: 100%;
}
a {
  text-decoration: none;
}
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
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
<<<<<<< HEAD
  box-shadow: 0 0 28px rgba(0,0,0,0.25);
=======
  box-shadow: 0 0 28px rgba(0, 0, 0, 0.25);
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
  background-color: white;
  z-index: 11;
}
#logo {
  font-size: 40px;
}
#nav_btns {
  width: 60%;
  flex-direction: row;
<<<<<<< HEAD
  justify-content: space-around
}
.selected_btn {
=======
  justify-content: space-around;
}
.router-link-active#nav_btn {
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  border-bottom: 5px solid;
  color: #016ef5;
<<<<<<< HEAD
}
.nav_btn {
=======
  display: flex;
  justify-content: center;
  align-items: center;
}
#nav_btn {
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  color: black;
<<<<<<< HEAD
=======
  display: flex;
  justify-content: center;
  align-items: center;
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
}
.nav_btn:hover {
  color: #016ef5;
  border-bottom: 5px solid;
}
.log_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
<<<<<<< HEAD
  border-radius: 15px;
  border-color: #016ef5;
=======
  border: solid;
  border-radius: 15px;
  border-color: #016ef5;
  display: flex;
  justify-content: center;
  align-items: center;
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
}
</style>
