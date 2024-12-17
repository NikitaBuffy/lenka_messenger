<script setup lang="ts">
import {computed, ref} from "vue";

import PasswordInput from "@src/components/ui/inputs/PasswordInput.vue";
import Button from "@src/components/ui/inputs/Button.vue";

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

const passwordMatch = computed(() => props.regData.password === confirmPassword.value);
const passwordsEntered = computed(() => props.regData.password !== "" && confirmPassword.value !== "");
const passwordLengthValid = computed(() => props.regData.password.length >= 8);

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
    <!--form-->
    <div :class="confirmPasswordEntered && confirmPassword.length > 0 && !passwordMatch ? 'mb-5' : 'mb-8'">
      <PasswordInput
        @valueChanged="(value) => (props.regData.password = value)"
        @input="passwordEntered = false"
        @blur="handlePasswordBlur"
        :value="props.regData.password"
        label="Пароль"
        placeholder="Не менее 8 символов"
        :class="passwordEntered && props.regData.password.length > 0 && !passwordLengthValid ? 'mb-0 error-input' : 'mb-5'"
        class="rounded-sm focus:border-red-200"
      />

      <div v-if="passwordEntered && props.regData.password.length > 0 && !passwordLengthValid" class="mb-5 body-2 input-error-text">
        Размер пароля должен превышать 8 символов
      </div>

      <PasswordInput
        @valueChanged="(value) => (confirmPassword = value)"
        @input="confirmPasswordEntered = false"
        @blur="handleConfirmPasswordBlur"
        :value="confirmPassword"
        label="Подтверждение пароля"
        placeholder="Введите пароль еще раз"
        :class="confirmPasswordEntered && confirmPassword.length > 0 && !passwordMatch ? 'error-input' : ''"
        class="rounded-sm focus:border-red-200"
      />

      <div v-if="confirmPasswordEntered && confirmPassword.length > 0 && !passwordMatch" class="body-2 input-error-text">
        Пароли не совпадают
      </div>
    </div>

    <!--controls-->
    <div class="mb-6">
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
