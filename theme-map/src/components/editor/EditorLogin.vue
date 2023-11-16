<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
// import { useRouter } from "vue-router";
import { useEditorStore } from "@/stores/editor";

// const router = useRouter();

const editorStore = useEditorStore();

const { isLogin } = storeToRefs(editorStore);
const { editorLogin, getEditorInfo } = editorStore;

const loginEditor = ref({
  id: "",
  pw: "",
});

const handleLogin = async () => {
  console.log("login attempt");
  await editorLogin(loginEditor.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("111. ", token);
  console.log("isLogin: ", isLogin);
  if (isLogin) {
    console.log("로그인 성공아닌가???");
    getEditorInfo(token);
  }
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">로그인</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="mb-3 text-start">
            <label for="id" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              v-model="loginEditor.id"
              placeholder="아이디..."
            />
          </div>
          <div class="mb-3 text-start">
            <label for="pw" class="form-label">비밀번호 : </label>
            <input
              type="password"
              class="form-control"
              v-model="loginEditor.pw"
              @keyup.enter="login"
              placeholder="비밀번호..."
            />
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="handleLogin">
              로그인
            </button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">회원가입</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
