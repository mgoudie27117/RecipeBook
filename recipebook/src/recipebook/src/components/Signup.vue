<template>
    <div class="vue-tempalte">
    <form novalidate @submit.prevent="onSubmit()">
      <div class="form-group-signup">
        <h3>RecipeBook Sign Up</h3>
        <div class="alert alert-info" v-if="isBusy">Loading...</div>
        <div class="alert alert-danger" v-if="error">{{ error }}</div>
      </div>
      <div class="form-group form-group-signup">
          <label>First Name</label>
          <input type="text" 
            v-model="model.firstName"
            placeholder="Enter your first name"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group form-group-signup">
          <label>Last Name</label>
          <input type="text" 
            v-model="model.lastName"
            placeholder="Enter your last name"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group form-group-signup">
          <label>Username</label>
          <input type="text" 
            v-model="model.userName"
            placeholder="Enter your chosen username"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group form-group-signup">
          <label>Password</label>
          <input type="password" 
            v-model="model.password"
            placeholder="Enter your chosen password"
            class="form-control form-control-lg" 
            autocomplete="off" 
            required />
      </div>
      <div class="form-group form-group-signup">
          <label>Verify Password</label>
          <input type="password" 
            v-model="model.verificationPassword"
            placeholder="Re-enter your chosen password"
            class="form-control form-control-lg"  
            autocomplete="off" 
            required />
      </div>
      <button type="submit" @click.stop.prevent="onSubmit()" class="btn btn-dark btn-lg btn-block">Sign Up</button>
    </form>
  </div>
</template>

<script>
    import { computed, reactive } from "vue";
    import store from "../store/store";
    export default {
        setup() {
            const model = reactive({ userName: "", password: "", verificationPassword: "", firstName: "", lastName: "" });
            function onSubmit() {
                store.dispatch("requiredIndication");
                if (model.verificationPassword === model.password) {
                  if ((model.userName.length > 0) &&
                    (model.password.length > 0) &&
                    (model.firstName.length > 0) &&
                    (model.lastName.length > 0)) {
                      store.dispatch("create", model);
                    }
                } else {
                    store.dispatch("setComponentError", "Passwords do not Match!");
                    model.verificationPassword = "";
                    model.password = "";
                }
            }
            return {
                closeModal: () => store.commit("setModal", false),
                modal: computed(() => store.state.modal),
                error: computed(() => store.state.error),
                isBusy: computed(() => store.state.isBusy),
                model,
                onSubmit,
                showModal: () => store.commit("setModal", true)
            }
        }
    };
</script>