<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useEditorStore } from '@/stores/editor';

const router = useRouter();
const editorStore = useEditorStore();

const { cEditorDto, editorModify } = editorStore;
// 사용자 정보 수정을 위한 객체
const editorUpdate = ref({
  id: '',
  pw: '',
  emailId: '',
  emailDomain: '',
  editorName: ''
});

onMounted(() =>{
    initialize();
});

const initialize = () => {
    editorUpdate.value.id = cEditorDto.value.id;
    editorUpdate.value.emailId = cEditorDto.value.emailId;
    editorUpdate.value.emailDomain = cEditorDto.value.emailDomain;
    editorUpdate.value.editorName = cEditorDto.value.editorName;
}

const handleUpdate = async (event) => {
  event.preventDefault();
  console.log('Update Editor Info:', editorUpdate.value);
  event.preventDefault();
  await editorModify(editorUpdate.value);

  window.alert('정보가 업데이트되었습니다.');
  router.push('/mypage');
};
</script>

<template>
    <div class="update-form">
      <span class="description">회원 정보 수정</span>
      <form class="form-container">
        <div class="input-container">
          <label for="editorName" class="label">이름</label>
          <input type="text" id="editorName" class="input" v-model="editorUpdate.editorName" />
        </div>
        <div class="input-container">
          <label for="emailId" class="label">이메일 ID</label>
          <input type="text" id="emailId" class="input" v-model="editorUpdate.emailId" />
        </div>
        <div class="input-container">
          <label for="emailDomain" class="label">이메일 도메인</label>
          <input type="text" id="emailDomain" class="input" v-model="editorUpdate.emailDomain" />
        </div>
        <div class="input-container">
          <label for="pw" class="label">비밀번호</label>
          <input type="password" id="pw" class="input" v-model="editorUpdate.pw" />
        </div>
        <div class="input-container">
          <button type="submit" class="btn" @click="handleUpdate">
            <span class="btn-text">수정하기</span>
          </button>
        </div>
      </form>
    </div>
  </template>  

<style scoped>
.update-form {
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

.form-container {
  width: 40%;
  height: 50%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
.form-container div {
  margin-bottom: 15px;
}

.input-container {
  width: 43%;
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.label {
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

.btn {
  width: 100%;
  height: 63%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
}

.btn-text {
  color: #ffffff;
  font-size: 23px;
}
</style>
