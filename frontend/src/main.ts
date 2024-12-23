import router from "@src/router";
import "@src/style.css";
import { createPinia } from "pinia";
import { createApp } from "vue";
import vClickOutside from "click-outside-vue3";
import axios from 'axios';

import App from "@src/App.vue";

const pinia = createPinia();

axios.defaults.baseURL="http://localhost:8080";
axios.defaults.headers.post['Content-Type'] = 'application/json';

createApp(App).use(pinia).use(router).use(vClickOutside).mount("#app");
