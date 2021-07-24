<template>
  <div class="vue-template">
      <div class="share-recipe-container">
      <form novalidate >
        <div class="text-center share-header">
            <h3>{{model.recipe.recipeName}}</h3>
            <div class="alert alert-info share-entry-recipe" v-if="isBusy">Loading...</div>
            <div class="alert alert-danger share-entry-recipe" v-if="error">{{ error }}</div>
            <div class="alert alert-success share-entry-recipe" v-if="success">{{ success }}</div>
        </div>
        <div id="Btn-Container" class="form-group share-item share-entry-recipe d-flex flex-row">
          <button id="print-button" @click.stop.prevent="print()" class="btn btn-dark btn-lg btn-block col-3">Print Recipe</button>
          <div class="col-1"></div>
          <button id="fav-button" type="submit" @click.stop.prevent="favoriteRecipe()" class="btn btn-dark btn-lg btn-block col-3">{{model.buttonText}}</button>
          <div class="col-1"></div>
          <button id="update-button" v-if="model.isUserRecipe" type="submit" @click.stop.prevent="updateRecipe()" class="btn btn-dark btn-lg btn-block col-3">Update Recipe</button>
          <button id="review-button" v-if="!model.isUserRecipe && !model.hasReviewed && !model.readyReview" type="submit" @click.stop.prevent="reviewRecipe()" class="btn btn-dark btn-lg btn-block col-3">Review Recipe</button>
          <div class="col-1"></div>
        </div>
        <div v-if="model.readyReview">
          <div class="d-flex flex-row">
            <div class="col-4"></div>
            <div class="col-4">
              <vue3-star-ratings v-model="model.rating" @click.stop.prevent :step="1" :disableClick="true" />
            </div>
            <div class="col-4"></div>
          </div>
          <div class="d-flex flex-row">
            <div class="col-2"></div>
            <div class="col-8">
              <label>Leave a review</label>
              <textarea 
                      id="comment-text"
                      rows="10"
                      v-model="model.reviewComment"
                      class="form-control form-control-lg" 
                      autocomplete="off" />
            </div>
            <div class="col-2"></div>
          </div>
          <div class="d-flex flex-row pt-3 pb-4">
            <div class="col-2"></div>
            <button id="review-submission-button" @click.stop.prevent="submitReview()" class="btn btn-dark btn-lg btn-block col-3">Submit Review</button>
            <div class="col-7"></div>
          </div>
        </div>
        <div class="form-group share-item share-entry-recipe">
          <label>Recipe Description</label>
          <input type="text" 
              v-bind="{ id: model.recipe.recipeId }"
              v-model="model.recipe.recipeDescription"
              class="form-control form-control-lg dis-func" 
              autocomplete="off" 
              disabled />
        </div>
        <div class="form-group share-item share-entry-recipe d-flex flex-row">
            <div class="col-2 ">
              <div class="inner-container">
                <label>Servings: </label>
                <input type="number" 
                    v-bind="{ id: model.recipe.recipeId }"
                    v-model="model.recipe.servingSize"
                    class="form-control form-control-lg text-center dis-func" 
                    min="1"
                    default="1"
                    autocomplete="off" 
                    disabled />
              </div>
            </div>
            <div class="col-1"></div>
            <div class="col-9 paginator text-center">
              <div v-bind="{ id: model.mediaId}" ></div>
              <div v-if="(model.mediaNames.length > 0)" class="text-center">
                <div class="pagination text-center">
                  <a v-if="model.paginationPrev" @click="paginate(-1)">&laquo;Previous</a>
                  <a v-if="model.paginationNext" @click="paginate(1)">Next&raquo;</a>
                </div>
              </div>
            </div>
        </div>
        <div id="Printable">
          <div>
            <div class="form-group share-item share-entry-recipe">
                <label>Recipe Instructions</label>
                <textarea 
                    rows="10"
                    v-bind="{ id: model.recipe.recipeId }"
                    v-model="model.recipe.instructions"
                    class="form-control form-control-lg dis-func" 
                    autocomplete="off" 
                    disabled />
            </div>
            <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3">
                <label class="p-2">Ingredient(s):</label>
                <div class="share-item">
                  <ul v-for="(ingredient, index) in model.recipe.ingredients" :key="ingredient" class="p-0">
                    <li class="list-unstyled d-flex">
                      <div class="col-1 m-auto text-center" @click.stop.prevent="removeIngredient(index, ingredient)">
                        <font-awesome-icon icon="minus" class="fa-lg icon-remove mt-4" v-if="model.updateAvailable"></font-awesome-icon>
                      </div>
                      <div class="col-4">
                        <label>Ingredient Name</label>
                        <input type="text" 
                            v-bind="{ id: model.recipe.recipeId }"
                            v-model="ingredient.ingredientName"
                            class="form-control form-control-lg dis-func" 
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
                              class="form-control form-control-lg dis-func" 
                              autocomplete="off" 
                              disabled />
                      </div>
                      <div class="col-1"></div>
                      <div class="col-2">
                          <label>Measure Unit</label>
                          <div id="measure-selector">
                            <vue-select 
                              :options="options"
                              v-model="ingredient.portionMeasure"
                              :close-on-select="true" 
                              :disabled="!model.updateAvailable" />
                          </div>
                      </div>
                      <div class="col-1"></div>
                    </li>
                  </ul>
                  <ul class="p-0" v-if="model.updateAvailable">
                    <li class="list-unstyled d-flex">
                      <div class="col-1 m-auto text-center" @click.stop.prevent="addIngredient()">
                        <font-awesome-icon icon="plus" class="fa-lg icon-add plus-work"></font-awesome-icon>
                      </div>
                      <div class="col-11"></div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group share-item share-entry-recipe" v-if="model.updateAvailable">
            <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3 col-12">
              <label class="p-2">Add Recipe Media:</label>
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
                <ul class="p-0">
                  <li class="list-unstyled d-flex">
                    <div class="col-1 m-auto text-center" @click.stop.prevent="addMedia">
                      <font-awesome-icon icon="plus" class="fa-lg icon-add plus-work"></font-awesome-icon>
                    </div>
                    <div class="col-11"></div>
                  </li>
                </ul>
              </div>
            </div>
            <div class="form-group share-item share-entry-recipe rounded border ingredient-border mt-3 mb-3 pb-3 col-12" v-if="model.mediaNames.length > 0">
              <label class="p-2">Remove Recipe Media:</label>
              <div class="share-item">
                <ul v-for="(media, index) in model.mediaNames" :key="media" class="p-0">
                  <li class="list-unstyled d-flex" v-if="media != 'DEFAULT.JPG'">
                    <div class="col-1 m-auto text-center" @click.stop.prevent="removeRecipeMedia(index)">
                      <font-awesome-icon icon="minus" class="fa-lg icon-remove mt-4"></font-awesome-icon>
                    </div>
                    <div class="col-10">
                      <label>Recipe File</label>
                      <input type="text" 
                        v-model="model.mediaNames[index]"
                        class="form-control form-control-lg" />
                    </div>
                    <div class="col-1"></div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        <div class="share-item share-entry-recipe">
          <button v-if="model.updateAvailable" type="submit" @click.stop.prevent="submitUpdate()" class="btn btn-dark btn-lg btn-block space mb-5">Submit Update</button>
        </div>
        <div class="d-flex flex">
          <div class="col-1"></div>
          <div class="col-4"><h4>Recipe Reviews</h4></div>
          <div class="col-7"></div>
        </div>
        <div v-if="model.reviews.length == 0" class="d-flex flex pb-5">
          <div class="col-2"></div>
          <div class="col-8 text-center"><h5>No reviews for this recipe. Try the recipe and be the first to rate it!</h5></div>
          <div class="col-2"></div>
        </div>
        <ul v-for="(review) in model.reviews" :key="review" class="p-0">
          <li class="list-unstyled pb-5">
            <div class="d-flex flex">
              <div class="col-1"></div>
              <div class="rounded border ingredient-border col-10">
                <div class="d-flex flex-row">
                  <div class="col-1"></div>
                  <div class="col-4 critic">
                    <h5>Reviewed by: {{review.critic}}</h5>
                  </div>
                  <div class="col-2"></div>
                  <div class="col-4">
                    <vue3-star-ratings v-model="review.rating" @click.stop.prevent :step="1" :showControl="false" :disableClick="true" />
                  </div>
                  <div class="col-1"></div>
                </div>
                <div class="d-flex flex-row pb-3">
                  <div class="col-1"></div>
                  <div class="col-10">
                    <label>Review</label>
                    <textarea 
                            id="comment-text"
                            rows="5"
                            v-model="review.comments"
                            class="form-control form-control-lg" 
                            autocomplete="off" 
                            disabled />
                  </div>
                  <div class="col-1"></div>
                </div>
                <div class="col-1"></div>
              </div>
            </div>
          </li>
        </ul>
      </form>  
    </div>
  </div>
