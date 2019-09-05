import request from '../utils/request';

export const fetchData = (query) => {
    return request({
        url: '/user/list',
        method: 'post',
        data: query
    })
}
