<template>
    <div class="vue-template">
      <div class="vertical-center">
        <div class="inner-block">
          <form novalidate @submit.prevent="onSubmit()">
            <div class="form-group-signup">
              <h3>RecipeBook Sign Up</h3>
              <div class="alert alert-info" v-if="isBusy">Loading...</div>
              <div class="alert alert-danger" v-if="error">{{ error }}</div>
              <div class="alert alert-success" v-if="success">{{ success }}</div>
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

            <div class="form-group form-group-signup">
                <label>Security Question</label>
                <div id="security-selector">
                  <vue-select 
                    :options="security.questions"
                    v-model="model.securityQuestion"
                    :close-on-select="true" />
                </div>
            </div>
            <div class="form-group form-group-signup">
                <label>Security Answer</label>
                <input type="text" 
                  v-model="model.securityAnswer"
                  placeholder="Enter your security question answer"
                  class="form-control form-control-lg" 
                  required />
            </div>
            <button type="submit" @click.stop.prevent="onSubmit()" class="btn btn-dark btn-lg btn-block">Sign Up</button>
          </form>
        </div>
      </div>
  </div>
</template>

<script>
    import { computed, reactive } from "vue";
    import store from "../store/store";
    import axios from "../axios-connection";
    export default {
      beforeMount() {
            store.dispatch('logoutURLHandler');
            axios.get('/api/user/securityquestions').then(message => { this.security.questions = message.data; });
        },
        setup() {
            const security = reactive({ questions: [] });
            const model = reactive({ userName: "", 
                                      password: "", 
                                      verificationPassword: "", 
                                      firstName: "", 
                                      lastName: "",
                                      securityQuestion: "",
                                      securityAnswer: ""
                                    });
            function onSubmit() {
                store.dispatch("requiredIndication", security.questions);
                if (model.verificationPassword === model.password) {
                  if ((model.userName.length > 0) &&
                    (model.password.length > 0) &&
                    (model.firstName.length > 0) &&
                    (model.lastName.length > 0) && 
                    (model.securityQuestion.length > 0) &&
                    (model.securityAnswer.length > 0)) {
                      store.dispatch("create", model);
                    }
                } else {
                    store.dispatch("setComponentError", "Passwords do not Match!");
                    model.verificationPassword,model.password = "";
                }
            }
            return {
                success: computed(() => store.state.success),
                error: computed(() => store.state.error),
                isBusy: computed(() => store.state.isBusy),
                model,
                security,
                onSubmit
            }
        }
    };
</script>

<style> 
#security-selector div.vue-select {
  min-height: calc(1.5em + 1rem + 2px);
  padding: .5rem 1rem;
  font-size: 1.25rem;
  border-radius: .3rem;
  border-color: #ced4da !important;
  width: 100%;
}
</style>