<template>
  <div class="vue-tempalte">
    <!-- Navigation -->
    <nav
      class="navbar navbar-default shadow bg-white rounded fixed-top"
    >
      <div class="container-fluid">
        <div class="navbar-header">
          <h1>
              <router-link class="nav-link fs-5"
                to="/home"
                >RecipeBook</router-link
              >
            </h1>
        </div>
        <ul class="nav navbar-nav flex-row">
          <li class="nav-item me-3">
            <span v-if="!isAuthenticated">
              <router-link class="nav-link" to="/"
                >Sign in</router-link
              >
            </span>
          </li>
          <li class="nav-item me-3">
            <span v-if="!isAuthenticated">
              <router-link class="nav-link" to="/signup"
                >Sign up</router-link 
              >
            </span>
          </li>
          <li class="nav-item me-3">
            <span v-if="isAuthenticated">
              <router-link class="nav-link"
                to="/sharerecipe"
                >Share Recipe</router-link 
              >
            </span>
          </li>
          <li class="nav-item me-3">
            <span v-if="isAuthenticated">
              <router-link class="nav-link" to="/favoriterecipes"
                >Favorite Recipes</router-link
              >
            </span>
          </li>
          <li class="nav-item me-3">
            <span v-if="isAuthenticated">
              <router-link  class="nav-link"
                @click.prevent="onLogout"
                to="/"
                >Sign out</router-link
              >
            </span>
          </li>
        </ul>
      </div>
    </nav>

    <!-- Main -->
    <div class="App">
          <router-view />
    </div>
  </div>
</template>

<script>
import store from "./store/store";
import { computed } from "vue";
export default {
  name: "App",
  setup() {
    return {
      error: computed(() => store.state.error),
      isAuthenticated: computed(() => store.getters.isAuthenticated),
      isBusy: computed(() => store.state.isBusy),
      onLogout: () => store.dispatch("logout"),
    };
  },
};
</script>
