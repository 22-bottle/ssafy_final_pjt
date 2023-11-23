<script setup>
import { ref } from 'vue';
import { regist } from '@/api/editor';
import router from '../../router';

const loginEditor = ref({
  id: '',
  pw: '',
  emailId: '',
  emailDomain: '',
  editorName: '',
});

const handelRegist = () => {
  console.log('Enter handelRegist method');
  regist(
    loginEditor.value,
    () => {
      window.alert('회원가입 성공!!');
      router.replace('/login');
    },
    (error) => {
      console.log(error);
      window.alert('회원가입 실패..');
      router.go(0);
    }
  );
};
</script>

<template>
  <div class="regist">
    <span class="description title">회원가입</span>
    <form class="registform">
      <div class="inputContainer">
        <label for="id" class="registdata">아이디</label>
        <input type="text" id="id" class="input" v-model="loginEditor.id" />
      </div>
      <div class="inputContainer">
        <label for="editorName" class="registdata">이름</label>
        <input type="text" id="editorName" class="input" v-model="loginEditor.editorName" />
      </div>
      <div class="inputContainer">
        <label for="pw" class="registdata">비밀번호</label>
        <input type="password" id="pw" class="input" v-model="loginEditor.pw" />
      </div>
      <div class="inputContainer mt-1">
        <label for="email" class="registdata">이메일</label>
        <div class="emailContainer">
          <input type="text" id="emailId" class="input-email" v-model="loginEditor.emailId" />@
          <input type="text" id="emailDomain" class="input-email" v-model="loginEditor.emailDomain" />
        </div>
      </div>
      <div class="inputContainer">
        <button type="submit" class="btn" @click="handelRegist">
          <label for="btn" class="btndata">가입하기</label>
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.regist {
  position: absolute;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.description {
  font-size: 70px;
  margin-top: 125px;
  margin-bottom: 20px;
}

.registform {
  width: 40%;
  height: 60%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}

.inputContainer {
  width: 43%;
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.emailContainer {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.registdata {
  width: 100%;
  height: 30%;
  text-align: start;
  color: #808080;
  font-size: 20px;
  margin-bottom: 1px;
}

.input {
  width: 100%;
  height: 35%;
  border: 1px solid #808080;
  border-radius: 10px;
}

.input-email {
  width: 100%;
  height: 50%;
  border-radius: 5px;
}

.btn {
  width: 100%;
  height: 63%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
}

.btndata {
  color: #ffffff;
  font-size: 23px;
}

.mt-1 {
  margin-top: 1%;
}
</style>
