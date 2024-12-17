<script setup lang="ts">
import { ref } from "vue";

import Button from "@src/components/ui/inputs/Button.vue";
import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";

const oldPassword = ref("");
const newPassword = ref("");
const confirmNewPassword = ref("");
</script>

<template>
  <div class="w-full p-5 flex flex-col justify-center items-center">
    <div class="mb-6 w-[21.875rem]">
      <!--header-->
      <div class="mb-6 flex flex-col">
        <img
          src="../../../../assets/images/logo/white-logo.png"
          class="w-[1.775rem] h-[1.725rem] mb-4"
          alt="lenka messenger logo"
        />
        <p class="heading-2 text-color mb-4">Сброс пароля</p>
        <p class="body-3 text-color text-opacity-75 font-light">
          Введите ваш старый и новый пароль снизу для сброса
        </p>
      </div>

      <!--form-->
      <div class="mb-6">
        <PasswordInput
          :value="oldPassword"
          @valueChanged="
            (value) => {
              oldPassword = value;
            }
          "
          bordered
          label="Старый пароль"
          placeholder="Введите ваш старый пароль"
          class="mb-5"
        />

        <PasswordInput
          :value="newPassword"
          @valueChanged="
            (value) => {
              newPassword = value;
            }
          "
          bordered
          label="Новый пароль"
          placeholder="Придумайте новый пароль"
          class="mb-5"
        />

        <PasswordInput
          :value="confirmNewPassword"
          @valueChanged="
            (value) => {
              confirmNewPassword = value;
            }
          "
          bordered
          label="Подтверждение пароля"
          placeholder="Введите ваш новый пароль еще раз"
        />
      </div>

      <!--controls-->
      <div>
        <Button class="contained-primary contained-text w-full" link to="/">
          Сбросить пароль
        </Button>
      </div>
    </div>
  </div>
</template>
