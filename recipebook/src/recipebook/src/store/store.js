import { createStore } from "vuex";
import axios from "../axios-connection";
import router from "../router/router";

const store = createStore({
    state: {
        error: "",
        isBusy: false,
        modal: true,
        modalMessage: "",
        model: {},
        token: ""
    },
    mutations: {
        clearBusy: (state) => state.isBusy = false,
        clearError: (state) => state.error = "",
        clearToken: (state) => {
            state.token = "";
        },
        setBusy: (state) => state.isBusy = true,
        setError: (state, error) => state.error = error,
        setModal: (state, setter) => state.modal = setter,
        setModalMessage: (state, message) => state.modalMessage = message,
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
                            axios.post('/api/user/createuser', formData)
                                .then((response) => {
                                    if (response.data) {
                                        console.log('Set modal to true.')
                                        commit("setModal", true);
                                        commit("setModalMessage", "Account created. Please login to enjoy RecipeBook!")
                                        setTimeout(() => {
                                            console.log('Redirecting!')
                                            router.push("/");
                                        }, 5000);
                                    }
                                });
                        } else {
                            commit("setError", "Username already exists!");
                        }
                    });
            }  catch {
                commit("setError", "Failed to create account!");
            } finally {
                commit("clearBusy");
            }
        },
        login: async ({ commit }, formData) => {
            try {
                commit("setBusy");
                commit("clearError");
                axios.post('/api/user/login', formData)
                    .then((message) => {
                        if (!(message.data.accessToken.length > 0)) {
                            commit("setError", "Invalid login! Please try again.");
                        } else {
                            commit("setToken", message.data);
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
            router.push("/");
        },
        setComponentError: ({ commit }, message) => {
            commit("setError", message);
        },
        requiredIndication: ({ commit }) => {
            let good = "1px solid #ced4da";
            let error = "3px solid #FF0000";
            let inputs = document.getElementsByTagName('input');
            for (let i = 0; i < inputs.length; i++) {
                if (inputs[i].value.length > 0) {
                    inputs[i].style.border = good;
                } else {
                    inputs[i].style.border = error;
                }
            }
            commit("setError", "Please enter all required fields!");
        }
    },
    getters: {
        activeModal: (state) => {
            return state.modal;
        },
        isAuthenticated: (state) => {
            return state.token.length > 0;
        }
    }
});

export default store;