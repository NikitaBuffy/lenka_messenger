<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";

import useStore from "@src/store/store";
import { fetchData } from "@src/store/defaults";

import FadeTransition from "@src/components/ui/transitions/FadeTransition.vue";

const store = useStore();

store.$subscribe((_mutation, state) => {
  localStorage.setItem("chat", JSON.stringify(state));
});

// here we load the data from the server.
onMounted(async () => {
  store.status = "loading";

  // fake server call
  setTimeout(() => {
    store.delayLoading = false;
  });
  const request = await fetchData();

  store.$patch({
    status: "success",
    user: request.data.user,
    conversations: request.data.conversations,
    notifications: request.data.notifications,
    archivedConversations: request.data.archivedConversations,
  });
});

const height = ref(`${window.innerHeight}px`);

const resizeWindow = () => {
  height.value = `${window.innerHeight}px`;
};

onMounted(() => {
  window.addEventListener("resize", resizeWindow);
});

onUnmounted(() => {
  window.removeEventListener("resize", resizeWindow);
});
</script>

<template>
  <div :class="{ dark: store.settings.darkMode }">
    <div class="bg-white dark:bg-gray-800 transition-colors duration-500" :style="{ height: height }">
      <router-view v-slot="{ Component }">
        <FadeTransition>
          <component :is="Component" />
        </FadeTransition>
      </router-view>
    </div>
  </div>
</template>
