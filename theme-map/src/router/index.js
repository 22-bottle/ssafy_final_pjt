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
  ],
});

export default router;
