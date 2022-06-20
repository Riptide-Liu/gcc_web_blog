import request from '../../utils/Js/request'

export default {
    //获取用户列表
    getUserList:async function(){
        return request({
            url: '/userList',
            method:'get',
            data:{}
        })
    },
    //封禁用户
    disableUser:async function(params){
        return request({
            url: '/disableUser',
            method:'post',
            data:{params}
        })
    },
    //解封用户
    enableUser:async function(params){
        return request({
            url: '/enableUser',
            method:'post',
            data:{params}
        })
    },
    //获取今日访客
    getTodayVisitor:async function(params){
        return request({
            url: '/getTodayVisitor',
            method:'post',
            data:{params}
        })
    },
    //获取近7天访客
    getWeekVisitor:async function(params){
        return request({
            url: '/getOneWeekVisitor',
            method:'post',
            data:{params}
        })
    },
    //记载访客数量
    updateVisitor:async function(params){
        return request({
            url: '/updateVisitor',
            method:'post',
            data:{params}
        })
    },
}