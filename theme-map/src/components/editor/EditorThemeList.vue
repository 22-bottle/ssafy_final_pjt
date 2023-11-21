<script setup>
import { ref, onMounted } from 'vue';
import { themesOfEditor } from '@/api/theme';
import ThemeItem from '@/components/theme/ThemeItem.vue';

const props = defineProps({ editorId: String });
const themes = ref([]);

onMounted(() => {
  getThemesOfEditor();
});

const getThemesOfEditor = () => {
  themesOfEditor(
    props.editorId,
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
  text-decoration-line: none;
}
</style>
