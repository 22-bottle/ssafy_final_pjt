<script setup>
import { ref, onMounted } from 'vue';
import { allTags } from '@/api/theme';

import ThemeList from "../components/theme/ThemeList.vue";
import TagItem from "../components/theme/TagItem.vue";

const tags = ref([]);

onMounted(() => {
    getTags();
})

const getTags = () => {
    allTags(
        ({ data }) => {
            tags.value = data;
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
            <button>
                <router-link :to="{ name: 'themeCreate' }">새 테마 등록하기</router-link>
            </button>
        </div>
        <div id="hotThemes">
            <div>인기 테마 Top 5</div>
            <theme-list type="hot"></theme-list>
        </div>
        <div id="allThemes">
            <div>전체 테마</div>
            <div id="tags">
                <tag-item v-for="(tag, index) in tags" :key="tag.tagId" :tag="tag"></tag-item>
            </div>
            <theme-list type="all"></theme-list>
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
#tags {
    display: flex;
    flex-direction: row;
}
</style>