import { createRouter, createWebHistory } from 'vue-router';
import TheMapView from '@/views/TheMapView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: TheMapView,
    },
    {
      path: '/theme',
      name: 'theme',
      component: TheMapView,
    },
    {
      path: '/editor',
      name: 'editor',
      component: TheMapView,
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
