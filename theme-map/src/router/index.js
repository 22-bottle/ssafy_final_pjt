import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '@/views/TheMainView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: TheMainView,
    },
  ],
});

export default router;
