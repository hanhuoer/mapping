import request from '@/utils/request'
import qs from 'qs';

export function list() {
    return request({
        url: '/client/list',
        method: 'get'
    })
}

export function add(client) {
    return request({
        url: '/client/add',
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        },
        data: client
    })
}

export function update(client) {
    return request({
        url: '/client/update',
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        },
        data: client
    })
}

export function remove(id) {
    let formData = qs.stringify({
        id: id
    });
    return request({
        url: '/client/delete',
        method: 'post',
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: formData
    })
}

export function refresh() {
    return request({
        url: '/client/refresh',
        method: 'post'
    })
}
