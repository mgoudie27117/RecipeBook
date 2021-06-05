import { createWebHistory, createRouter } from "vue-router";
import Home from '../components/Home.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';
import store from "../store/store";

const authenticationGuard = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next();
    } else {
        next("/")
    }
};

const routes = [
    {
        path: '/signup',
        name: 'signup',
        component: Signup
    },
    {
        path: '/',
        name: 'login',
        component: Login
    },
    {
        path: '/home',
        name: 'home',
        component: Home,
        beforeEnter: authenticationGuard
    }
];

const router = createRouter({
    history: createWebHistory(),
    mode: 'history',
    routes
});

router.afterEach(() => {
    store.commit("clearError");
    store.commit("setModal", false);
    store.commit("setModalMessage", "");
});

export default router;