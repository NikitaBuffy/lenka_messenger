<script setup lang="ts">
import Button from "@src/components/ui/inputs/Button.vue";
import LabeledTextInput from "@src/components/ui/inputs/LabeledTextInput.vue";
</script>

<template>
  <div>
    <!--form-->
    <div class="mb-5">
      <LabeledTextInput
        label="Почта"
        placeholder="Введите свою электронную почту"
        class="mb-5"
      />
      <LabeledTextInput
        label="Логин"
        placeholder="Придумайте имя пользователя"
        class="mb-5"
      />
      <LabeledTextInput
        label="Имя"
        placeholder="Введите свое имя"
        class="mb-5"
      />
      <LabeledTextInput
        label="Фамилия"
        placeholder="Введите свою фамилию"
        class="mb-5"
      />
    </div>

    <!--local controls-->
    <div class="mb-6">
      <Button
        class="contained-primary contained-text w-full mb-4"
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
