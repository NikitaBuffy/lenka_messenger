<script setup lang="ts">
import {useRoute} from "vue-router";
import { computed } from "vue";

import LoginForm from "@src/components/views/authentication/login/LoginForm.vue";
import RegisterForm from "@src/components/views/authentication/register/RegisterForm.vue";
import Cover from "@src/components/views/authentication/Cover.vue";
import FadeTransition from "@src/components/ui/transitions/FadeTransition.vue";

const route = useRoute();

const activeMethod = computed((): any => {
  if (route.meta.method === "register") {
    return RegisterForm;
  } else if (route.meta.method === "login") {
    return LoginForm;
  }
});
</script>

<template>
  <div class="w-full h-full">
    <div class="w-full h-full flex dark:bg-gray-800">
      <Cover />
      <FadeTransition>
        <component :is="activeMethod" />
      </FadeTransition>
    </div>
  </div>
</template>