</template>

<script>
import { computed, reactive, ref } from "vue";
import store from "../store/store";
import router from "../router/router";
import axios from "../axios-connection";
import "@dafcoe/vue-swappable-card/dist/vue-swappable-card.css";
export default {
    beforeMount() {
        axios.post("/api/recipe/isuserrecipe", { recipeId: router.currentRoute._value.params.recipeId, token: store.state.token })
          .then((message) => {
            this.model.isUserRecipe = message.data;
          });
        axios.post("/api/recipe/hasreviewed", { recipeId: router.currentRoute._value.params.recipeId, token: store.state.token })
          .then((message) => {
            this.model.hasReviewed = message.data;
          });
        axios.get("/api/recipe/getreviews/" + router.currentRoute._value.params.recipeId).then((message) => {
          this.model.reviews = message.data;
          console.log(message.data);
        });
        this.favoriteCheck();
        axios.get("/api/recipe/getrecipe/" + router.currentRoute._value.params.recipeId)
            .then((message) => {
                this.model.recipe = message.data;
                this.model.ingredients = message.data.ingredients;
                this.model.mediaId = router.currentRoute._value.params.recipeId + "_media";
                axios.get("/api/recipemedia/retrieverecipemedianames/" + router.currentRoute._value.params.recipeId)
                 .then((media) => { 
                     this.model.mediaNames = media.data;
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
        const files = ref([]);
        const options = reactive(store.state.measureUnits);
        const model = reactive({
            buttonText: "",
            hasReviewed: false,
            isFavorite: false,
            isUserRecipe: false,
            paginationIndex: 0,
            paginationPrev: false,
            paginationNext: false,
            mediaId: "_media",
            media: [{ file: {} }],
            mediaNames: [],
            rating: 0,
            readyReview: false,
            reviews: [],
            reviewComment: "",
            removeIngredients: [],
            removeMedia: [],
            recipe: {},
            ingredients: [],
            updateAvailable: false
        });
        function addIngredient() {
          model.ingredients.push({
              ingredientName: "",
              portionAmount: 0.01,
              portionMeasure: ""
            });
            setTimeout(function() {
              var nodes = document.getElementsByClassName("dis-func");
              for (var j = 0; j < nodes.length; j++) {
                nodes[j].disabled = false;
              }
            }, 200);
        }
        function addMedia() {
          model.media.push({ file: {} });
        }
        function favoriteCheck() {
          axios.post("/api/recipe/isfavoriterecipe", { recipeId: router.currentRoute._value.params.recipeId, token: store.state.token })
          .then((message) => {
              this.model.isFavorite = message.data;
              if (this.model.isFavorite) {
                this.model.buttonText = "Remove Favorite";
                if (document.getElementById("fav-button").classList.contains("btn-dark")) {
                  document.getElementById("fav-button").classList.remove("btn-dark");
                }
                if (!document.getElementById("fav-button").classList.contains("btn-danger")) {
                  document.getElementById("fav-button").classList.add("btn-danger");
                }
              } else {
                this.model.buttonText = "Add Favorite";
                if (document.getElementById("fav-button").classList.contains("btn-danger")) {
                  document.getElementById("fav-button").classList.remove("btn-danger");
                }
                if (!document.getElementById("fav-button").classList.contains("btn-dark")) {
                  document.getElementById("fav-button").classList.add("btn-dark");
                }
              }
          });
        }
        function favoriteRecipe() {
          let data = { recipeId: this.model.recipe.recipeId, token: store.state.token };
          if (this.model.isFavorite) {
            axios.post("/api/recipe/removefavoriterecipe", data)
              .then((message) => {
                store.commit("setFavorites", message.data);
                this.favoriteCheck();
              })
          } else {
            axios.post("/api/recipe/addfavoriterecipe", data)
              .then((message) => {
                store.commit("setFavorites", message.data);
                this.favoriteCheck();
              })
          }
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
        function paginate(change) {
          model.paginationIndex += change;
          model.paginationPrev = (model.paginationIndex > 0);
          model.paginationNext = ((model.paginationIndex + 1) < model.mediaNames.length);
          var file = model.mediaNames[model.paginationIndex];
          document.getElementById(model.mediaId).innerHTML = '';
          var fileType = (file.toUpperCase().includes(".JPEG") || file.toUpperCase().includes(".JPG")) ? "img" : "video";
          var element = document.createElement(fileType);
          element.src = "http://localhost:80/api/recipemedia/retrieverecipemedia/" + model.recipe.recipeId + "/" + file;
          document.getElementById(model.mediaId).appendChild(element);
        }
        function prepareJSON(string) {
            return (string.replaceAll(" ", "__")).replaceAll(",", "---");
        }
        function print() {
          const WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
          var ingredientHtml = `<div class="row">
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item"><b>Ingredient Name</b></div>
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item"><b>Portion Amount</b></div>
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item"><b>Portion Measure</b></div>
                                </div>`;
          model.recipe.ingredients.forEach((ingredient) => {
            ingredientHtml = ingredientHtml + `
                                <div class="row">
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item">${ingredient.ingredientName}</div>
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item">${ingredient.portionAmount}</div>
                                  <div class="col-1"></div>
                                  <div class="col-3 ingredient-item">${ingredient.portionMeasure}</div>
                                </div>
                              `;
          });
          WinPrint.document.write(`<!DOCTYPE html>
                                    <html>
                                      <head>
                                        <h1>${model.recipe.recipeName}</h1>
                                      </head>
                                      <body>
                                        <div>
                                          <p><br /><br /><b>Recipe Description: </b><br />${model.recipe.recipeDescription}<br /><br /></p>
                                        </div>
                                        <div>
                                          <p><b>Recipe Instructions: </b><br />${model.recipe.instructions}<br /><br /></p>
                                        </div>
                                        ` + ingredientHtml + `
                                      </body>
                                      <style>
                                        h1, .ingredient-item {
                                          text-align: center;
                                        }
                                        .row::after {
                                            content: "";
                                            clear: both;
                                            display: table;
                                        }
                                        [class*="col-"] {
                                            float: left;
                                            padding: 15px;
                                        }
                                        .col-1 {width: 8.33%;}
                                        .col-2 {width: 16.66%;}
                                        .col-3 {width: 25%;}
                                        .col-4 {width: 33.33%;}
                                        .col-5 {width: 41.66%;}
                                        .col-6 {width: 50%;}
                                        .col-7 {width: 58.33%;}
                                        .col-8 {width: 66.66%;}
                                        .col-9 {width: 75%;}
                                        .col-10 {width: 83.33%;}
                                        .col-11 {width: 91.66%;}
                                        .col-12 {width: 100%;}
                                        * {
                                          box-sizing: border-box;
                                        }
                                      </style>
                                    </html>`);

          WinPrint.document.close();
          WinPrint.focus();
          WinPrint.print();
          WinPrint.close();
        }
        function removeIngredient(index, ingredient) {
          if (model.recipe.ingredients.length > 1 && index > -1) {
            model.recipe.ingredients.splice(index, 1);
            model.removeIngredients.push({
              ingredientId: -1,
              ingredientName: ingredient.ingredientName,
              portionAmount: ingredient.portionAmount,
              portionMeasure: ingredient.portionMeasure
            });
          }
        }
        function removeMedia(index) {
          if (model.media.length > 1 && index > -1) {
            model.media.splice(index, 1);
          }
        }
        function removeRecipeMedia(index) {
          if (model.mediaNames.length >= 0 && index > -1 && model.mediaNames[0] != "DEFAULT.JPG") {
            model.removeMedia.push(model.mediaNames[index]);
            model.mediaNames.splice(index, 1)
          }
        }
        function reviewRecipe() {
          model.readyReview = true;
        }
        function submitReview() {
          if (model.reviewComment.length > 0) {
            axios.post("/api/recipe/addreview", 
                { token: store.state.token, recipeId: model.recipe.recipeId, rating: model.rating, comments: prepareJSON(model.reviewComment) })
              .then((message) => {
                model.reviews = message.data;
                model.readyReview = false;
                model.hasReviewed = true;
                store.commit("clearError");
            });
          } else { 
            document.getElementById("comment-text").style.border = "3px solid #FF0000";
            store.commit("setError", "Please leave a review to go with the rating!");
          }
        }
        function submitUpdate() {
          store.dispatch("requiredIndication");
          store.dispatch("requiredIndicationSelect", store.state.measureUnits);
          if (store.state.error.length < 1) {
            const recipe = {
              SharedRecipe: {
                recipeName: prepareJSON(this.model.recipe.recipeName),
                recipeDescription: prepareJSON(this.model.recipe.recipeDescription),
                servingSize: model.recipe.servingSize,
                instructions: prepareJSON(this.model.recipe.instructions)
              },
              Ingredients: [],
              Token: store.state.token,
              Files: [],
              MediaRemoval: []
            };
            this.model.recipe.ingredients.forEach(ingredient => recipe.Ingredients.push({
                ingredientId: ingredient.ingredientId,
                ingredientName: prepareJSON(ingredient.ingredientName),
                portionAmount: ingredient.portionAmount,
                portionMeasure: ingredient.portionMeasure
              }));
            model.removeIngredients.forEach(ingredient => recipe.Ingredients.push({
                ingredientId: ingredient.ingredientId,
                ingredientName: prepareJSON(ingredient.ingredientName),
                portionAmount: ingredient.portionAmount,
                portionMeasure: ingredient.portionMeasure
              }));
            recipe.MediaRemoval = model.removeMedia;
            this.model.media.forEach(media => {
              if (media.file && media.file.name && media.file.name.length > 0) {
                let mediaAdd = media.file;
                if (model.removeMedia.indexOf(media.name)) {
                  model.mediaNames.push(mediaAdd.name);
                }
                recipe.Files.push({ mediaAdd })
              }
            });
            document.getElementById(this.model.mediaId).innerHTML = '';
            store.dispatch("updateRecipe", recipe);
            updateRecipeReset();
            model.removeMedia = [];
            model.media = [{ file: {} }];
          }
        }
        function updateRecipe() {
          model.updateAvailable = true;
          var nodes = document.getElementsByClassName("dis-func");
          for (var j = 0; j < nodes.length; j++) {
            nodes[j].disabled = false;
          }
          document.getElementById("update-button").disabled = true;
          document.getElementById("print-button").disabled = true;
        }
        function updateRecipeReset() {
          model.updateAvailable = false;
          var nodes = document.getElementsByClassName("dis-func");
          for (var j = 0; j < nodes.length; j++) {
            nodes[j].disabled = true;
          }
          document.getElementById("update-button").disabled = false;
          document.getElementById("print-button").disabled = false;
          if (model.mediaNames.length == 0) {
            var header = document.createElement("p");
            var text = document.createTextNode("No media was shared, but don't let that stop you for trying the recipe out!");
            header.appendChild(text);
            document.getElementById(model.mediaId).appendChild(header);
            document.getElementById(model.mediaId).parentNode.classList.remove("paginator");
          } else if (model.mediaNames.length == 1) {
            var image = document.createElement("img");
            image.src = "http://localhost:80/api/recipemedia/retrieverecipemedia/" + model.recipe.recipeId + "/" + model.mediaNames[0];
            document.getElementById(model.mediaId).appendChild(image);
          } else if (model.mediaNames.length > 0) {
            paginate(0);
          }
        }
        return {
            error: computed(() => store.state.error),
            isBusy: computed(() => store.state.isBusy),
            success: computed(() => store.state.success),
            files,
            model,
            options,
            addIngredient,
            addMedia,
            favoriteCheck,
            favoriteRecipe,
            handleFileUpload,
            paginate,
            print,
            removeIngredient,
            removeMedia,
            removeRecipeMedia,
            reviewRecipe,
            submitReview,
            submitUpdate,
            updateRecipe,
            updateRecipeReset
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
  padding-bottom: 2%;
}

#Btn-Container {
  padding-bottom: 2%;
}

.vue3-star-ratings__wrapper {
  padding: 0% !important;
}

.critic {
  display: block;
  margin: 25px auto;
  text-align: left;
}
</style>