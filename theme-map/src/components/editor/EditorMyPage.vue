<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from "@/stores/editor";

const editorStore = useEditorStore();
const { editorInfo } = storeToRefs(editorStore);

const editor = ref({
  id: ""
});

onMounted(()=> {
  initialize();
});

function initialize() {
  editor.value = editorInfo.value;
}

</script>

<template>
  <div class="mypage">
    <div>
      <span class="editor-name">{{ editor.id }}</span>
    </div>
    <div>

    </div>
  </div>
</template>

<style scoped>
.mypage {
  position: absolute;
  top: 20%;
  left: 15%;
  width: 100%;
  height: 100%;
  background-color: #f5fffa;
}

.editor-name {
  font-size: 70px;
  margin-bottom: 20px;
}
</style>
