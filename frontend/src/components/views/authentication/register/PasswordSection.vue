<script setup lang="ts">
import {computed, ref} from "vue";
import { useRouter } from "vue-router";
import AuthService from '@src/services/AuthService';
import { sleep } from "@src/utils";

import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";
import Button from "@src/components/ui/inputs/Button.vue";

const router = useRouter();

const props = defineProps<{
  regData: {
    email: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
  };
}>();
const confirmPassword = ref("");
const isLoading = ref(false);
const errorMessage = ref("");
const passwordError = ref(false);
const confirmPasswordError = ref(false);

const handlePasswordInput = () => {
  if (props.regData.password.length < 8) {
    errorMessage.value = "Размер пароля должен превышать 8 символов";
    passwordError.value = true;
  } else {
    errorMessage.value = "";
  }
};

const handleConfirmPasswordInput = () => {
  if (props.regData.password !== confirmPassword.value) {
    errorMessage.value = "Пароли не совпадают";
    confirmPasswordError.value = true;
  } else {
    errorMessage.value = "";
  }
}

const clearError = (field: string) => {
  switch (field) {
    case 'main': passwordError.value = false; break;
    case 'confirm': confirmPasswordError.value = false; break;
  }
}

const canSubmit = computed(() => {
  const passwordsEntered = props.regData.password.trim() !== '' && confirmPassword.value.trim() !== '';
  const noErrors = !passwordError.value && !confirmPasswordError.value;
  return passwordsEntered && noErrors;
});

const handleRegister = async () => {
  try {
    isLoading.value = true;

    await sleep(800);

    const response = await AuthService.register(props.regData);
    const token = response.token;
    localStorage.setItem("authToken", token);
    router.push('/');
  } catch (error: any) {
    let message;

    if (error.response) {
      message = error.response.data?.message || "Произошла ошибка на сервере";
    } else {
      message = error.message;
    }

    errorMessage.value = message;
  } finally {
    isLoading.value = false;
  }
}
</script>

<template>
  <div>
    <!--form-->
    <div :class="errorMessage ? 'mb-5' : 'mb-8' ">
      <PasswordInput
        @valueChanged="(value) => (props.regData.password = value)"
        @input="clearError('main')"
        @blur="handlePasswordInput"
        :value="props.regData.password"
        label="Пароль"
        placeholder="Не менее 8 символов"
        :class="passwordError ? 'error-input' : ''"
        class="mb-5 rounded-sm focus:border-red-200"
      />

      <PasswordInput
        @valueChanged="(value) => (confirmPassword = value)"
        @input="clearError('confirm')"
        @blur="handleConfirmPasswordInput"
        :value="confirmPassword"
        label="Подтверждение пароля"
        placeholder="Введите пароль еще раз"
        :class="confirmPasswordError ? 'error-input' : ''"
        class="rounded-sm focus:border-red-200"
      />

      <!-- error message -->
      <div v-if="errorMessage" class="body-2 input-error-text">
        {{ errorMessage }}
      </div>
    </div>

    <!--controls-->
    <div class="mb-6">
      <Button
          class="contained-primary contained-text w-full mb-4"
          :disabled="!canSubmit"
          @click="handleRegister"
          :loading="isLoading"
      >
        Зарегистрироваться
      </Button>
      <Button
        class="outlined-primary outlined-text w-full"
        @click="
          props.regData.password = '';
          $emit('active-section-change', {
            sectionName: 'personal-section',
            animationName: 'slide-right',
          })
        ">
        Назад
      </Button>
    </div>
  </div>
</template>
