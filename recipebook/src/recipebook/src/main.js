import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import router from "./router/router";
import store from "./store/store";
import "./assets/bootstrap/css/bootstrap.min.css";
import "./assets/css/main.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import VueNextSelect from "vue-next-select";
import "vue-next-select/dist/index.min.css";
import VueSwappableCard from "@dafcoe/vue-swappable-card";
import vue3StarRatings from "vue3-star-ratings";

library.add(faPlus, faMinus);

axios.defaults.baseURL = "http://localhost:3000/";
axios.defaults.headers.get["Accepts"] = "application/json";

const reqInterceptor = axios.interceptors.request.use(config => {
  return config;
});
const resInterceptor = axios.interceptors.response.use(res => {
  return res;
});

axios.interceptors.request.eject(reqInterceptor);
axios.interceptors.response.eject(resInterceptor);

createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .component('vue-select', VueNextSelect)
  .component("vue3-star-ratings", vue3StarRatings)
  .use(store)
  .use(router)
  .use(VueSwappableCard)
  .mount("#app");