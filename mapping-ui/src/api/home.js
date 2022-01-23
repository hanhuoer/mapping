import request from '@/utils/request'

export function overview() {
    return request({
        url: '/home/overview',
        method: 'get'
    })
}

export function traffic() {
    return request({
        url: '/home/traffic',
        method: 'get'
    })
}
