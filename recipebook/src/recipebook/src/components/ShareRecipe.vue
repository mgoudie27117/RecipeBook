<template>
  <div class="vue-template">
    <div class="share-recipe-container">
      <form novalidate @submit.prevent="onSubmit()">
        <div class="text-center share-item">
            <h3>Share your recipe with RecipeBook!</h3>
            <div class="alert alert-info share-entry-recipe" v-if="isBusy">Loading...</div>
            <div class="alert alert-danger share-entry-recipe" v-if="error">{{ error }}</div>
            <div class="alert alert-success share-entry-recipe" v-if="success">{{ success }}</div>
        </div>
        <div class="form-group share-item share-entry-recipe d-flex flex-row">
            <div class="col-6">
              <label>Recipe Name</label>
              <input type="text" 
                  v-model="model.recipeName"
                  class="form-control form-control-lg" 
                  autocomplete="off" 
                  required />
            </div>
            <div class="col-4"></div>
            <div class="col-2">
              <label>Serving Size</label>
              <input type="number" 
                  v-model="model.servingSize"
                  class="form-control form-control-lg text-center" 
                  min="1"
                  default="1"
                  autocomplete="off" 
                  required />
            </div>
        </div>
        <div class="form-group share-item share-entry-recipe">
            <label>Recipe Description</label>
            <input type="text" 
                v-model="model.recipeDescription"
                class="form-control form-control-lg" 
                autocomplete="off" 
                required />
        </div>
        <div class="form-group share-item share-entry-recipe">
            <label>Recipe Instructions</label>
            <input type="text" 
                v-model="model.instructions"
                class="form-control form-control-lg" 
                autocomplete="off" 
                required />
        </div>
        <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3">
          <label class="p-2">Ingredient(s):</label>
          <div class="share-item">
            <ul v-for="(ingredient, index) in model.ingredients" :key="ingredient" class="p-0">
              <li class="list-unstyled d-flex">
                <div class="col-1 m-auto text-center" @click.stop.prevent="removeIngredient(index)">
                  <font-awesome-icon icon="minus" class="fa-lg icon-remove mt-4"></font-awesome-icon>
                </div>
                <div class="col-4">
                  <label>Ingredient Name</label>
                  <input type="text" 
                      v-model="ingredient.ingredientName"
                      class="form-control form-control-lg" 
                      autocomplete="off" 
                      required />
                </div>
                <div class="col-1"></div>
                <div class="col-2">
                    <label>Portion Amount</label>
                    <input type="number" 
                        v-model="ingredient.portionAmount"
                        step="0.01"
                        min="0.01"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <div class="col-1"></div>
                <div class="col-2">
                    <label>Measure Unit</label>
                    <div id="measure-selector">
                      <vue-select 
                        :options="options"
                        v-model="ingredient.measureUnit"
                        :close-on-select="true" />
                    </div>
                </div>
                <div class="col-1"></div>
              </li>
            </ul>
            <div class="d-flex">
              <div class="col-1 m-auto text-center" @click.stop.prevent="addIngredient">
                <font-awesome-icon icon="plus" class="fa-lg icon-add plus-work"></font-awesome-icon>
              </div>
              <div class="col-11"></div>
            </div>
          </div>
        </div>
        <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3">
          <label class="p-2">Recipe Media:</label>
          <div class="share-item">
            <ul v-for="(media, index) in model.media" :key="media" class="p-0">
              <li class="list-unstyled d-flex">
                <div class="col-1 m-auto text-center" @click.stop.prevent="removeMedia(index)">
                  <font-awesome-icon icon="minus" class="fa-lg icon-remove mt-4"></font-awesome-icon>
                </div>
                <div class="col-10">
                  <label>Recipe File</label>
                  <input type="file" 
                    class="form-control form-control-lg" 
                    ref="files" 
                    v-on:change="handleFileUpload(index)" />
                </div>
                <div class="col-1"></div>
              </li>
            </ul>
            <div class="d-flex">
              <div class="col-1 m-auto text-center" @click.stop.prevent="addMedia">
                <font-awesome-icon icon="plus" class="fa-lg icon-add plus-work"></font-awesome-icon>
              </div>
              <div class="col-11"></div>
            </div>
          </div>
        </div>
        <div class="share-item share-entry-recipe">
          <button type="submit" class="btn btn-dark btn-lg btn-block space mb-5">Share</button>
          <button type="clear" class="btn btn-dark btn-lg btn-block mb-5" @click.stop.prevent="clearForm">Clear</button>
        </div>
      </form>  
    </div>
  </div>
