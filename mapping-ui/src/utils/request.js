import axios from "axios";
import {Message} from 'element-ui';
import router from '../router';
import {getToken, removeToken} from "./auth";

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
    config.headers['Authorization'] = getToken();
    return config;
}, error => {
    return Promise.reject(error);
});

service.interceptors.response.use(response => {
    return response.data;
}, error => {
    try {
        let status = error.response.status;
        if (status >= 200 && status < 500) {
            let code = error.response.data.code;
            if (code === "40001"
                || code === "40002"
                || code === "40003"
                || code === "40004"
                || code === "40005"
                || code === "40006"
            ) {
                Message.warning("login");
                removeToken();
                router.push("/login");
            } else {
                return Promise.reject(error.response.data);
            }
        } else {
            return Promise.reject(error.response.data);
        }
    } catch (e) {
        Message.error(e || "Has error");
        return Promise.reject(error.response.data);
    }
});

export default service;
