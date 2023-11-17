<script setup>
import { ref, onMounted } from 'vue';
import { useEditorStore } from "@/stores/editor";

const editorStore = useEditorStore();
const { getEditorInfo, editorModify, editorResign, cEditorInfo } = editorStore;

const editor = ref({});

onMounted(() => {
  initalize();
});

const initalize = async () => {
  editor.value = cEditorInfo.value;
}

const handleGetEditorInfo = async () => {
    console.log("getEditorInfo attempt");
    let token = sessionStorage.getItem("accessToken");
    await getEditorInfo(token);
    editor.value = (cEditorInfo.value)
};

const handleModify = async () => {
    console.log("handleModify attempt");
    await editorModify(editor.value);
};

const handleResign = async () => {
    console.log("handleResign attempt");
    await editorResign(editor.value.id);
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="text-center">마이페이지</h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-12">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  id: <input class="list-group-item" v-model="editor.id" disabled/>
                  pw: <input class="list-group-item" v-model="editor.pw" />
                  salt: <input class="list-group-item" v-model="editor.salt" disabled/>
                  emailId: <input class="list-group-item" v-model="editor.emailId" />
                  emailDomain: <input class="list-group-item" v-model="editor.emailDomain" />
                  editorName: <input class="list-group-item" v-model="editor.editorName" />
                  likeSum: <input class="list-group-item" v-model="editor.likeSum" disabled/>
                  joinDate: <input class="list-group-item" v-model="editor.joinDate" disabled/>
                  token: <input class="list-group-item" v-model="editor.token" disabled/>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="col-auto text-center mt-3">
          <button type="button" class="btn btn-outline-primary ms-1 mb-3" @click="handleModify">수정</button>
          <button type="button" class="btn btn-outline-success ms-1 mb-3"  @click="handleResign">탈퇴</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
