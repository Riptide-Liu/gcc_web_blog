import request from '../../utils/Js/request'

export default {
    //评审状态查询（我创建的）
    queryStatus:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/review/query/status',
            method:'post',
            data:{params,token}
        })
    },
    //评审查询（我创建的）
    queryMe:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/review/query/me',
            method:'post',
            data:{params,token}
        })
    },
    //评审查询（我参与的）
    queryJoin:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/review/query/join',
            method:'post',
            data:{params,token}
        })
    },
    //评审查询（我审核的）
    queryVerifier:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/review/query/verifier',
            method:'post',
            data:{params,token}
        })
    },
    //最近访问查询
    visitedShow:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/visited/show',
            method:'post',
            data:{params,token}
        })
    },
    //最近访问记录
    visitedAdd:async function(params,token){
        return request({
            url:'/it_evaluation/backend/public/api/visited/add',
            method:'post',
            data:{params,token}
        })
    },
}