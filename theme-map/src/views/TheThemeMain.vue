<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { allTags } from '@/api/theme';
import { useEditorStore } from '@/stores/editor';

import ThemeList from '../components/theme/ThemeList.vue';
import TagItem from '../components/theme/TagItem.vue';

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
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">테마로 보는 대전</div>
      <template v-if="isLogin">
        <button>
          <router-link :to="{ name: 'create' }">새 테마 등록하기</router-link>
        </button>
      </template>
    </div>
    <div id="hotThemes">
      <div>인기 테마 Top 10</div>
      <theme-list type="hot"></theme-list>
    </div>
    <div id="allThemes">
      <div>전체 테마</div>
      <div id="tags">
        <tag-item
          v-for="(tag, index) in tags"
          :key="tag.tagId"
          :tag="tag"
          :class="{ selected: tag.selected }"
          @click="onTagClick"
          :id="tag.tagId"
        ></tag-item>
      </div>
      <theme-list type="all" :tags="selectedTags"></theme-list>
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
.selected {
  color: red;
}
</style>
