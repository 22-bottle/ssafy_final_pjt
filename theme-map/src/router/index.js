  import { createRouter, createWebHistory } from 'vue-router';
  import TheMapView from '@/views/TheMapView.vue';
  import EditorLogin from '@/components/editor/EditorLogin.vue';
  import EditorRegist from '@/components/editor/EditorRegist.vue';
  import EditorMyPage from '@/components/editor/EditorMyPage.vue';
  import TheEditorMain from '@/views/TheEditorMain.vue';
  import TheThemeMain from '@/views/TheThemeMain.vue';
  import ThemeCreate from '@/components/theme/ThemeCreate.vue';
  import TheThemeView from '@/views/TheThemeView.vue';

  const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
      {
        path: '/',
        name: 'home',
        component: TheMapView,
        redirect: '/place',
        children: [
          {
            path: 'place',
            name: 'place',
            component: () => import('@/components/map/PlaceList.vue'),
          },
        ],
      },
      {
        path: '/theme',
        name: 'theme',
        component: TheThemeView,
        redirect: '/theme/main',
        children: [
          {
            path: 'main',
            name: 'main',
            component: TheThemeMain,
          },
          {
            path: 'detail/:themeId',
            name: 'detail',
            component: () => import('@/views/TheMapView.vue'),
          },
          {
            path: 'create',
            name: 'create',
            component: ThemeCreate,
          },
          {
            path: 'keyword/:themeId',
            name: 'keyword',
            component: () => import('@/components/map/KeywordPlace.vue'),
          },
        ],
      },
      {
        path: '/editor',
        name: 'editor',
        component: TheEditorMain,
      },
      {
        path: '/login',
        name: 'login',
        component: EditorLogin,
      },
      {
        path: '/regist',
        name: 'regist',
        component: EditorRegist,
      },
      {
        path: '/mypage',
        name: 'mypage',
        component: EditorMyPage,
      },
    ],
  });

  export default router;
