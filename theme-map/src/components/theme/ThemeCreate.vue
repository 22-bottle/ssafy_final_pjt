<script setup>
import { ref } from 'vue';
import { useRouter } from "vue-router";
import { createTheme } from '@/api/theme';
import { useEditorStore } from '@/stores/editor';
import { jwtDecode } from 'jwt-decode';

const router = useRouter();

const editorStore = useEditorStore();

const { cEditorInfo } = editorStore;

const token = sessionStorage.getItem('accessToken');
const id = token === null ? cEditorInfo.value.id : jwtDecode(token).id;

const theme = ref({
    themeName: "",
    description: "",
    editorId: id,
    type: "",
    visible: "",
});

const onThemeCreate = () => {
    createTheme(
        theme.value,
        () => {
            router.push({ name: 'detail' });
        },
        (error) => {
            console.log(error);
        }
    );
};
</script>

<template>
    <div id="container">
        <div id="header">
            <div class="title">새 테마 등록하기</div>
        </div>
        <form action="">
            <label for="themeName">테마 이름 : </label>
            <input type="text" id="themeName" name="themeName" :themeName="theme.themeName"><br />
            <label for="description">테마 설명 : </label>
            <input type="text" id="description" name="description" :descriptio="theme.description"><br />
            테마 유형 : 
            <input type="radio" id="public" name="type" value="1" :themeType="theme.type">
            <label for="public">Public</label>
            <input type="radio" id="private" name="type" value="0" :themeType="theme.type">
            <label for="private">Private</label><br />
            공개 여부 : 
            <input type="radio" id="visible" name="visible" value="1" :visible="theme.visible">
            <label for="visible">공개</label>
            <input type="radio" id="invisible" name="visible" value="0" :visible="theme.visible">
            <label for="invisible">비공개</label><br />
            <button @click="onThemeCreate">등록하기</button>
        </form>
    </div>
</template>

<style scoped>
#container {
    margin-top: 3%;
    padding: 8%;
}
#header {
    font-size: 40px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
</style>