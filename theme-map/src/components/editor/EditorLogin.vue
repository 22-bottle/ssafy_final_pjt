<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useEditorStore } from "@/stores/editor";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const editorStore = useEditorStore();
const menuStore = useMenuStore();

const { isLogin } = storeToRefs(editorStore);
const { editorLogin } = editorStore;
const { changeMenuState } = menuStore;

const loginEditor = ref({
  id: "",
  pw: "",
});

const handleLogin = async () => {
  console.log("login attempt");
  await editorLogin(loginEditor.value);

  if (isLogin) {
    console.log("로그인 성공!!");
    changeMenuState();
    router.push("/");
  } else {
    console.log("로그인 실패..");
    router.go(0);
  }
};
const handleSingIn = async () => {
  console.log("signin attempt");
  await editorLogin(loginEditor.value);

  if (isLogin) {
    console.log("로그인 성공!!");
    changeMenuState();
    router.push("/");
  } else {
    console.log("로그인 실패..");
    router.go(0);
  }
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="text-center">
          <mark class="orange text-center">로그인</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <form>
          <!-- 아이디 비밀번호 입력폼 -->
          <div class="mb-3 text-start">
            <label for="id" class="form-label">아이디</label>
            <input
              type="text"
              class="form-control"
              v-model="loginEditor.id"
            />
          </div>
          <div class="mb-3 text-start">
            <label for="pw" class="form-label">비밀번호</label>
            <input
              type="password"
              class="form-control"
              v-model="loginEditor.pw"
              @keyup.enter="login"
            />
          </div>

          <!-- JWT 설정 -->
          <div class="form-check mb-3 text-start">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 로그인 유지하기 </label>
          </div>

          <!-- 버튼 -->
          <div class="text-center">
            <div>
              <button type="button" class="btn btn-outline-primary mt-3" @click="handleLogin">
                로그인>
              </button>
            </div>
            <div>
              <button type="button" class="btn mb-5" @click="handleSingIn">회원가입</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
