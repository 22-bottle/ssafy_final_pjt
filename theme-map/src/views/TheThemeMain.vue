<script setup>
import { ref, onMounted } from 'vue';
import { hotTheme, allTheme } from '@/api/theme';

import ThemeList from "../components/theme/ThemeList.vue";
import TagList from "../components/theme/TagList.vue";

const hotThemes = ref([]);
const allThemes = ref([]);
const tags = ref([]);

onMounted(() => {
    getHotThemes();
    getAllThemes();
});

const getHotThemes = () => {
    hotTheme(
        ({ data }) => {
            data = hotThemes;
        },
        (error) => {
            console.log(error);
        }
    );
};

const getAllThemes = () => {
    allTheme(
        tags,
        ({ data }) => {
            console.log(data);
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
            <div class="title">테마로 보는 대전</div>
            <button>새 테마 등록하기</button>
        </div>
        <div id="hotThemes">
            <div>인기 테마 Top 5</div>
            <theme-list></theme-list>
        </div>
        <div id="allThemes">
            <div>전체 테마</div>
            <tag-list></tag-list>
            <theme-list></theme-list>
        </div>
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