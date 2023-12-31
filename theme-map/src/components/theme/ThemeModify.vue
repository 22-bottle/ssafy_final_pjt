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
  if (theme.value.themeName.replace(/^\s+|\s+$/gm, '') === '') {
    window.alert('테마 이름을 입력해주세요!');
  } else {
    updateTheme(
      theme.value,
      () => {
        onUpdateTag(selectedTags.value);
      },
      (error) => {
        console.log(error);
      }
    );
  }
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
      });
    },
    (error) => {
      console.log(error);
    }
  );
};

const tagListDto = ref({
  tags: [],
});
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
    );
  } else {
    router.replace({ name: 'detail', parmas: { themeId: theme.value.themeId } });
  }
};
</script>

<template>
  <div id="container">
    <div class="title" id="header">테마 수정하기</div>
    <form class="inputform mt-1">
      <div class="inputContainer mt-3">
        <label for="themeName" class="section">테마 이름</label>
        <input type="text" id="themeName" name="themeName" class="input" v-model="theme.themeName" /><br />
      </div>
      <div class="inputContainer mt-3">
        <label for="description" class="section">테마 설명</label>
        <input type="text" id="description" name="description" class="input" v-model="theme.description" /><br />
      </div>
      <div class="inputContainer mt-3">
        <span class="section">테마 유형</span>
        <div class="radios">
          <div>
            <input type="radio" id="public" name="type" value="1" v-model="theme.type" checked @click="checkPublic" />
            <label for="public">Public</label>
          </div>
          <div>
            <input type="radio" id="private" name="type" value="0" v-model="theme.type" @click="checkPrivate" />
            <label for="private">Private</label><br />
          </div>
        </div>
      </div>
      <div class="inputContainer mt-3">
        <span class="section">공개 여부</span>
        <div class="radios">
          <div>
            <input type="radio" id="visible" name="visible" value="1" v-model="theme.visible" :checked="isPublic" />
            <label for="visible">공개</label>
          </div>
          <div>
            <input type="radio" id="invisible" name="visible" value="0" v-model="theme.visible" :disabled="isPublic" />
            <label for="invisible">비공개</label><br />
          </div>
        </div>
      </div>
      <div class="inputContainer mt-3" id="tags">
        <span class="section">태그 목록</span>
        <tag-item
          v-for="(tag, index) in tags"
          :key="tag.tagId"
          :tag="tag"
          :class="{ unselected: !tag.selected, selected: tag.selected }"
          @click="onTagClick"
          :id="tag.tagId"
          class="tagItem mt-3"
        ></tag-item>
      </div>
      <button class="btn mt-3" @click="onThemeModify">수정하기</button>
    </form>
  </div>
</template>

<style scoped>
#container {
  width: 100%;
  margin-top: 10%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
#header {
  font-size: 60px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 2%;
}
.title {
  font-size: 60px;
  margin-bottom: 20px;
}
.inputform {
  width: 40%;
  height: 60%;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
.inputContainer {
  width: 43%;
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.section {
  width: 100%;
  height: 30%;
  text-align: start;
  color: #808080;
  font-size: 20px;
  margin-bottom: 1px;
}
.input {
  width: 100%;
  height: 30%;
  font-size: 20px;
  border: 1px solid #808080;
  border-radius: 10px;
}
.btn {
  width: 43%;
  height: 50%;
  background-color: #016ef5;
  border: 0px;
  border-radius: 10px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.25);
  color: #ffffff;
  font-size: 23px;
  margin-bottom: 5%;
  cursor: pointer;
}
.radios label {
  color: #808080;
}
.radios {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  font-size: 18px;
  margin-top: 5%;
}
.mt-1 {
  margin-top: 1%;
}
.mt-3 {
  margin-top: 3%;
}
#tags {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: wrap;
  font-size: 18px;
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
.tagItem {
  font-size: 18px;
  width: 80px;
}
</style>
