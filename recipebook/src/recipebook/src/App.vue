<template>
  <div class="vue-tempalte">
    
    <!-- Navigation -->
    <nav class="navbar shadow bg-white rounded justify-content-between flex-nowrap flex-row fixed-top">
      <div class="container">
        <ul class="nav navbar-nav flex-row float-right">
          <li class="nav-item">
            <span v-if="!isAuthenticated">
              <router-link class="btn btn-outline-secondary" to="/">Sign in</router-link>
            </span>
          </li>
          <li class="nav-item">
            <span v-if="!isAuthenticated">
              <router-link class="btn btn-outline-secondary" to="/signup">Sign up</router-link>
            </span>
          </li>
          <li class="nav-item">
            <span v-if="isAuthenticated">
              <router-link class="btn btn-outline-secondary" @click.prevent="onLogout" to="/">Sign out</router-link>
            </span>
          </li>
        </ul>
      </div>
    </nav>

    <!-- Main -->
    <div class="App">
      <div class="vertical-center">
        <div class="inner-block">
          <router-view />
        </div>
      </div>
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
        onLogout: () => store.dispatch("logout")
      }
    }
  };
</script>