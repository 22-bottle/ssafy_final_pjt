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

const cnt = ref(0);
const selectedTags = ref({ 0: { tagId: '0', tagName: 'none', selected: false } });
const onTagClick = (event) => {
  const index = event.target.id - 1;
  const tag = tags.value[index];

  tag.selected = !tag.selected;
  if (tag.selected) {
    selectedTags.value[tag.tagId] = tag;
    allShow.value = false;
    cnt.value = cnt.value + 1;
  } else {
    delete selectedTags.value[tag.tagId];
    cnt.value = cnt.value - 1;
    if (cnt.value == 0) {
      allShow.value = true;
    }
  }
};
const allShow = ref(true);
const onAllTag = () => {
  tags.value.forEach((tag) => {
    tag.selected = false;
    delete selectedTags.value[tag.tagId];
  });
  allShow.value = true;
};
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">테마 지도 찾아보기</div>
      <template v-if="isLogin">
        <button class="btn mt-1">
          <router-link class="btn-data" :to="{ name: 'create' }">+ 새 테마 등록하기</router-link>
        </button>
      </template>
    </div>
    <div id="hotThemes" class="mt-3">
      <div class="theme-section">🔥 인기 테마 Top 10</div>
      <div class="list">
        <theme-list type="hot"></theme-list>
      </div>
    </div>
    <div id="allThemes" class="mt-3">
      <div class="theme-section">🌏 전체 테마</div>
      <div id="tags">
        <button @click="onAllTag" :class="{ unselected: !allShow, selected: allShow }">전체</button>
        <tag-item
          v-for="(tag, index) in tags"
          :key="tag.tagId"
          :tag="tag"
          :class="{ unselected: !tag.selected, selected: tag.selected }"
          @click="onTagClick"
          :id="tag.tagId"
        ></tag-item>
      </div>
      <div class="list">
        <theme-list type="all" :tags="selectedTags"></theme-list>
      </div>
    </div>
  </div>
</template>

<style scoped>
#container {
  margin-top: 3%;
  padding: 8%;
}
#header {
  width: 100%;
  font-size: 40px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
#tags {
  margin-top: 2%;
  margin-left: 2%;
  display: flex;
  flex-direction: row;
}
.unselected {
  margin-right: 1%;
  width: 8%;
  font-size: 20px;
  color: #016ef5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  border: 3px solid #016ef5;
  border-radius: 30px;
  cursor: pointer;
}
.selected {
  margin-right: 1%;
  width: 8%;
  font-size: 20px;
  color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #016ef5;
  border: 0px;
  border-radius: 30px;
  cursor: pointer;
}
.list {
  width: 100%;
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
.mt-3 {
  margin-top: 3%;
}
.mt-5 {
  margin-top: 5%;
}
</style>
