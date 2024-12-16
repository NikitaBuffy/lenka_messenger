<script setup lang="ts">
import FadeTransition from "@src/components/ui/transitions/FadeTransition.vue";
</script>

<template>
  <KeepAlive>
    <div class="xs:relative md:static h-full flex xs:flex-col md:flex-row overflow-hidden">
      <FadeTransition>
        Welcome to Main page
      </FadeTransition>
    </div>
  </KeepAlive>
</template>
