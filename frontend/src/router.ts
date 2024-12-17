import { createRouter, createWebHistory } from "vue-router";
import AuthView from "@src/components/views/authentication/AuthView.vue";
import HomeView from "@src/components/views/main/HomeView.vue";
import PasswordResetView from "@src/components/views/authentication/reset/PasswordResetView.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
    meta: { requiresAuth: true }
  },
  {
    path: "/auth/login",
    name: "Login",
    component: AuthView,
    meta: { method: "login" }
  },
  {
    path: "/auth/register",
    name: "Register",
    component: AuthView,
    meta: { method: "register" }
  },
  {
    path: "/auth/reset",
    name: "ResetPassword",
    component: PasswordResetView,
    meta: { method: "reset"}
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("authToken");

  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: "Login" });
  } else next();
});

export default router;
