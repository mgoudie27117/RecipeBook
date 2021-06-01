<template>
    <div class="vue-tempalte">
        <form>
            <h3>RecipeBook Sign In</h3>
            <div class="form-group">
                <label>Username</label>
                <input type="text" 
                    id="userName"
                    v-model="userName"
                    class="form-control form-control-lg" 
                    autocomplete="off" />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" 
                    id="password"
                    v-model="password"
                    class="form-control form-control-lg" 
                    autocomplete="off" />
            </div>
            <button type="submit" @click.stop.prevent="onSubmit()" class="btn btn-dark btn-lg btn-block">Sign In</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: 'login',
        data: () => ({
                firstName: '',
                lastName: '',
                password: '',
                userId: '',
                userName: '',
                authenticated: false
        }),
        methods: {
            onSubmit() {
                const formData = {
                    userName: this.userName,
                    password: this.password
                }
                axios.post('/api/user/login', formData)
                    .then((message) => {
                        console.log(message.data);
                        if (message && message.data && message.data.authenticated) {
                            this.authenticated = message.data.authenticated;
                            this.firstName = message.data.firstName;
                            this.lastName = message.data.lastName;
                            this.userId = message.data.userId;
                            this.$router.push('/landing');
                        }
                    }).catch(error => { console.log(error); });
            }
        }
    }    
</script>