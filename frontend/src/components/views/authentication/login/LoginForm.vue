<script setup lang="ts">
import { ref } from "vue";

import Button from "@src/components/ui/inputs/Button.vue";
import LabeledTextInput from "@src/components/ui/inputs/LabeledTextInput.vue";
import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";

const password = ref("");
</script>

<template>
  <div class="p-5 md:basis-1/3 xs:basis-full flex flex-col justify-center items-center">
    <div class="w-full md:px-[10%] xs:px-[10%]">
      <!--header-->
      <div class="mb-7 flex flex-col">
        <img
          src="../../../../assets/images/logo/white-logo.png"
          class="w-[1.775rem] h-[1.725rem] mb-4"
          alt="lenka messenger logo"
        />
        <p class="heading-2 text-color mb-4">С возвращением!</p>
        <p class="body-3 text-color text-opacity-75 font-light">
          Войдите в свой аккаунт и начните общаться прямо сейчас
        </p>
      </div>

      <!--form-->
      <div class="mb-6">
        <LabeledTextInput label="Логин" placeholder="Введите свой логин" class="mb-5"/>
        <PasswordInput
          @value-changed="
            (value) => {
              password = value;
            }
          "
          :value="password"
          label="Пароль"
          placeholder="Введите свой пароль"
        />
      </div>

      <!--auth button-->
      <div class="mb-6">
        <Button class="contained-primary contained-text w-full mb-4" link to="/">Войти</Button>
      </div>

      <!--register-->
      <div>
        <div class="flex justify-center">
          <p class="body-2 text-color">
            Еще нет аккаунта?
            <router-link
              to="/auth/register/"
              class="text-indigo-400 opacity-100">
              Зарегистрироваться
            </router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
