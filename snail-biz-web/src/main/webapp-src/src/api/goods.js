import request from '../utils/request';

export const fetchGoodsList = (query) => {
    return request({
        url: '/goods/list',
        method: 'post',
        data: query
    })
}
