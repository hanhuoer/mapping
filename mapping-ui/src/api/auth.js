import request from '@/utils/request';
import qs from 'qs';

export function signIn(username, password) {
    let formData = qs.stringify({
        username: username,
        password: password
    });
    return request({
        url: '/auth/sign/in',
        method: 'post',
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: formData
    })
}

export async function refresh() {
    return request({
        url: '/auth/refresh',
        method: 'post'
    })
}

export function getRefreshLeastSeconds() {
    return request({
        url: '/auth/get/refresh/least/seconds',
        method: 'get'
    })
}

export function getExpireAt() {
    return request({
        url: '/auth/get/expire/at',
        method: 'get'
    })
}
