import request from '../../utils/Js/request'

export default {
    //新增分类
    addType:async function(params){
        return request({
            url: '/addCategory',
            method:'post',
            data:{params}
        })
    },
    //删除分类
    deleteType:async function(params){
        return request({
            url: '/deleteCategory',
            method:'post',
            data:{params}
        })
    },
    //编辑分类
    editType:async function(params){
        return request({
            url: '/editCategory',
            method:'post',
            data:{params}
        })
    },
    //获取分类
    getType:async function(){
        return request({
            url: '/categoryList',
            method:'get',
            data:{}
        })
    },
    //根据所传级别获取父分类
    getParent:async function(params){
        return request({
            url: '/topicParentLevel',
            method:'post',
            data:{params}
        })
    },
}