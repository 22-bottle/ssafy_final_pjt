import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "@/views/TheMainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/editor",
      name: "editor",
      component: () => import("@/views/TheEditorView.vue"),
      children: [
        {
          path: "login",
          name: "editor-login",
          component: () => import("@/components/editor/EditorLogin.vue"),
        },
        {
          path: "join",
          name: "editor-join",
          component: () => import("@/components/editor/EditorRegister.vue"),
        },
        {
          path: "mypage",
          name: "editor-mypage",
          component: () => import("@/components/editor/EditorMyPage.vue"),
        },
        // {
        //   path: "modify/:id",
        //   name: "editor-modify",
        //   component: () => import("@/components/editor/EditorModify.vue"),
        // },
      ],
    },
  ]
});

export default router;
