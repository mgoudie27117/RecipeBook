import { createWebHistory, createRouter } from "vue-router";
import FavoriteRecipes from "../components/FavoriteRecipes.vue";
import ForgotPassword from "../components/ForgotPassword.vue";
import Home from "../components/Home.vue";
import Login from "../components/Login.vue";
import Signup from "../components/Signup.vue";
import ShareRecipe from "../components/ShareRecipe.vue";
import SharedRecipe from "../components/SharedRecipe.vue";
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
        path: "/favoriterecipes",
        name: "favoriterecipes",
        component: FavoriteRecipes,
        beforeEnter: authenticationGuard
    },
    {
        path: "/forgotpassword",
        name: "forgotpassword",
        component: ForgotPassword
    },
    {
        path: "/home",
        name: "home",
        component: Home,
        beforeEnter: authenticationGuard
    },
    {
        path: "/",
        name: "login",
        component: Login
    },
    {
        path: "/sharerecipe",
        name: "sharerecipe",
        component: ShareRecipe,
        beforeEnter: authenticationGuard
    },
    {
        path: "/sharedrecipe",
        name: "sharedrecipe",
        component: SharedRecipe,
        props: true,
        beforeEnter: authenticationGuard
    },
    {
        path: "/signup",
        name: "signup",
        component: Signup
    }
];

const router = createRouter({
    history: createWebHistory(),
    linkActiveClass: "active",
    linkExactActiveClass: "exact-active",
    mode: 'history',
    routes
});

router.afterEach(() => {
    store.commit("clearError");
    store.commit("clearSuccess");
});

export default router;