</template>

<script>
  import { computed, reactive, ref } from "vue";
  import store from "../store/store";
  export default {
    name: "sharerecipe",
    setup() {
      const files = ref([]);
      const options = reactive(store.state.measureUnits);
      const model = reactive({ 
        recipeName: "",
        recipeDescription: "",
        servingSize: 1,
        instructions: "",
        ingredients: [
          {
            ingredientName: "",
            portionAmount: 0.01,
            measureUnit: ""
          }
        ],
        media: [{ file: {} }]
      });
      function onSubmit() {
        store.dispatch("requiredIndication");
        store.dispatch("requiredIndicationSelect", store.state.measureUnits);
        if (store.state.error.length < 1) {
          const recipe = {
            SharedRecipe: {
              recipeName: model.recipeName,
              recipeDescription: model.recipeDescription,
              servingSize: model.servingSize,
              instructions: model.instructions
            },
            Ingredients: [],
            Token: store.state.token,
            Files: []
          };
          model.ingredients.forEach(ingredient => recipe.Ingredients.push({
              ingredientName: ingredient.ingredientName,
              portionAmount: ingredient.portionAmount,
              portionMeasure: ingredient.measureUnit
            }));
          model.media.forEach(media => {
            if (media.file && media.file.name && media.file.name.length > 0) {
              let mediaAdd = media.file;
              recipe.Files.push({ mediaAdd })
            }
          });
          store.dispatch("shareRecipe", recipe);
        }
      }
      function addIngredient() {
        model.ingredients.push({
            ingredientName: "",
            portionAmount: 0.01,
            measureUnit: ""
          });
      }
      function addMedia() {
        model.media.push({ file: {} });
      }
      function clearForm() {
        model.recipeName = "";
        model.recipeDescription = "";
        model.servingSize = 1;
        model.instructions = "";
        model.ingredients = model.ingredients.slice(1, 1);
        model.ingredients[0] = {
            ingredientName: "",
            portionAmount: 0.01,
            measureUnit: ""
          };
        model.media = model.media.slice(1, 1);
        model.media = [{ file: {} }]
        let inputs = document.querySelectorAll('input[type=text]');
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].style.border = "1px solid #ced4da";
        }
        store.commit("clearError");
        store.commit("clearBusy");
        store.commit("clearSuccess");
      }
      function handleFileUpload(index) {
        if (files.value.files[0].name.toLowerCase().includes(".mp4")
          || files.value.files[0].name.toLowerCase().includes(".jpeg")
          || files.value.files[0].name.toLowerCase().includes(".jpg")) {
            model.media[index].file = files.value.files[0];
        } else {
          store.commit("setError", "Only .jpg, .jpeg, or .mp4 files are accepted for sharing!");
        }
      }
      function removeIngredient(index) {
        if (model.ingredients.length > 1 && index > -1) {
          model.ingredients.splice(index, 1);
        }
      }
      function removeMedia(index) {
        if (model.media.length > 1 && index > -1) {
          model.media.splice(index, 1);
        }
      }
      return {
        error: computed(() => store.state.error),
        isBusy: computed(() => store.state.isBusy),
        measureUnits: computed(() => store.state.measureUnits),
        success: computed(() => store.state.success),
        model,
        onSubmit,
        addIngredient,
        addMedia,
        removeIngredient,
        removeMedia,
        clearForm,
        handleFileUpload,
        options,
        files
      };
    }
  };
</script>

<style>
.icon-add {
  color: rgb(0, 148, 0);
}

.icon-remove {
  color: red;
  margin-left: 1%;
}

div.ingredient-border {
  border-color: #ced4da !important;
}

#measure-selector div.vue-select {
  min-height: calc(1.5em + 1rem + 2px);
  padding: .5rem 1rem;
  font-size: 1.25rem;
  border-radius: .3rem;
  border-color: #ced4da !important;
}

.share-recipe-container {
    height: 100%;
    width: 100%;
}

.share-item {
  padding-top: 1%;
}

.share-entry-recipe {
  margin: auto;
  width: 80%;
}

.space {
  margin-right: 5%;
}
</style>