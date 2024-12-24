<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
  import { sleep } from "@src/utils";

import AuthService from '@src/services/AuthService';
import Button from "@src/components/ui/inputs/Button.vue";
import LabeledTextInput from "@src/components/ui/inputs/LabeledTextInput.vue";
import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";

const router = useRouter();

const password = ref("");
const login = ref("");
const errorMessage = ref("");
const passwordError = ref(false);
const loginError = ref(false);
const isLoading = ref(false);

const handleLogin = async () => {
  errorMessage.value = "";

  if (!password.value || !login.value) {
    errorMessage.value = "Пожалуйста, заполните все поля";
    return;
  }

  if (password.value.length < 8) {
    errorMessage.value = "Размер пароля должен превышать 8 символов";
    passwordError.value = true;
    return;
  }

  try {
    isLoading.value = true;
    const requestData = {
      username: login.value,
      password: password.value,
    };

    await sleep(800);

    const response = await AuthService.login(requestData);
    const token = response.token;
    localStorage.setItem("authToken", token);
    router.push('/');
  } catch (error: any) {
    let message;

    if (error.response) {
      if (error.response.status === 403) {
        message = "Неверный логин или пароль";
        passwordError.value = true;
        loginError.value = true;
      } else {
        message = error.response.data?.message || "Произошла ошибка на сервере";
      }
    } else {
      message = error.message;
    }

    errorMessage.value = message;
  } finally {
    isLoading.value = false;
  }
}

const clearError = () => {
  errorMessage.value = "";
  passwordError.value = false;
  loginError.value = false;
}
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
        <p class="heading-2 text-color mb-4">С возвращением!</p>
        <p class="body-3 text-color text-opacity-75 font-light">
          Войдите в свой аккаунт и начните общаться прямо сейчас
        </p>
      </div>

      <!--form-->
      <div :class="errorMessage ? 'mb-5' : 'mb-8' ">
        <LabeledTextInput
          @value-changed="(value) => { login = value }"
          @input="clearError"
          :value="login"
          label="Логин"
          placeholder="Введите свой логин"
          :class="loginError ? 'error-input' : ''"
          class="mb-5 rounded-sm focus:border-red-200"
        />
        <PasswordInput
          @value-changed="(value) => { password = value }"
          @input="clearError"
          :value="password"
          label="Пароль"
          placeholder="Введите свой пароль"
          :class="passwordError ? 'error-input' : ''"
          class="rounded-sm focus:border-red-200"
        />

        <!-- error message -->
        <div v-if="errorMessage" class="body-2 input-error-text">
          {{ errorMessage }}
        </div>
      </div>

      <!--auth button-->
      <div class="mb-6">
        <Button :loading="isLoading" class="contained-primary contained-text w-full mb-4" @click="handleLogin" link to="/">
          Войти
        </Button>
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
