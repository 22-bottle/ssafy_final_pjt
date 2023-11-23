<script setup>
import { ref, onMounted, watch } from 'vue';
import { hotTheme, allTheme, themesOfTag } from '@/api/theme';
import ThemeItem from './ThemeItem.vue';
import { objectToString } from '@vue/shared';

const props = defineProps({ type: String, tags: Object });

const themes = ref([]);

onMounted(() => {
  if (props.type === 'all') {
    getAllThemes();
    watch(props.tags, (newTags, oldTags) => {
      getThemesofTag(newTags);
    });
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

const tagListDto = ref({
  tags: [],
});

const getThemesofTag = (tags) => {
  tagListDto.value.tags = [];
  Object.values(tags).forEach((tag) => {
    if (tag.tagId != 0) {
      tagListDto.value.tags.push(tag);
    }
  });
  if (tagListDto.value.tags.length == 0) {
    getAllThemes();
  } else {
    themesOfTag(
      tagListDto.value,
      ({ data }) => {
        themes.value = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
};
</script>

<template>
  <div id="list">
    <template v-for="(theme, index) in themes" :key="theme.themeId">
      <div class="theme">
        <theme-item :theme="theme" class="theme"></theme-item>
      </div>
    </template>
  </div>
</template>

<style scoped>
#list {
  height: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
}

.theme {
  width: 20%;
  height: 100%;
  margin-top: 3%;
}
</style>
