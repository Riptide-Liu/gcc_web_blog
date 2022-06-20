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
    //注册接口
    register:async function(params){
        return request({
            url: '/register',
            method:'post',
            data:{params}
        })
    },
    //编辑用户
    editUserInfo:async function(params){
        return request({
            url: '/editUserInfo',
            method:'post',
            data:{params}
        })
    },
    //修改密码
    changePassword:async function(params){
        return request({
            url: '/changePassword',
            method:'post',
            data:{params}
        })
    },
}