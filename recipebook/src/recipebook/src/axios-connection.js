import axios from 'axios';

const instance = axios.create({
    baseUrl: 'http://ec2-3-134-79-22.us-east-2.compute.amazonaws.com:80'
})

export default instance;