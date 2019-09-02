import request from '../utils/request';

export const login = (query) => {
    return request({
        url: '/user/login',
        method: 'post',
        data: query
    })
}
