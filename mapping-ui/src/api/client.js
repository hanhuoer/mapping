import request from '@/utils/request'

export function add(client) {
    return request({
        url: '/add',
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        },
        data: client
    })
}

export function update(client) {
    return request({
        url: '/update',
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        },
        data: client
    })
}

export function remove(id) {
    return request({
        url: '/delete',
        method: 'post',
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: {
            id: id
        }
    })
}

export function refresh() {
    return request({
        url: '/refresh',
        method: 'post'
    })
}
