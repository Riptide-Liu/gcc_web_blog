import request from '../../utils/Js/request'

export default {
    //新增博文
    addBlog:async function(params){
        return request({
            url: '/publish',
            method:'post',
            data:{params}
        })
    },
    //删除博文
    deleteBlog:async function(params){
        return request({
            url: '/deleteArticle',
            method:'post',
            data:{params}
        })
    },
    //编辑博文
    editBlog:async function(params){
        return request({
            url: '/alterArticle',
            method:'post',
            data:{params}
        })
    },
    //获取某一博文
    getOneBlog:async function(params){
        return request({
            url: '/getArticle',
            method:'post',
            data:{params}
        })
    },
    //获取博文列表（内容摘要）
    getBlogSimpleList:async function(params){
        return request({
            url: '/getArticleList',
            method:'post',
            data:{params}
        })
    },
    //获取博文列表
    getBlogList:async function(params){
        return request({
            url: '/getUserArticle',
            method:'post',
            data:{params}
        })
    },
    //获取Top5博文
    getTop5Blog:async function(){
        return request({
            url: '/getTop5Article',
            method:'get',
            data:{}
        })
    },
    //点赞
    setLike:async function(params){
        return request({
            url: '/like',
            method:'post',
            data:{params}
        })
    },
    //取消点赞
    deleteLike:async function(params){
        return request({
            url: '/revokeLike',
            method:'post',
            data:{params}
        })
    },
    //根据分类获取对应博文
    getTypeBlog:async function(params){
        return request({
            url: '/getArticleByCategory',
            method:'post',
            data:{params}
        })
    },
}