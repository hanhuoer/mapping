import axios from "axios"

let baseURL;

let env = process.env.NODE_ENV;
if ("development" === env) {
    baseURL = "http://127.0.0.1:18000"
} else if ("production" === env) {
    baseURL = ""
} else {
    baseURL = ""
}

const service = axios.create({
    baseURL: baseURL, timeout: 5000
});

service.interceptors.request.use(config => {
    return config;
}, error => {
    return Promise.reject(error);
});

service.interceptors.response.use(response => {
    return response.data;
}, error => {
    return Promise.reject(error);
});

export default service;
