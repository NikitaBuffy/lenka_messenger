<script setup lang="ts">
import {computed, ref} from "vue";

import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";
import Button from "@src/components/ui/inputs/Button.vue";

const password = ref("");
const confirmPassword = ref("");

const passwordMatch = computed(() => password.value === confirmPassword.value);
const passwordsEntered = computed(() => password.value !== "" && confirmPassword.value !== "");
const passwordLengthValid = computed(() => password.value.length >= 8);

const passwordEntered = ref(false);
const handlePasswordBlur = () => {
  passwordEntered.value = true;
};

const confirmPasswordEntered = ref(false);
const handleConfirmPasswordBlur = () => {
  confirmPasswordEntered.value = true;
}
</script>

<template>
  <div>
    <div class="mb-5">
      <!--form-->
      <PasswordInput
        @valueChanged="(value) => (password = value)"
        @blur="handlePasswordBlur"
        :value="password"
        label="Пароль"
        placeholder="Придумайте пароль для входа"
        :class="passwordEntered && password.length > 0 && !passwordLengthValid ? 'border border-red-500 focus:ring-red-200' : ''"
        class="rounded-sm focus:border-red-200 mb-4"
      />

      <div v-if="passwordEntered && password.length > 0 && !passwordLengthValid" class="mb-4 text-red-500 body-2">
        Размер пароля должен превышать 8 символов!
      </div>

      <PasswordInput
        @valueChanged="(value) => (confirmPassword = value)"
        @blur="handleConfirmPasswordBlur"
        :value="confirmPassword"
        label="Подтверждение пароля"
        placeholder="Введите ваш пароль еще раз"
        :class="confirmPasswordEntered && confirmPassword.length > 0 && !passwordMatch ? 'border border-red-500 focus:ring-red-200' : ''"
        class="rounded-sm focus:border-red-200"
      />

      <div v-if="confirmPasswordEntered && confirmPassword.length > 0 && !passwordMatch" class="mt-3 text-red-500 body-2">
        Пароли не совпадают!
      </div>
    </div>

    <!--controls-->
    <div class="mb-5">
      <Button class="contained-primary contained-text w-full mb-4" :disabled="!(passwordMatch && passwordsEntered && passwordLengthValid)">
        Зарегистрироваться
      </Button>
      <Button
        class="outlined-primary outlined-text w-full"
        @click="
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
