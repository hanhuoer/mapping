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
