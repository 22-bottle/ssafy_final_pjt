<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { curTheme, updateTheme, allTags, tagsOfTheme, updateTag } from '@/api/theme';

import TagItem from '@/components/theme/TagItem.vue';

const route = useRoute();
const router = useRouter();

const theme = ref({
  themeId: route.params.themeId,
  themeName: '',
  description: '',
  editorId: '',
  type: '',
  visible: '',
  likeSum: '',
});

const tags = ref([]);
const selectedTags = ref({ 0: { tagId: '0', tagName: 'none', selected: false } });

onMounted(() => {
  getTheme();
  getTags();
  checkTags();
});

const getTheme = () => {
  curTheme(
    route.params.themeId,
    ({ data }) => {
      theme.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const isPublic = ref(true);

const checkPublic = () => {
  isPublic.value = true;
};
const checkPrivate = () => {
  isPublic.value = false;
};

const onThemeModify = (event) => {
  event.preventDefault();
  updateTheme(
    theme.value,
    () => {
      onUpdateTag(selectedTags.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

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

const checkTags = () => {
  tagsOfTheme(
    route.params.themeId,
    ({ data }) => {
      data.forEach((tag) => {
        const index = tag.tagId - 1;
        const curTag = tags.value[index];
        curTag.selected = true;
        selectedTags.value[tag.tagId] = tag;
      })
    },
    (error) => {
      console.log(error);
    }
  )
};

const tagListDto = ref({
  tags: [],
})
const onUpdateTag = (tags) => {
  tagListDto.value.tags = [];
  Object.values(tags).forEach((tag) => {
    if (tag.tagId != 0) {
      tagListDto.value.tags.push(tag);
    }
  });
  if (tagListDto.value.tags.length != 0) {
    updateTag(
      route.params.themeId,
      tagListDto.value,
      () => {
        router.replace({ name: 'detail', parmas: { themeId: theme.value.themeId } });
      },
      (error) => {
        console.log(error);
      }
    )
  } else {
    router.replace({ name: 'detail', parmas: { themeId: theme.value.themeId } });
  }
}
</script>

<template>
  <div id="container">
    <div id="header">
      <div class="title">테마 수정하기</div>
    </div>
    <form action="">
      <label for="themeName">테마 이름 : </label>
      <input type="text" id="themeName" name="themeName" v-model="theme.themeName" /><br />
      <label for="description">테마 설명 : </label>
      <input type="text" id="description" name="description" v-model="theme.description" /><br />
      테마 유형 :
      <input type="radio" id="public" name="type" value="1" v-model="theme.type" checked @click="checkPublic" />
      <label for="public">Public</label>
      <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
      <label for="private">Private</label><br />
      공개 여부 :
      <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" :checked="isPublic" />
      <label for="visible">공개</label>
      <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
      <label for="invisible">비공개</label><br />

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
      <button @click="onThemeModify">수정하기</button>
    </form>
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
