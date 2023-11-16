<script setup>
import { ref } from 'vue';
import { regist } from '@/api/editor';

const loginEditor = ref({
  editorName: "",
  id: "",
  pw: "",
});

const handelRegist = () => {
  console.log("regist attempt");
  regist(
    loginEditor.value,
    (response) => {
      console.log(response.data.message);
    },
    (error) => {
      console.log(error);
    }
  );
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form>
          <div class="mb">
            <label for="username" class="form-label">이름 : </label>
            <input type="text" class="form-control" v-model="loginEditor.editorName" placeholder="이름..." />
          </div>
          <div class="mb">
            <label for="id" class="form-label">아이디 : </label>
            <input type="text" class="form-control" v-model="loginEditor.id" placeholder="아이디..." />
          </div>
          <div class="mb">
            <label for="email" class="form-label">이메일 : </label>
            <input type="text" class="form-control" v-model="loginEditor.emailId" placeholder="email id..." />@
            <input type="text" class="form-control" v-model="loginEditor.emailDomain" placeholder="email domain..." />
          </div>
          <div class="mb">
            <label for="pw" class="form-label">비밀번호 : </label>
            <input type="text" class="form-control" v-model="loginEditor.pw" placeholder="비밀번호..." />
          </div>
          <div class="mb">
            <label for="pwcheck" class="form-label">비밀번호확인 : </label>
            <input type="text" class="form-control" id="pwcheck" v-model="loginEditor.pw" placeholder="비밀번호확인..." />
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="handelRegist">회원가입</button>
            <button type="button" class="btn btn-outline-primary ms-1 mb-3">회원정보찾기</button>
            <button type="reset" class="btn btn-outline-success ms-1 mb-3">초기화</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
