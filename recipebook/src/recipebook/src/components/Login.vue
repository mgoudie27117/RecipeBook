<template>
    <div class="vue-tempalte">
        <form>
            <h3>Sign In</h3>
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
            <button v-on:click="onSubmit" type="submit" class="btn btn-dark btn-lg btn-block">Sign In</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios'
    export default {
        name: 'login',
        data() {
            return {
                firstName: '',
                lastName: '',
                password: '',
                userId: '',
                userName: ''
            }
        },
        methods: {
            onSubmit() {
                axios.post('/api/user/login', {
                        userName: this.userName,
                        password: this.password
                    }, { timeout: 10000 }).then((message) => {
                        console.log(message.data);
                    }).catch(error => {
                        console.log('Error', error);
                    });
                this.$router.push('/landing');
            }
        },
    }    
</script>