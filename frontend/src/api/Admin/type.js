import request from '../../utils/Js/request'

export default {
    //新增分类
    addCategory:async function(params){
        return request({
            url: '/addCategory',
            method:'post',
            data:{params}
        })
    },
    //删除分类
    deleteCategory:async function(params){
        return request({
            url: '/deleteCategory',
            method:'post',
            data:{params}
        })
    },
    //编辑分类
    editCategory:async function(params){
        return request({
            url: '/editCategory',
            method:'post',
            data:{params}
        })
    },
    //获取分类
    getCategory:async function(){
        return request({
            url: '/categoryList',
            method:'get',
            data:{}
        })
    },
}