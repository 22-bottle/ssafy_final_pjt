<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useEditorStore } from '@/stores/editor';

const router = useRouter();
const editorStore = useEditorStore();

const { isLogin } = storeToRefs(editorStore);
const { editorLogin, cCurEditorDto } = editorStore;

const setEditor = ref({
  id: '',
  pw: '',
  setToken: false,
});

onMounted(() => {
  initialize();
});

const initialize = () => {
  setEditor.value = cCurEditorDto.value;
  console.log(setEditor.value);
};

const loginEditor = ref({
  id: '',
  pw: '',
  setToken: false,
});

onMounted(() => {
  console.log(cCurEditorDto.value);
  console.log(cCurEditorDto.value == null, cCurEditorDto.value != null);
  if (cCurEditorDto.value != null) {
    setInfo();
  }
});

const setInfo = () => {
  loginEditor.value.id = cCurEditorDto.value.id;
  loginEditor.value.setToken = true;
};

const handleLogin = async (event) => {
  console.log('Enter handleLogin method');
  event.preventDefault();
  await editorLogin(loginEditor.value);

  if (isLogin.value) {
    window.alert('로그인 성공!!');
    router.push('/');
  } else {
    window.alert('로그인 실패..');
    router.go(0);
  }
};

const handleSingIn = async () => {
  console.log('Enter handleSingIn method');
  router.push('regist');
};
</script>

<template>
  <div class="login">
    <span class="description title">로그인</span>
    <form class="loginform">
      <div class="inputContainer">
        <label for="id" class="logindata">아이디</label>
        <input type="text" id="id" class="input" v-model="loginEditor.id" />
      </div>
      <div class="inputContainer mt-3">
        <label for="pw" class="logindata">비밀번호</label>
        <input type="password" id="pw" class="input" v-model="loginEditor.pw" />
      </div>
      <!-- <div class="baseContainer mt-1">
        <label for="find" class="find">비밀번호 찾기</label>
      </div> -->
      <!-- <div class="baseContainer mt-3">
        <input type="checkbox" id="setToken" class="checkbox" v-model="loginEditor.setToken" />
        <label for="find" class="checkboxdata">로그인 유지하기</label>
      </div> -->
      <div class="inputContainer mt-1">
        <button type="submit" class="btn" @click="handleLogin">
          <label for="btn" class="btndata">로그인 ></label>
        </button>
      </div>
      <div class="inputContainer">
        <label for="btn" class="registdata" @click="handleSingIn">회원가입</label>
      </div>
    </form>
  </div>
</template>

<style scoped>
.login {
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
  margin-bottom: 20px;
}

.loginform {
  padding-top: 30px;
  width: 40%;
  height: 43%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}

.baseContainer {
  width: 43%;
  height: 5%;
  display: flex;
}

.inputContainer {
  width: 43%;
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.logindata {
  width: 100%;
  height: 38%;
  text-align: start;
  color: #808080;
  font-size: 20px;
  margin-bottom: 1px;
}

.input {
  width: 100%;
  height: 62%;
  border: 1px solid #808080;
  border-radius: 10px;
}

.find {
  display: flex;
  justify-content: end;
  width: 100%;
  height: 100%;
  color: #808080;
  font-size: 15px;
}

.checkbox {
  width: 10%;
  height: 65%;
  justify-content: start;
}

.checkboxdata {
  justify-content: start;
  color: #000000;
  font-size: 18px;
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
  cursor: pointer;
  height: 54px;
}

.registdata {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  width: 100%;
  height: 100%;
  font-size: 23px;
  cursor: pointer;
}

.mt-1 {
  margin-top: 1%;
}

.mt-3 {
  margin-top: 3%;
}
</style>
