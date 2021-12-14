import request from '../../utils/Js/request'

export default {
    //获取所有项目
    getAllProject:async function(params,token){
        return request({
            url:'/timers/backend/public/api/time/name',
            method:'post',
            data:{params,token}
        })
    },
    //获取所有成员信息
    getFendaAll:async function(params,token){
        return request({
            url:'/commonlib/public/api/get/userlist',
            method:'post',
            data:{params,token}
        })
    },
    //获取所有组别信息
    getGroupAll:async function(params){
        return request({
            url:'/sharesys/backend/public/api/get/group',
            method:'post',
            data:{params}
        })
    },
    //通过工号批量获取用户信息
    getUserByWorkid:async function(params){
        return request({
            url:'/commonlib/public/api/getUserByWorkid',
            method:'post',
            data:{params}
        })
    },
    //通过catid批量获取组长信息
    getUserByCatid:async function(params){
        return request({
            url:'/commonlib/public/api/getUserByCatid',
            method:'post',
            data:{params}
        })
    },
    //资源上传
    uploadFile:async function(params,token){
        return request({
            url:'/commonlib/public/api/resource',
            method:'post',
            data:{params,token}
        })
    },
}