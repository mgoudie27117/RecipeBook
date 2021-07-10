<template>
  <div class="vue-template">
    <div class="home-container">
      <h1 class="text-center">Delicious shared recipes to try for yourself!</h1>
      <div class="recipe-display">
        <div class="recipe-card" v-for="(recipe) in model.recipes" :key="recipe">
          <div class="recipe-card-item p-2 text-center" @click="handleSelect(recipe)">
            <vue-swappable-card direction="from-top">
                  <template #content-primary>
                      <img  class="p-2" src="" v-bind="{ id: recipe.recipeId }" />
                  </template>
                  <template #content-secondary>
                      <h4 class="p-1">{{recipe.recipeName}}</h4>
                      <h5 class="p-1">{{recipe.recipeDescription}}</h5>
                  </template>
              </vue-swappable-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "vue";
import router from "../router/router";
import store from "../store/store";
import axios from "../axios-connection";
import "@dafcoe/vue-swappable-card/dist/vue-swappable-card.css";
export default {
  beforeMount() {
    axios.post("/api/recipe/getfavoriterecipes", { token: store.state.token }).then((message) => {
      store.commit("setFavorites", message.data);
    });
  },
  mounted() {
      axios.get("/api/recipe/gethomerecipes").then((message) => {
      this.model.recipes = message.data;
      this.model.recipes.forEach(recipe => {
        axios.get("/api/recipemedia/retrieverecipemedianames/" + recipe.recipeId)
          .then((message) => { 
              let file = "DEFAULT.JPG";
              message.data.forEach(mediaItem => {
                if (!mediaItem.toUpperCase().includes("DEFAULT") && (mediaItem.toUpperCase().includes(".JPG") || mediaItem.toUpperCase().includes(".JPEG"))) {
                  file = mediaItem;
                }
              });
              let id = (file == "DEFAULT.JPG") ? "DEFAULT" : recipe.recipeId;
              document.getElementById(recipe.recipeId).src = "http://localhost:80/api/recipemedia/retrieverecipemedia/" + id + "/" + file;
          })
      });
    });
  },
  setup() { 
    const model = reactive({
      recipes: []
    });
    function handleSelect(recipe) {
      router.push({ name: "sharedrecipe", params: { recipeId: recipe.recipeId } });
    }
    return {
        error: computed(() => store.state.error),
        isBusy: computed(() => store.state.isBusy),
        success: computed(() => store.state.success),
        model,
        handleSelect
    }
  }
};
</script>

<style>
body { margin: 0; }

.home-container {
    padding-top: 8%;
}

.vue-tempalte, .App, .vue-template, .home-container {
    height: 100%;
    width: 100%;
}

div.recipe-display {
  display: flex;
  flex-direction: row;
}

.recipe-card {
  width: 25%;
}

div.recipe-card-item div {
  box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
  background-color: #ffffff !important;
}

img {
  width: 80%;
}

</style>