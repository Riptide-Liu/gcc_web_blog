import Account from './Account/account'
import User from './Service/user'
import Type from './Service/type'
import Blog from './Service/blog'
import Comment from './Service/comment'

export default {
    //Account
    UserLogin:Account.userLogin,
    Register:Account.register,
    EditUserInfo:Account.editUserInfo,
    ChangePassword:Account.changePassword,
    //User
    GetUserList:User.getUserList,
    DisableUser:User.disableUser,
    EnableUser:User.enableUser,
    GetTodayVisitor:User.getTodayVisitor,
    GetWeekVisitor:User.getWeekVisitor,
    UpdateVisitor:User.updateVisitor,
    //Type
    AddType:Type.addType,
    DeleteType:Type.deleteType,
    EditType:Type.editType,
    GetType:Type.getType,
    GetParent:Type.getParent,
    //Blog
    AddBlog:Blog.addBlog,
    DeleteBlog:Blog.deleteBlog,
    EditBlog:Blog.editBlog,
    GetOneBlog:Blog.getOneBlog,
    GetBlogSimpleList:Blog.getBlogSimpleList,
    GetBlogList:Blog.getBlogList,
    GetTop5Blog:Blog.getTop5Blog,
    SetLike:Blog.setLike,
    DeleteLike:Blog.deleteLike,
    GetTypeBlog:Blog.getTypeBlog,
    //Comment
    AddComment:Comment.addComment,
    GetCommentsByUserId:Comment.getCommentsByUserId,
    GetCommentsByArticleId:Comment.getCommentsByArticleId,
}