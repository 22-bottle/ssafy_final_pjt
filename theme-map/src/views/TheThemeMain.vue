<script setup>
import ThemeList from "../components/theme/ThemeList.vue";
import TagList from "../components/theme/TagList.vue";
</script>

<template>
    <div id="container">
        <div id="header">
            <div class="title">테마로 보는 대전</div>
            <button>새 테마 등록하기</button>
        </div>
        <div id="hotThemes">
            <div>인기 테마 Top 5</div>
            <theme-list type="hot"></theme-list>
        </div>
        <div id="allThemes">
            <div>전체 테마</div>
            <tag-list></tag-list>
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
</style>