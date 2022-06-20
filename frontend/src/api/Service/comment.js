import request from '../../utils/Js/request'

export default {
    //发表评论
    addComment:async function(params){
        return request({
            url:'/comment',
            method:'post',
            data:{params}
        })
    },
    //通过UserID获取评论
    getCommentsByUserId:async function(params){
        return request({
            url:'/getCommentsByUserId',
            method:'post',
            data:{params}
        })
    },
    //通过博文ID获取评论
    getCommentsByArticleId:async function(params){
        return request({
            url:'/getCommentsByArticleId',
            method:'post',
            data:{params}
        })
    }
}