<script setup lang="ts">
import { computed, ref } from "vue";
import Button from "@src/components/ui/inputs/Button.vue";
import LabeledTextInput from "@src/components/ui/inputs/LabeledTextInput.vue";

const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

const props = defineProps<{
  regData: {
    email: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
  };
}>();

const errorMessage = ref("");
const loginError = ref(false);
const emailError = ref(false);

const handleLoginInput = () => {
  // change it after backend is completed
  if (props.regData.username === 'hello') {
    errorMessage.value = "Имя пользователя уже существует";
    loginError.value = true;
  } else {
    errorMessage.value = "";
  }
}

const handleEmailInput = () => {
  if (!emailRegex.test(props.regData.email)) {
    errorMessage.value = "Введите корректную почту";
    emailError.value = true;
  } else {
    errorMessage.value = "";
  }
}

const personalFieldsFilled = computed(() => {
  return [props.regData.username, props.regData.email, props.regData.firstName, props.regData.lastName]
    .every(value => value.trim() !== "");
});

const clearError = (field: string) => {
  switch (field) {
    case 'login': loginError.value = false; break;
    case 'email': emailError.value = false; break;
  }
  errorMessage.value = "";
}
</script>

<template>
  <div>
    <!--form-->
    <div :class="errorMessage ? 'mb-5' : 'mb-8' ">
      <LabeledTextInput
        @value-changed="(value) => props.regData.email = value"
        :value="props.regData.email"
        @blur="handleEmailInput"
        @input="clearError('email')"
        label="Почта"
        placeholder="Введите свою электронную почту"
        :class="emailError ? 'error-input' : ''"
        class="mb-5"
      />
      <LabeledTextInput
        @value-changed="(value) => props.regData.username = value"
        :value="props.regData.username"
        @blur="handleLoginInput"
        @input="clearError('login')"
        label="Логин"
        placeholder="Придумайте имя пользователя"
        :class="loginError ? 'error-input' : ''"
        class="mb-5 rounded-sm focus:border-red-200"
      />
      <LabeledTextInput
        @value-changed="(value) => props.regData.firstName = value"
        :value="props.regData.firstName"
        label="Имя"
        placeholder="Введите свое имя"
        class="mb-5"
      />
      <LabeledTextInput
        @value-changed="(value) => props.regData.lastName = value"
        :value="props.regData.lastName"
        label="Фамилия"
        placeholder="Введите свою фамилию"
      />

      <!-- error message -->
      <div v-if="errorMessage" class="body-2 input-error-text">
        {{ errorMessage }}
      </div>
    </div>

    <!--local controls-->
    <div class="mb-6">
      <Button
        class="contained-primary contained-text w-full"
        :disabled="!(personalFieldsFilled && !loginError && !emailError)"
        @click="
          $emit('active-section-change', {
            sectionName: 'password-section',
            animationName: 'slide-left',
          })
        ">
        Далее
      </Button>
    </div>
  </div>
</template>
