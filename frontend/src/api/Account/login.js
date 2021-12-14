import request from '../../utils/Js/request'

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