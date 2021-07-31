<template>
  <div class="vue-template">
    <div class="home-setup">
      <div class="filter-section" @submit.prevent="onSubmit()">
        <div class="text-center pb-1">
          <h5>Recipe Filters</h5>
        </div>
        <div class="filter-option text-center">
          <div>
            <span>Rated & Up</span>
            <div class="d-flex flex-row">
              <div class="col-2"></div>
              <vue3-star-ratings v-model="options.rating" :numberOfStars="5" :starSize="'25'" @click.stop.prevent :step="1" :disableClick="true" :showControl="true"/>
            </div>
          </div>
        </div>
        <div class="filter-option text-center">
          <div>
            <span>Meal Category</span>
            <div class="d-flex flex-row">
              <div class="col-3"></div>
              <vue-select 
                :options="options.mealCategories"
                v-model="options.category"
                :close-on-select="true" />
            </div>
          </div>
        </div>
        <div class="filter-option text-center">
          <div>
            <span>Contains Ingredient</span>
            <div class="d-flex flex-row">
              <div class="col-1"></div>
              <input type="text" 
                v-bind="{ id: 0 }"
                v-model="options.ingredient"
                class="form-control form-control-sm dis-func" 
                autocomplete="off" />
                <div class="col-1"></div>
            </div>
          </div>
        </div>
        <div class="d-flex flex-row pb-1">
          <div class="col-1"></div>
          <button id="apply-filter" @click.stop.prevent="applyFilters()" class="btn btn-dark btn-sm btn-block col-10">Apply Filter(s)</button>
          <div class="col-1"></div>
        </div>
        <div class="d-flex flex-row pt-1">
          <div class="col-1"></div>
          <button id="apply-filter" @click.stop.prevent="resetFilters()" class="btn btn-dark btn-sm btn-block col-10">Reset</button>
          <div class="col-1"></div>
        </div>
      </div>
      <div class="home-container">
        <h1 class="text-center">Delicious shared recipes to try for yourself!</h1>
        <div class="recipe-display">
          <div class="recipe-card wrap" v-for="(recipe) in model.recipes" :key="recipe">
            <div class="recipe-card-item p-2 text-center" @click="handleSelect(recipe)">
              <vue-swappable-card direction="from-top">
                    <template #content-primary>
                        <img  class="p-2" src="" v-bind="{ id: recipe.recipeId }" />
                    </template>
                    <template #content-secondary>
                        <h4 class="p-1">{{recipe.recipeName}}</h4>
                    </template>
                </vue-swappable-card>
            </div>
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
    axios.get("/api/recipe/getmealcategories").then((message) => {
      this.options.mealCategories = message.data;
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
    const options = reactive({
      category: "",
      ingredient: "",
      rating: 0,
      mealCategories: []
    })
    function applyFilters() {
      if (options.rating > 0 || options.category.length > 0 || options.ingredient.length > 0) {
        let filter = {
          category: options.category,
          ingredient: options.ingredient,
          rating: options.rating
        }
        axios.post("/api/recipe/getfilteredrecipes", filter).then((message) => {
          model.recipes = message.data;
          model.recipes.forEach(recipe => {
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
        })
      } else {
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
      }
    }
    function handleSelect(recipe) {
      router.push({ name: "sharedrecipe", params: { recipeId: recipe.recipeId } });
    }
    function resetFilters() {
      location.reload();
    }
    return {
        error: computed(() => store.state.error),
        isBusy: computed(() => store.state.isBusy),
        success: computed(() => store.state.success),
        model,
        options,
        applyFilters,
        handleSelect,
        resetFilters
    }
  }
};
</script>

<style>
body { 
  margin: 0; 
}

.home-container {
    padding-top: 8%;
}

.filter-section {
  margin-top: 2%;
  padding-top: 4%;
}

.vue-tempalte, .App, .vue-template {
    height: 100%;
    width: 100%;
}

.home-setup {
  height: 100%;
  display: flex;
  flex-direction: row;
}

.home-container {
  height: 100%;
  width: 74%;
}

.recipe-display {
  height: 100%;
  display: flex;
  flex-direction: row;
}

img {
    max-width: 100%;
    height: auto;
    width: auto\9; /* ie8 */
}

.wrap    { 
  -webkit-flex-wrap: wrap;
  flex-wrap: wrap;
}  

div.recipe-card-item.p-2.text-center {
  height: 20%;
}

div.recipe-card-item div, .filter-section {
  box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.2);
  background-color: #ffffff !important;
}

.filter-section {
  height: 100%;
  width: 15%;
}

img {
  width: 80%;
}

.filter-option {
  margin-top: 5%;
  margin-bottom: 15%;
}

.vue3-star-ratings__wrapper {
  margin: 0 !important;
}

</style>