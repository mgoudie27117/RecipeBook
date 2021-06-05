import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';

import router from './router/router';
import store from './store/store';
import './assets/bootstrap/css/bootstrap.min.css';
import './assets/css/main.css';

axios.defaults.baseURL = 'http://localhost:3000/';
axios.defaults.headers.get['Accepts'] = 'application/json';

const reqInterceptor = axios.interceptors.request.use(config => {
  console.log('Request Interceptor', config)
  return config
});
const resInterceptor = axios.interceptors.response.use(res => {
  console.log('Response Interceptor', res)
  return res
});

axios.interceptors.request.eject(reqInterceptor);
axios.interceptors.response.eject(resInterceptor);

createApp(App).use(store).use(router).mount("#app");