import { createWebHistory, createRouter } from "vue-router";
import Home from '../components/Landing.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';

const router = createRouter({
    mode: 'history',
    history: createWebHistory(),
    base: process.env.BASE_URL,
    routes: [
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
    ]
});

export default router;