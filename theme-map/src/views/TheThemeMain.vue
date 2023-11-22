<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { allTags } from '@/api/theme';
import { useEditorStore } from '@/stores/editor';

import ThemeList from '@/components/theme/ThemeList.vue';
import TagItem from '@/components/theme/TagItem.vue';

const tags = ref([]);
const editorStore = useEditorStore();

const { isLogin } = storeToRefs(editorStore);

onMounted(() => {
  getTags();
});

const getTags = () => {
  allTags(
    ({ data }) => {
      tags.value = data;
      tags.value.forEach((tag) => {
        tag.selected = false;
      });
    },
    (error) => {
      console.log(error);
    }
  );
};

const selectedTags = ref({ 0: { tagId: '0', tagName: 'none', selected: false } });
const onTagClick = (event) => {
  const index = event.target.id - 1;
  const tag = tags.value[index];

  tag.selected = !tag.selected;
  if (tag.selected) {
    selectedTags.value[tag.tagId] = tag;
  } else {
    delete selectedTags.value[tag.tagId];
  }
};
const onAllTag = () => {
  tags.value.forEach((tag) => {
    tag.selected = false;
    delete selectedTags.value[tag.tagId];
  });
};
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">테마로 보는 대전</div>
      <template v-if="isLogin">
        <button class="btn mt-1">
          <router-link class="btn-data" :to="{ name: 'create' }">+ 새 테마 등록하기</router-link>
        </button>
      </template>
    </div>
    <div id="hotThemes" class="mt-5">
      <div class="theme-section">🔥 인기 테마 Top 10</div>
      <div class="list">
        <theme-list type="hot"></theme-list>
      </div>
    </div>
    <div id="allThemes" class="mt-5">
      <div class="theme-section">🌏 전체 테마</div>
      <div id="tags">
        <button id="all" @click="onAllTag">전체</button>
        <div id="tag" v-for="(tag, index) in tags" :key="tag.tagId">
          <tag-item
            :tag="tag"
            :class="{ selected: tag.selected }"
            @click="onTagClick"
            :id="tag.tagId"
          ></tag-item>
        </div>
        <button id="etc">더보기</button>
      </div>
      <div class="list">
        <theme-list type="all" :tags="selectedTags"></theme-list>
      </div>
    </div>
  </div>
</template>

<style scoped>
#container {
  position: absolute;
  width: 100%; height: 100%;
  background-color: #f5fffa;
  margin-top: 3%;
  padding: 8%;
}
#header {
  width: 80%;
  font-size: 40px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
#tags {
  margin-top: 1%;
  margin-left: 0.5%;
  display: flex;
  flex-direction: row;
}
#tag {
  margin-right: 1%;
  width: 8%;
  font-size: 30px;
  color: #016ef5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #FFFFFF;
  border: 3px solid #016ef5;
  border-radius: 30px;
}
#all {
  margin-right: 1%;
  width: 8%;
  font-size: 30px;
  color: #FFFFFF; 
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #016ef5;
  border: 0px;
  border-radius: 30px;
}
#etc {
  margin-right: 1%;
  width: 6.5%;
  font-size: 25px;
  color: #016ef5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #80b5f7;
  border: 1px;
  border-radius: 30px;
}
#hotThemes {
  width: 80%; height: 50%;
}
#allThemes {
  width: 80%; height: 50%;
}
.list {
  width: 100%; height: 40vh;
}
.selected {
  color: red;
}
.title {
  font-size: 60px;
}
.theme-section {
  font-size: 45px;
  color: black;
}
.btn {
  width: 15%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 30px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
}
.btn-data {
  color: #ffffff;
  font-size: 23px;
  text-decoration-line: none;
}
.mt-1 {
  margin-top: 1%;
}
.mt-5 {
  margin-top: 5%;
}
</style>
