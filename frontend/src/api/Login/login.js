import request from '../../utils/js/request'

export default {
    //登录接口
    userLogin:async function(params){
        return request({
            url: '/login',
            method:'post',
            data:{params}
        })
    },
}