<script setup>
import { ref, onMounted } from 'vue';
import ThemeItem from '@/components/theme/ThemeItem.vue';

const props = defineProps({ themes: Array });
const themes = ref([]);

</script>

<template>
  <div id="list">
    <theme-item v-for="(theme, index) in props.themes" :key="theme.themeId" :theme="theme"></theme-item>
  </div>
</template>

<style scoped>
#list {
  display: flex;
  flex-direction: row;
  text-decoration-line: none;
}
</style>