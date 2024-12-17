<script setup lang="ts">
import { computed, ref } from "vue";

import SlideTransition from "@src/components/ui/transitions/SlideTransition.vue";
import PasswordSection from "@src/components/views/authentication/register/PasswordSection.vue";
import PersonalSection from "@src/components/views/authentication/register/PersonalSection.vue";
import { RouterLink } from "vue-router";

defineEmits(["activeSectionChange"]);

const activeSectionName = ref("personal-section");
const animation = ref("slide-left");

const regData = ref({
  email: "",
  username: "",
  password: "",
  firstName: "",
  lastName: "",
});

const ActiveSection = computed((): any => {
  if (activeSectionName.value === "personal-section") {
    return PersonalSection;
  } else if (activeSectionName.value === "password-section") {
    return PasswordSection;
  }
});

const changeActiveSection = (event: {
  sectionName: string;
  animationName: string;
}) => {
  animation.value = event.animationName;
  activeSectionName.value = event.sectionName;
};
</script>

<template>
  <div class="p-5 md:basis-1/3 xs:basis-full flex flex-col justify-center items-center">
    <div class="w-full md:px-[10%] xs:px-[10%]">
      <!--header-->
      <div class="mb-8 flex flex-col">
        <img
          src="@src/assets/images/logo/white-logo.png"
          class="w-[1.775rem] h-[1.725rem] mb-4"
          alt="lenka messenger logo"
        />
        <p class="heading-2 text-color mb-4">Добро пожаловать в Lenka Messenger!</p>
        <p class="body-3 text-color text-opacity-75 font-light">
          Зарегистрируйтесь и начните общаться
        </p>
      </div>

      <!--form section-->
      <SlideTransition :animation="animation">
        <component
          @active-section-change="changeActiveSection"
          :is="ActiveSection"
          :reg-data="regData"
        />
      </SlideTransition>

      <!--bottom text-->
      <div class="flex justify-center">
        <p class="body-2 text-color">
          Уже есть аккаунт?
          <RouterLink to="/auth/login/" class="text-indigo-400 opacity-100">
            Войти
          </RouterLink>
        </p>
      </div>
    </div>
  </div>
</template>
