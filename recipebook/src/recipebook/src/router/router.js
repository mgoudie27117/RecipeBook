import { createWebHistory, createRouter } from "vue-router";
import Home from '../components/Landing.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';

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
        path: '/landing',
        name: 'landing',
        component: Home
    }
];

const router = createRouter({
    history: createWebHistory(),
    mode: 'history',
    routes
})

export default router;