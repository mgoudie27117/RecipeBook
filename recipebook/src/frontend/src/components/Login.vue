<template>
    <div class="vue-tempalte">
        <form>
            <h3>Sign In</h3>
            <div class="form-group">
                <label>Username</label>
                <input id="un" type="text" class="form-control form-control-lg" autocomplete="off" />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input id="pw" type="password" class="form-control form-control-lg" autocomplete="off" />
            </div>
            <button v-on:click="this.loginSubmit" type="submit" class="btn btn-dark btn-lg btn-block">Sign In</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: 'login',
        data() {
            return {
            }
        },
        methods: {
            loginSubmit() {
                axios.post('/api/user/login', {
                        userName: document.getElementById('un').value,
                        password: document.getElementById('pw').value
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