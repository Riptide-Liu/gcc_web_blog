<template>
    <div class="ClientBlog">
        <a slot="extra" @click="goHome()">返回首页</a>
        <!-- 顶部博文框 -->
        <a-spin tip="正在加载..."
            spinning="bbSpinning">
            <div class="blogBox">
                <!-- 顶部信息框 -->
                <div class="topBox">
                    <!-- 标题框 -->
                    <div class="titleBox">
                        <p>{{title}}</p>
                    </div>
                    <!-- 文章信息框 -->
                    <div class="infoBox">
                        <p>分类：{{type}}</p>
                        <p>作者：{{blogNickname}}</p>
                        <p>更新日期：{{updateTime}}</p>
                    </div>
                </div>
                <!-- 中部文章主体框 -->
                <div class="midBox">
                    <div v-html="content"></div>
                </div>
            </div>
        </a-spin>
        <a-divider style="color: black"/>
        <!-- 底部操作框 -->
        <div class="bottomBox">
            <!-- 评论列表框 -->
            <a-spin tip="正在加载..."
                spinning="cbSpinning">
                <div class="commontListBox">
                    <div style="font-size: 30px; margin-bottom: 10px">
                        <span>评论列表</span>
                        <div style="text-align: left; font-size: 14px">
                            <span v-if="commentList.length == 0">当前暂无评论</span>
                        </div>
                    </div>
                    <a-timeline>
                        <a-timeline-item v-for="item of commentList" :key="item.id">
                            <a-card style="width: 300px">
                                <div v-html="item.content"></div>
                                <div class="commentInfoText">
                                    <p>{{item.username || item.nickname}}</p>
                                    <div style="text-align: right">
                                        <p>{{item.createTime}}</p>
                                    </div>
                                </div>
                            </a-card>
                        </a-timeline-item>
                    </a-timeline>
                </div>
            </a-spin>
            <!-- 评论编辑框 -->
            <div class="commontBox">
                <div style="font-size: 30px; margin-bottom: 10px">
                    <span>发表评论</span>
                </div>
                <mavon-editor
                    style="z-index: 1"
                    @save="saveComment"
                    @change="updateComment"
                    ref="editor"
                    v-model="commentAbout">
                </mavon-editor>
            </div>
            <!-- 操作按钮框 -->
            <div class="operationBox">
                <a-button-group style="text-align: right">
                    <a-button 
                        v-if="isLike == 0"
                        style="border: 0px; marigin-right: 20px" 
                        shape="circle"
                        @click="setLike(blogId)">
                        <img src="../../assets/Images/disLike.png"
                            style="width: 16px; height: 16px">
                    </a-button>
                    <a-button 
                        v-if="isLike == 1"
                        style="border: 0px; marigin-right: 20px" 
                        shape="circle"
                        @click="deleteLike(blogId)">
                        <img src="../../assets/Images/like.png"
                            style="width: 16px; height: 16px">
                    </a-button>
                </a-button-group>
                <a-button
                    type="primary"
                    :loading="addLoading"
                    @click="addComment()">
                    发表
                </a-button>
            </div>
        </div>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import {mavonEditor} from 'mavon-editor';
    import 'mavon-editor/dist/css/index.css';
    export default {
        name: "clientblog",
        components: {mavonEditor},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                blogId: '',
                title: '',
                type: '',
                blogNickname: '',
                blogUserID: '',
                content: '',
                updateTime: '',
                isLike: '',

                commentDraft: '',
                commentAbout: '',
                commentAboutHtml: '',
                commentList: [],

                bbSpinning: true,
                cbSpinning: true,
                addLoading: false,
            };
        },
        created(){
            this.getSession()
            this.getBlogInfo()
            this.getBlogComment()
        },
        methods:{
            //存取session
            getSession(){
                //获取用户信息
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
                //获取当前文章ID
                this.blogId = window.sessionStorage.getItem("nowBlogID")
            },
            //获取当前博文基本信息
            getBlogInfo(){
                //制造参数
                let params = {
                    "id": this.blogId,
                };
                Ajax.GetOneBlog(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.title = res.data.article.title
                        this.type = res.data.topic
                        this.blogNickname = res.data.nickname
                        this.blogUserID = res.data.article.userID
                        this.content = res.data.article.content
                        this.updateTime = res.data.article.updateTime
                        this.isLike = res.data.article.isLike
                        //记载访客数量
                        this.updateVisitor()
                        //恢复Spinning
                        this.bbSpinning = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                        //恢复Spinning
                        this.bbSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //获取当前博文评论列表
            getBlogComment(){
                //制造参数
                let params = {
                    "articleID": this.blogId,
                };
                Ajax.GetCommentsByArticleId(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.commentList = res.data
                        //恢复Spinning
                        this.cbSpinning = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                        //恢复Spinning
                        this.cbSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //记录访客数量
            updateVisitor(){
                //制造参数
                let params = {
                    "userID": this.blogUserID,
                };
                Ajax.UpdateVisitor(params).then(res =>{
                    this.$_info('成功返回：',res);
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //返回首页
            goHome(){
                this.$router.push({path: '/clientHome'})
            },
            //评论保存草稿
            saveComment(markdown, html){
                console.log("Html:",html);
                this.commentDraft = markdown
                window.localStorage.setItem('commentDraft',markdown)
                //成功提示框
                this.$message.success({
                    content: '草稿保存成功',
                });
            },
            //评论动态更新
            updateComment(markdown, html){
                console.log("Markdown:",markdown);
                console.log("Html:",html);
                this.commentAboutHtml = html
            },
            //发表评论
            addComment(){
                //制造参数
                let params = {
                    "articleID": this.blogId,
                    "userID": this.id,
                    "content": this.commentAboutHtml,
                };
                Ajax.AddComment(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.data,
                        });
                        //刷新视图
                        this.getBlogComment()
                        //归零输入框
                        this.commentAbout = ''
                        //删除草稿箱
                        window.localStorage.removeItem('commentDraft')
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getBlogComment()
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //点赞
            setLike(aID){
                //制造参数
                let params = {
                    'userID': this.id,
                    'articleID': aID,
                }
                Ajax.SetLike(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$message.success({
                            content: '点赞成功',
                        });
                        //刷新视图
                        this.getBlogInfo()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getBlogInfo()
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //取消点赞
            deleteLike(aID){
                //制造参数
                let params = {
                    'userID': this.id,
                    'articleID': aID,
                }
                Ajax.DeleteLike(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$message.success({
                            content: '取消点赞成功',
                        });
                        //刷新视图
                        this.getBlogInfo()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getBlogInfo()
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
        }
    };
</script>

<style lang="scss" scoped>
    .ClientBlog{
        width: 100%;
        height: 800px;
        // background-color: white;;
        overflow-y: auto;
        .blogBox{
            background-color: white;
            padding: 10px 30px 30px 30px;
            border-radius: 40px;
            .topBox{
                .titleBox{
                    font-size: 40px;
                    text-align: center;
                }
                .infoBox{
                    display: flex;
                    justify-content: space-between;
                }
            }
        }
        .bottomBox{
            background-color: white;
            padding: 10px 30px 30px 30px;
            border-radius: 40px;
            .commentListBox{
                .commentInfoText{
                    display: flex;
                    justify-content: space-between;
                }
            }
            .operationBox{
                margin-top: 20px;
                display: flex;
                justify-content: space-between;
            }
        }
    } 
</style>