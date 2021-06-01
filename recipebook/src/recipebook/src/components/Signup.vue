<template>
    <div class="vue-tempalte">
    <form>
      <h3>RecipeBook Sign Up</h3>
      <div class="form-group">
          <label>First Name</label>
          <input type="text" 
            v-model="firstName"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group">
          <label>Last Name</label>
          <input type="text" 
            v-model="lastName"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group">
          <label>Username</label>
          <input type="text" 
            v-model="userName"
            class="form-control form-control-lg" 
            required />
      </div>
      <div class="form-group">
          <label>Password</label>
          <input type="password" 
            v-model="password"
            class="form-control form-control-lg" 
            autocomplete="off" 
            required />
      </div>
      <div class="form-group">
          <label>Verify Password</label>
          <input type="password" 
            v-model="verificationPassword"
            class="form-control form-control-lg"  
            autocomplete="off" 
            required />
      </div>
      <button type="submit" @click.stop.prevent="onSubmit()" class="btn btn-dark btn-lg btn-block">Sign Up</button>
    </form>
  </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: 'signup',
        data: () => ({
            firstName: '',
            lastName: '',
            password: '',
            userId: 0,
            userName: '',
            verificationPassword: '',
            authenticated: false
        }),
         methods: {
            onSubmit() {
                // password does not match
                if (this.password !== this.verificationPassword) {
                    alert('Passwords do not match! Please re-enter your password and vefiry entry to try again.');
                    this.password = '';
                    this.verificationPassword = '';
                } else {
                    const formData = {
                        userName: this.userName,
                        password: this.password,
                        firstName: this.firstName,
                        lastName: this.lastName
                    }
                    axios.get('/api/user/usernameexists', { params: { userName: this.userName } })
                    .then((message) => {
                        if (message && !message.data) {
                          axios.post('/api/user/createuser', formData)
                            .then((response) => {
                              console.log()
                              if (response && response.data) {
                                this.userId = response.data.userId;
                                this.$router.push('/');
                              }
                            }).catch(err => { console.log(err); })
                        }
                        console.log(message);
                    }).catch(error => { console.log(error); });
                }
              }
         }
    }
</script>