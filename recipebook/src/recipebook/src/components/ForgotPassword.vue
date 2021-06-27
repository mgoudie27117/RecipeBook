<template>
    <div class="vue-template">
        <div class="vertical-center">
            <div class="inner-block">
              <form novalidate @submit.prevent="onSubmit()">
                <div class="form-group-login">
                    <h3>Fogotten Password</h3>
                    <div class="alert alert-info" v-if="isBusy">Loading...</div>
                    <div class="alert alert-danger" v-if="error">{{ error }}</div>
                    <div class="alert alert-success share-entry-recipe" v-if="success">{{ success }}</div>
                </div>
                <div class="form-group form-group-login" v-if="model.userCheck">
                    <label>Please enter your username.</label>
                    <input type="text" 
                        v-model="model.userName"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <div class="form-group form-group-login" v-if="model.securityCheck">
                    <label>Security Question</label>
                    <input type="text" 
                        v-model="model.securityQuestion"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        disabled />
                </div>
                <div class="form-group form-group-login" v-if="model.securityCheck">
                    <label>Security Answer</label>
                    <input type="text" 
                        v-model="model.securityAnswer"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <div class="form-group form-group-login" v-if="model.passwordCheck">
                    <label>New Password</label>
                    <input type="password" 
                        v-model="model.newPassword"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <div class="form-group form-group-login" v-if="model.passwordCheck">
                    <label>Verify New Password</label>
                    <input type="password" 
                        v-model="model.passwordVerification"
                        class="form-control form-control-lg" 
                        autocomplete="off" 
                        required />
                </div>
                <button type="submit" class="btn btn-dark btn-lg btn-block">Submit</button>
              </form>  
            </div>
        </div>
    </div>
</template>

<script>
    import { computed, reactive } from "vue";
    import store from "../store/store";
    import axios from "../axios-connection";
    import router from "../router/router";
    export default {
        beforeMount() {
            store.dispatch('logoutURLHandler');
        },
        setup() {
            const model = reactive({ 
                userName: "", 
                securityQuestion: "",
                securityAnswer: "",
                newPassword: "",
                passwordVerification: "",
                token: "",
                userCheck: true ,
                securityCheck: false,
                passwordCheck: false
            });
            function onSubmit() {
                if (this.model.userCheck && !this.model.securityCheck && !this.model.passwordCheck) {
                    axios.get('/api/user/securityquestion/' + this.model.userName)
                        .then(message => { 
                            if (message.data && message.data.length > 1) {
                                store.commit("clearError");
                                this.model.userCheck = false;
                                this.model.securityQuestion = message.data;
                                this.model.securityCheck = true;
                            } else {
                                store.commit("setError", "Username is not recognized!");
                            }
                        });
                } else if (!this.model.userCheck && this.model.securityCheck && !this.model.passwordCheck) {
                    var formData = {
                        userName: this.model.userName,
                        securityAnswer: this.model.securityAnswer
                    }
                    axios.post('/api/user/securityanswer', formData)
                        .then(message => {
                            if (message && message.data && message.data.length > 0) {
                                store.commit("clearError");
                                this.model.token = message.data;
                                this.model.securityCheck = false;
                                this.model.passwordCheck = true;
                            } else {
                                store.commit("setError", "Incorrect security answer provided!");
                            }
                        });
                } else if (!this.model.userCheck && !this.model.securityCheck && this.model.passwordCheck) {
                    if (this.model.newPassword !== this.model.passwordVerification 
                        || this.model.newPassword.length == 0 
                        || this.model.passwordVerification.length == 0) {
                        store.commit("setError", "Please ensure that both password fields are entered and the passwords match!");
                    } else {
                        store.commit("clearError");
                        var formPassword = {
                            token: this.model.token,
                            password: this.model.newPassword
                        }
                        axios.post('/api/user/updatepassword', formPassword)
                            .then(message => {
                                if (message && message.data && message.data == "SUCCESS") {
                                    store.commit("clearError");
                                    store.commit("setSuccess", "New password has been updated! Please proceed to login or wait to be redirected.");
                                    let passwords = document.querySelectorAll('input[type=password]');
                                    for (let i = 0; i < passwords.length; i++) {
                                        passwords[i].disabled = true;
                                    }
                                    setTimeout(() => {
                                        router.push("/");
                                    }, 10000);
                                } else {
                                    store.commit("setError", "Failed to update password!");
                                }
                            });
                    }
                }
            }
            return {
                error: computed(() => store.state.error),
                isBusy: computed(() => store.state.isBusy),
                success: computed(() => store.state.success),
                model,
                onSubmit
            }
        }
    };
</script>