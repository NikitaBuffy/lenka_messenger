<script setup lang="ts">
import loginImg from "@src/assets/images/login.png";
import background from "@src/assets/images/misc-mask-light.png";
</script>

<template>
  <div class="py-[2.125rem] px-[3.125rem] h-full basis-2/3 md:flex xs:hidden items-center justify-center">
    <div class="w-full h-full px-7 flex items-center bg-no-repeat bg-center bg-cover rounded-[.75rem] justify-center relative"
        :style="{ 'background-color': 'rgb(248, 247, 250, 1)' }">
        <div class="w-[55%] h-[70%] flex items-center bg-no-repeat bg-center bg-cover"
             :style="{ 'background-image': `url(${loginImg})` }">
        </div>
        <div class="absolute inset-0 flex justify-end items-end pointer-events-none"
             :style="{ 'background-image': `url(${background})`, 'background-size': 'contain', 'background-repeat': 'no-repeat',
             'background-position': 'bottom' }">
        </div>
    </div>
  </div>
</template>
