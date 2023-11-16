<script setup>
import { jwtDecode } from "jwt-decode";
import { useEditorStore } from "@/stores/editor";

const editorStore = useEditorStore();

const { editorLogout } = editorStore;

const handleLogout = async () => {
    console.log("logout attempt");
    let token = sessionStorage.getItem("accessToken");
    let id = jwtDecode(token).id;
    await editorLogout(id);
};

</script>

<template>
  <div class="container row justify-content-center">
    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="orange">로그아웃</mark>

    </h2>
    <div class="col-auto text-center">
        <button type="button" class="btn btn-outline-primary mb-3" @click="handleLogout">
            로그아웃
        </button>
    </div>
  </div>
</template>

<style scoped>

</style>
