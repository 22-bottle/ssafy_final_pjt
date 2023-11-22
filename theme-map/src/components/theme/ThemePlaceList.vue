<script setup>
import { ref, onMounted, inject } from 'vue';
import { storeToRefs } from 'pinia';
import { useEditorStore } from '@/stores/editor';
import { useRoute, useRouter } from 'vue-router';
import { themePlace, getSpareNum } from '@/api/place';
import { curTheme, didLike, postLike, disLike } from '@/api/theme';
import PlaceItem from '@/components/map/PlaceItem.vue';
import PlaceDetail from '@/components/map/PlaceDetail.vue';

const editorStore = useEditorStore();
const { isLogin } = storeToRefs(editorStore);
const { cEditorDto } = editorStore;
const router = useRouter();
const route = useRoute();

const themePlaces = ref([]);
const theme = ref({
  themeId: route.params.themeId,
  themeName: '',
  description: '',
  editorId: '',
  type: '',
  visible: '',
  likeSum: '',
});

onMounted(() => {
  getTheme();
  getThemePlace();
  getDidLike();
});

const spareNum = ref(0);
const getTheme = () => {
  curTheme(
    route.params.themeId,
    ({ data }) => {
      theme.value = data;
      getSpareNum(
        theme.value.themeId,
        cEditorDto.value.editorId,
        ({ data }) => {
          spareNum.value = Number(data);
        }, 
        (error) => {
          console.log(error)
        }
      )
      checkCanAdd();
    },
    (error) => {
      console.log(error);
    }
  );
};

const getThemePlace = () => {
  themePlace(
    route.params.themeId,
    ({ data }) => {
      themePlaces.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const didILiked = ref(false);
const getDidLike = () => {
  didLike(
    cEditorDto.value.editorId,
    route.params.themeId,
    ({ data }) => {
      didILiked.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const likeDto = ref({
  editorId: cEditorDto.value.editorId,
  themeId: route.params.themeId,
});
const like = () => {
  postLike(
    likeDto.value,
    () => {
      didILiked.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
};
const dislike = () => {
  disLike(
    likeDto.value,
    () => {
      didILiked.value = false;
    },
    (error) => {
      console.log(error);
    }
  );
};
/* =============> */
const emit = defineEmits(['clickPlace']);

const visibility = ref(false);
const placeToView = ref(null);

const handleDetail = (place) => {
  if (placeToView.value === place) {
    visibility.value = !visibility.value;
  } else {
    visibility.value = true;
    placeToView.value = place;
  }
  console.log(visibility.value, clicked.value);
  emit('clickPlace', place);
};

const clicked = inject('clicked');

const goBack = () => {
  router.go(-1);
};

const handleDelete = () => {
  themePlaces.value = [];
  getThemePlace();
};
const updateScore = () => {
  themePlaces.value = [];
  getThemePlace();
};

const canAdd = ref(false);
const checkCanAdd = () => {
  if (theme.value.type == 0 && theme.value.editorId == cEditorDto.value.editorId) {
    canAdd.value = true;
  } else if (theme.value.type == 1 && theme.value.editorId == cEditorDto.value.editorId) {
    if (spareNum.value < 10) {
      canAdd.value = true;
    }
  } else if (theme.value.type == 1 && theme.value.editorId != cEditorDto.value.editorId) {
    if (spareNum < 1) {
      canAdd.value = true;
    }
  }
  console.log(canAdd.value)
}
/* <============= */
</script>

<template>
  <div>
    <!-- 리스트 -->
    <div class="list">
      <button id="goBackBtn" @click="goBack"></button>
      <button
        id="fullHeart"
        v-if="didILiked && isLogin"
        @click="dislike"
        :class="{ beforeMoving: !visibility || clicked, afterMoving: visibility && !clicked }"
      ></button>
      <button
        id="emptyHeart"
        v-if="!didILiked && isLogin"
        @click="like"
        :class="{ beforeMoving: !visibility || clicked, afterMoving: visibility && !clicked }"
      ></button>
      <router-link :to="{ name: 'keyword', params: { themeId: theme.themeId } }" v-if="canAdd">
        <button
          id="addBtn"
          :class="{ beforeMoving: !visibility || clicked, afterMoving: visibility && !clicked }"
        ></button>
      </router-link>
      <router-link :to="{ name: 'themeModify' }" :theme="theme">
        <button
          id="editBtn"
          v-if="theme.editorId == cEditorDto.editorId"
          :class="{ beforeMoving: !visibility || clicked, afterMoving: visibility && !clicked, canAdd: canAdd, cantAdd: !canAdd }"
        ></button>
      </router-link>
      <div class="name">{{ theme.themeName }}</div>
      <div class="items">
        <div style="color: white" class="mb15">{{ theme.description }}</div>
        <div class="items scrollbar">
          <place-item
            v-for="(place, index) in themePlaces"
            :key="index"
            :place="place"
            @detail="handleDetail"
            @delete="handleDelete"
          ></place-item>
        </div>
        <template v-if="isLogin && (theme.type == 1 || theme.editorId === cEditorDto.editorId)"> </template>
        <template v-if="visibility && !clicked">
          <place-detail :place="placeToView" @updateScore="updateScore"></place-detail>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>
.list {
  position: absolute;
  top: 20%;
  left: 2%;
  z-index: 10;
  width: 21%;
  height: 73%;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}

.name {
  position: relative;
  width: 90%;
  height: 10%;
  background-color: #016ef5;
  color: #ffffff;
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}

.items {
  position: relative;
  width: 90%;
  height: 84%;
  background-color: #016ef5;
  color: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}
.scrollbar {
  overflow: auto;
}
.beforeMoving {
  left: 23.3%;
}
.afterMoving {
  left: 43.3%;
  z-index: 20;
}
#goBackBtn {
  position: fixed;
  top: 14%;
  left: 2%;
  width: 50px;
  height: 50px;
  background-color: transparent;
  background-image: url(../../assets/img/goback.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
}
#fullHeart {
  position: fixed;
  top: 23%;
  width: 60px;
  height: 55px;
  background-color: white;
  background-image: url(../../assets/img/fullHeart.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
  border-radius: 8px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
#emptyHeart {
  position: fixed;
  top: 23%;
  width: 60px;
  height: 55px;
  background-color: white;
  background-image: url(../../assets/img/emptyHeart.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
  border-radius: 8px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
.canAdd {
  top: 37%;
}
.cantAdd {
  top: 30%;
}
#editBtn {
  position: fixed;
  width: 60px;
  height: 55px;
  background-color: white;
  background-image: url(../../assets/img/pencil.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
  border-radius: 8px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
#addBtn {
  position: fixed;
  top: 30%;
  width: 60px;
  height: 55px;
  background-color: white;
  background-image: url(../../assets/img/add.png);
  background-repeat: no-repeat;
  background-position: center center;
  cursor: pointer;
  border: none;
  border-radius: 8px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.25);
}
</style>
