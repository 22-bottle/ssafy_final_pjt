<script setup>
import { ref, onMounted } from 'vue';
import { hotTheme, allTheme } from '@/api/theme';
import ThemeItem from './ThemeItem.vue';

const props = defineProps({ type: String, tags: Array });

// const selectedTags = Object.values(props.tags);
const themes = ref([]);

onMounted(() => {
  if (props.type === 'all') {
    getAllThemes();
  } else {
    getHotThemes();
  }
});

const getHotThemes = () => {
  hotTheme(
    ({ data }) => {
      themes.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getAllThemes = () => {
  allTheme(
    ({ data }) => {
      themes.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div id="list">
    <theme-item v-for="(theme, index) in themes" :key="theme.themeId" :theme="theme"></theme-item>
  </div>
</template>

<style scoped>
#list {
  display: flex;
  flex-direction: row;
}
</style>
