<template>
    <div class="vue-template">
        <div class="vertical-center">
            <div class="inner-block">
              <form novalidate @submit.prevent="onSubmit()">
                <div class="form-group-login">
                    <h3>RecipeBook Sign In</h3>
                    <div class="alert alert-info" v-if="isBusy">Loading...</div>
                    <div class="alert alert-danger" v-if="error">{{ error }}</div>
                </div>
                <div class="form-group form-group-login">
                    <label>Username</label>
                    <input type="text" 
                        v-model="model.userName"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <div class="form-group form-group-login">
                    <label>Password</label>
                    <input type="password" 
                        v-model="model.password"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <button type="submit" class="btn btn-dark btn-lg btn-block">Sign In</button>
              </form>  
            </div>
        </div>
    </div>
</template>

<script>
    import { computed, reactive } from "vue";
    import store from "../store/store";
    export default {
        setup() {
            const model = reactive({ userName: "", password: "" });
            function onSubmit() {
                store.dispatch("requiredIndication");
                if (model.userName.length > 0 && model.password.length > 0) {
                    store.dispatch("login", model);
                }
            }
            return {
                error: computed(() => store.state.error),
                isBusy: computed(() => store.state.isBusy),
                model,
                onSubmit
            }
        }
    };
</script>