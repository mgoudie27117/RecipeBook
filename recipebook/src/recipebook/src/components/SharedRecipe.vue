<template>
  <div class="vue-template">
      <div class="share-recipe-container">
      <form novalidate @submit.prevent="onSubmit()">
        <div class="text-center share-header">
            <h3>{{model.recipe.recipeName}}</h3>
            <div class="alert alert-info share-entry-recipe" v-if="isBusy">Loading...</div>
            <div class="alert alert-danger share-entry-recipe" v-if="error">{{ error }}</div>
            <div class="alert alert-success share-entry-recipe" v-if="success">{{ success }}</div>
        </div>
        <div class="form-group share-item share-entry-recipe d-flex flex-row">
            <div class="col-2 media-container">
              <div class="inner-container">
                <label>Servings: </label>
                <input type="number" 
                    v-bind="{ id: model.recipe.recipeId }"
                    v-model="model.recipe.servingSize"
                    class="form-control form-control-lg text-center" 
                    min="1"
                    default="1"
                    autocomplete="off" 
                    disabled />
              </div>
            </div>
            <div class="col-1"></div>
            <div class="col-9 paginator text-center">
              <div v-bind="{ id: model.mediaId}" ></div>
              <div v-if="(model.media.length > 0)" class="text-center">
                <div class="pagination text-center">
                  <a v-if="model.paginationPrev" @click="paginate(-1)">&laquo;Previous</a>
                  <a v-if="model.paginationNext" @click="paginate(1)">Next&raquo;</a>
                </div>
              </div>
            </div>
        </div>
        <div class="form-group share-item share-entry-recipe">
            <label>Recipe Description</label>
            <input type="text" 
                v-bind="{ id: model.recipe.recipeId }"
                v-model="model.recipe.recipeDescription"
                class="form-control form-control-lg" 
                autocomplete="off" 
                disabled />
        </div>
        <div class="form-group share-item share-entry-recipe">
            <label>Recipe Instructions</label>
            <textarea 
                rows="10"
                v-bind="{ id: model.recipe.recipeId }"
                v-model="model.recipe.instructions"
                class="form-control form-control-lg" 
                autocomplete="off" 
                disabled />
        </div>
        <div class="ingredient-container ">
          <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3">
            <label class="p-2">Ingredient(s):</label>
            <div class="share-item">
              <ul v-for="(ingredient) in model.ingredients" :key="ingredient" class="p-0">
                <li class="list-unstyled d-flex">
                  <div class="col-1"></div>
                  <div class="col-4">
                    <label>Ingredient Name</label>
                    <input type="text" 
                        v-bind="{ id: model.recipe.recipeId }"
                        v-model="ingredient.ingredientName"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        disabled />
                  </div>
                  <div class="col-1"></div>
                  <div class="col-2">
                      <label>Portion Amount</label>
                      <input type="number" 
                          v-bind="{ id: model.recipe.recipeId }"
                          v-model="ingredient.portionAmount"
                          step="0.01"
                          min="0.01"
                          class="form-control form-control-lg" 
                          autocomplete="off" 
                          disabled />
                  </div>
                  <div class="col-1"></div>
                  <div class="col-2">
                      <label>Measure Unit</label>
                      <div id="measure-selector">
                        <vue-select 
                          v-bind="{ id: model.recipe.recipeId }"
                          :options="options"
                          v-model="ingredient.portionMeasure"
                          :close-on-select="true" 
                          disabled />
                      </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </form>  
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "vue";
import store from "../store/store";
import router from "../router/router";
import axios from "../axios-connection";
import "@dafcoe/vue-swappable-card/dist/vue-swappable-card.css";
export default {
    beforeMount() {
        axios.get("/api/recipe/getrecipe/" + router.currentRoute._value.params.recipeId)
            .then((message) => {
                this.model.recipe = message.data;
                this.model.ingredients = message.data.ingredients;
                this.model.mediaId = router.currentRoute._value.params.recipeId + "_media";
                axios.get("/api/recipemedia/retrieverecipemedianames/" + router.currentRoute._value.params.recipeId)
                 .then((media) => { 
                     this.model.media = media.data;
                     if (media.data.length == 1 && media.data == "DEFAULT.JPG") {
                       var header = document.createElement("p");
                       var text = document.createTextNode("No media was shared, but don't let that stop you for trying the recipe out!");
                       header.appendChild(text);
                       document.getElementById(this.model.mediaId).appendChild(header);
                       document.getElementById(this.model.mediaId).parentNode.classList.remove("paginator");
                     } else if (media.data.length == 1) {
                       var image = document.createElement("img");
                       image.src = "http://localhost:80/api/recipemedia/retrieverecipemedia/" + this.model.recipe.recipeId + "/" + media.data[0];
                       document.getElementById(this.model.mediaId).appendChild(image);
                     } else if (media.data.length > 0) {
                       this.paginate(0);
                     }
                });
            });
    },
    setup() { 
        const options = reactive(store.state.measureUnits);
        const model = reactive({
            paginationIndex: 0,
            paginationPrev: false,
            paginationNext: false,
            mediaId: "_media",
            media: [],
            recipe: {},
            ingredients: []
        });
        function paginate(change) {
          this.model.paginationIndex += change;
          this.model.paginationPrev = (this.model.paginationIndex > 0);
          this.model.paginationNext = ((this.model.paginationIndex + 1) < this.model.media.length);
          var file = this.model.media[this.model.paginationIndex];
          document.getElementById(this.model.mediaId).innerHTML = '';
          var fileType = (file.toUpperCase().includes(".JPEG") || file.toUpperCase().includes(".JPG")) ? "img" : "video";
          var element = document.createElement(fileType);
          element.src = "http://localhost:80/api/recipemedia/retrieverecipemedia/" + this.model.recipe.recipeId + "/" + file;
          document.getElementById(this.model.mediaId).appendChild(element);
        }
        return {
            error: computed(() => store.state.error),
            isBusy: computed(() => store.state.isBusy),
            success: computed(() => store.state.success),
            model,
            options,
            paginate
        }
    }
};
</script>

<style>
.ingredient-container {
  padding-bottom: 5%;
}

.media-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.paginator {
  display: flex;
  flex-direction: column;
}

div.media-container p {
  margin: 0;
  margin-top: 10%;
}

img, video {
  width: 50%;
}

.pagination {
  display: block !important;
  padding: 2%;
}

.pagination a {
  color: black;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a.active {
  background-color: #4CAF50;
  color: white;
}

.pagination a:hover:not(.active) {
  background-color: #ddd;
}

.share-header {
  padding-bottom: 5%;
}
</style>