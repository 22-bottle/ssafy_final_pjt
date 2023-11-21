<script setup>
import { ref, onMounted } from 'vue';
import { themesOfEditor, themesOfLike, visibleThemesOfEditor } from '@/api/theme';
import ThemeItem from '@/components/theme/ThemeItem.vue';
import { useRoute } from 'vue-router';

const props = defineProps({ editorId: String, type: String });
const themes = ref([]);

const route = useRoute();

onMounted(() => {
  if (props.type === 'all') {
    getThemesOfEditor();
  } else {
    getThemesOfLike();
  }
});

const getThemesOfEditor = () => {
  if (route.name === 'mypage') {
    themesOfEditor(
      props.editorId,
      ({ data }) => {
        themes.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    visibleThemesOfEditor(
      props.editorId,
      ({ data }) => {
        themes.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const getThemesOfLike = () => {
  themesOfLike(
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
