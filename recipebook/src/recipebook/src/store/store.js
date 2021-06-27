import { createStore } from "vuex";
import axios from "../axios-connection";
import router from "../router/router";
import createPersistedState from 'vuex-persistedstate'

const store = createStore({
    plugins: [createPersistedState({
        storage: window.sessionStorage
    })],
    state: {
        error: "",
        isBusy: false,
        measureUnits: [],
        model: {},
        securityQuestions: [],
        success: "",
        token: ""
    },
    mutations: {
        clearBusy: (state) => state.isBusy = false,
        clearError: (state) => state.error = "",
        clearSuccess: (state) => state.success = "",
        clearToken: (state) => state.token = "",
        setBusy: (state) => state.isBusy = true,
        setError: (state, error) => state.error = error,
        setMeasureUnits: (state, units) => state.measureUnits = units,
        setSuccess: (state, success) => state.success = success,
        setToken: (state, responseData) => {
            state.token = responseData.accessToken;
            state.model = responseData;
        }
    },
    actions: {
        create: async ({ commit }, formData) => {
            try {
                commit("setBusy");
                commit("clearError");
                axios.get('/api/user/usernameexists', { params: { userName: formData.userName } })
                    .then((message) => {
                        if (!message.data) {
                            commit("setSuccess", "The username is available. Please wait while the account is created.");
                            setTimeout(() => {
                                axios.post('/api/user/createuser', formData)
                                .then((response) => {
                                    if (response.data) {
                                        commit("setSuccess", "The account has been created. Please click on the Sign in button, or wait to be redirected to the sign in page.");
                                        setTimeout(() => {
                                            router.push("/");
                                        }, 10000);
                                    }
                                });
                            }, 3000);
                        } else {
                            commit("setError", "Username already exists! Please enter a new one.");
                        }
                    });
            }  catch {
                commit("setError", "Failed to create account!");
            } finally {
                commit("clearBusy");
            }
        },
        getRecipeMeasures: async({ commit }) => {
            axios.get('api/measure/recipemeasures')
                .then((message) => {
                    let units = [];
                    message.data.forEach(unit => {
                        units.push(unit.measureUnit);
                    });
                    commit("setMeasureUnits", units);
                });
        },
        login: async ({ commit, dispatch }, formData) => {
            try {
                commit("setBusy");
                commit("clearError");
                axios.post('/api/user/login', formData)
                    .then((message) => {
                        if (!(message.data.accessToken.length > 0)) {
                            commit("setError", "Invalid login! Please try again.");
                        } else {
                            commit("setToken", message.data);
                            dispatch("getRecipeMeasures");
                            router.push("/home");
                        }
                    });
            }  catch {
                commit("setError", "Failed to login!");
            } finally {
                commit("clearBusy");
            }
        },
        logout: ({ commit }) => {
            commit("clearToken");
            sessionStorage.clear();
            router.push("/");
        },
        logoutURLHandler: ({ commit }) => {
            commit("clearToken");
            sessionStorage.clear();
        },
        setComponentError: ({ commit }, message) => {
            commit("setError", message);
        },
        shareRecipe: ({ commit }, recipe) => {
            commit("setBusy");
            try {
                axios.post('/api/recipe/checkuserrecipeexists', { recipeName: recipe.SharedRecipe.recipeName, token: recipe.Token })
                    .then(message => {
                        if (message.data) {
                            commit("setError", "Recipe name already created by this user!");
                        } else {
                            axios.post('/api/recipe/sharerecipe', recipe)
                                .then((shareResponse) => {
                                    commit("clearBusy");
                                    if (shareResponse.data > 0) {
                                        if (recipe.Files.length == 0) {
                                            commit("setSuccess", "Successfully shared recipe " + recipe.SharedRecipe.recipeName + "!");
                                        } else {
                                            var formData = new FormData();
                                            recipe.Files.forEach(file => {
                                                console.log(file);
                                                formData.append('files', file.mediaAdd);
                                            });
                                            const config = { headers: { 'content-type': 'multipart/form-data' } };
                                            axios.post('/api/recipemedia/uploadrecipemedia/' + shareResponse.data, formData, config)
                                                .then((uploadResponse) => {
                                                    if (uploadResponse.data === "SUCCESS") {
                                                        commit("setSuccess", "Successfully shared recipe " + recipe.SharedRecipe.recipeName + "!");
                                                    } else {
                                                        commit("setError", "Failed to upload recipe media.");
                                                    }
                                                });
                                        }
                                    } else {
                                        commit("setError", "Failed to share recipe.");
                                    } 
                                });
                        }
                    });
            }  catch {
                commit("setError", "Failed to share recipe!");
            } finally {
                commit("clearBusy");
            }
        },
        requiredIndication: ({ commit }, questions) => {
            console.log(questions);
            let check = false;
            let good = "1px solid #ced4da";
            let error = "3px solid #FF0000";
            let inputs = document.querySelectorAll('input[type=text]');
            for (let i = 0; i < inputs.length; i++) {
                if (inputs[i].value.length > 0) {
                    inputs[i].style.border = good;
                } else {
                    inputs[i].style.border = error;
                    check = true;
                }
            }
            let passwords = document.querySelectorAll('input[type=password]');
            for (let i = 0; i < passwords.length; i++) {
                if (passwords[i].value.length > 0) {
                    passwords[i].style.border = good;
                } else {
                    passwords[i].style.border = error;
                    check = true;
                }
            }
            let security = document.getElementsByTagName('input');
            for (let i = 0; i < security.length; i++) {
                if (security[i].getAttribute('placeholder') && security[i].getAttribute('placeholder') === 'Select option') {
                    security[i].style.border = error;
                    check = true;
                } else if (security[i].getAttribute('placeholder')) {
                    questions.forEach(item => {
                        if (item === security[i].getAttribute('placeholder')) {
                            security[i].style.border = good;
                        }
                    });
                }
            }
            if (check) {
                commit("setError", "Please enter all required fields!");
            } else {
                commit("clearError");
            }
            return check;
        },
        requiredIndicationSelect: ({ commit }, verifier) => {
            let check = false;
            let good = "1px solid #ced4da";
            let error = "3px solid #FF0000";
            let inputs = document.getElementsByTagName('input');
            for (let i = 0; i < inputs.length; i++) {
                if (inputs[i].getAttribute('placeholder')) {
                    check = true;
                    verifier.forEach(item => {
                        if (item === inputs[i].getAttribute('placeholder')) {
                            check = false;
                        }
                    });
                    if (check) {
                        inputs[i].style.border = error;
                    } else {
                        inputs[i].style.border = good;
                    }
                } 
            }
            if (check) {
                commit("setError", "Please enter all required fields!");
            } else {
                commit("clearError");
            }
            return check;
        }
    },
    getters: {
        isAuthenticated: (state) => {
            return state.token.length > 0;
        }
    }
});

export default store;