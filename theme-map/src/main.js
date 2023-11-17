<<<<<<< HEAD
import './assets/css/main.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);

app.mount('#app');
=======
import './assets/css/main.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);

app.mount('#app');
>>>>>>> e3b64e880d356a0a02c316734734812c983d7445
