<template>
    <div class="ClientHome">
        <a-row :gutter="30">
            <!-- 文章预览列表 -->
            <a-col :span="18">
                <a-spin tip="正在加载..."
                    spinning="lbSpinning">
                    <div class="leftBox">
                        <div class="blogListBox">
                            <div class="oneBlogCard"
                                v-for="item of blogListData"
                                :key="item.id">
                                <!-- 单一文章框 -->
                                <a-card 
                                    :title="item.articlePreview.title"
                                    :bordered="false"
                                    style="margin-bottom: 15px; border-radius: 20px;">
                                    <a slot="extra" @click="goOneBlog(item.articlePreview.id)">进入</a>
                                    <!-- 博文缩略 -->
                                    <div class="blogContent">
                                        <div v-html="item.articlePreview.previewContent"></div>
                                    </div>
                                    <!-- 文章信息 -->
                                    <div class="blogInfoBox">
                                        <div class="infoLeft">
                                            <p>{{item.nickname}}</p>
                                            <a-divider type="vertical" 
                                                style="color: black; margin:0 20px;"/>
                                            <p>{{item.topic}}</p>
                                        </div>
                                        <div class="infoRight">
                                            <p>{{item.articlePreview.lastUpdateTime}}</p>
                                            <a-divider type="vertical" 
                                                style="color: black; margin:0 20px;"/>
                                            <!-- 点赞操作框 -->
                                            <a-button-group style="text-align: right">
                                                <a-button 
                                                    v-if="item.isLike == false"
                                                    style="border: 0px; marigin-right: 20px" 
                                                    shape="circle"
                                                    @click="setLike(item.articlePreview.id)">
                                                    <img src="../../assets/Images/disLike.png"
                                                        style="width: 16px; height: 16px">
                                                </a-button>
                                                <a-button 
                                                    v-if="item.isLike == true"
                                                    style="border: 0px; marigin-right: 20px" 
                                                    shape="circle"
                                                    @click="deleteLike(item.articlePreview.id)">
                                                    <img src="../../assets/Images/like.png"
                                                        style="width: 16px; height: 16px">
                                                </a-button>
                                            </a-button-group>
                                        </div>
                                    </div>
                                </a-card>
                            </div>
                        </div>
                    </div>
                </a-spin>
            </a-col>
            <!-- 文章分类 -->
            <a-col :span="6">
                <div class="rightBox">
                    <!-- 热门博文框 -->
                    <a-spin tip="正在加载..."
                        spinning="rtbSpinning">
                        <div class="rightTopBox">
                            <span style="font-size: 30px">热门博文</span>
                            <!-- 单一排名列 -->
                            <div class="oneTop"
                                v-for="item of top5BlogList"
                                :key="item.rank"
                                @click="goOneBlog(item.articleId)">
                                <!-- 文章信息 -->
                                <div>
                                    <span style="font-size: 20px; color: rgb(212, 24, 0)">{{item.rank}}</span>
                                    <span>{{item.title}}</span>
                                </div>
                                <div>
                                    <span>点赞数：</span>
                                    <span>{{item.like}}</span>
                                </div>
                            </div>
                        </div>
                    </a-spin>
                    <!-- 博文分类框 -->
                    <a-spin tip="正在加载..."
                        spinning="rbbSpinning">
                        <div class="rightBottomBox">
                            <span style="font-size: 30px">博文分类</span>
                            <div class="dropdownBox">
                                <a-tree
                                    :tree-data="typeListData"
                                    :replace-fields="replaceTree"
                                    @select="getTypeBlog"/>
                            </div>
                        </div>
                    </a-spin>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name: "clienthome",
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                blogListData: [],
                top5BlogList: [],
                typeForUse: [],
                typeListData: [],
                replaceTree: {
                    title: 'topic',
                },

                lbSpinning: true,
                rtbSpinning: true,
                rbbSpinning: true,
            };
        },
        created(){
            this.getSession()
            this.getBlogList()
            this.getTop5Blog()
            this.getTypeList()
        },
        methods:{
            //存取session
            getSession(){
                //获取用户信息
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //获取博客列表
            getBlogList(){
                //制造参数
                let params = {
                    'id': this.id
                }
                Ajax.GetBlogSimpleList(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.blogListData = res.data
                        //恢复Spinning
                        this.lbSpinning = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //恢复Spinning
                        this.lbSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //跳转博客详情
            goOneBlog(id){
                //跳转界面
                this.$router.push({path: '/clientBlog'})
                //储存所查博文ID
                window.sessionStorage.setItem("nowBlogID",id)
            },
            //点赞
            setLike(aID){
                if(this.level == 0){
                    //失败提示框
                    this.$confirm({
                        okText: '前往注册',
                        cancelText: '不注册',
                        title: '游客不能执行点赞操作噢，请前往注册。',
                        onOk:() => {
                            //跳转界面
                            this.$router.push({path: '/signup'})
                        },
                    })
                }else{
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
                            this.getBlogList()
                            this.getTop5Blog()
                        }else if(res.code == 400){
                            //失败提示框
                            this.$error({
                                title: res.msg,
                            });
                            //刷新视图
                            this.getBlogList()
                            this.getTop5Blog()
                        }
                    }).catch(err =>{
                        this.$_error('错误信息：',err);
                    })
                }
            },
            //取消点赞
            deleteLike(aID){
                if(this.level == 0){
                    //失败提示框
                    this.$confirm({
                        okText: '前往注册',
                        cancelText: '不注册',
                        title: '游客不能执行点赞操作噢，请前往注册。',
                        onOk:() => {
                            //跳转界面
                            this.$router.push({path: '/signup'})
                        },
                    })
                }else{
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
                            this.getBlogList()
                            this.getTop5Blog()
                        }else if(res.code == 400){
                            //失败提示框
                            this.$error({
                                title: res.msg,
                            });
                            //刷新视图
                            this.getBlogList()
                            this.getTop5Blog()
                        }
                    }).catch(err =>{
                        this.$_error('错误信息：',err);
                    })
                }
            },
            //获取Top5博文
            getTop5Blog(){
                Ajax.GetTop5Blog().then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.top5BlogList = res.data
                        //赋值排名
                        this.top5BlogList[0].rank = 'Top1'
                        this.top5BlogList[1].rank = 'Top2'
                        this.top5BlogList[2].rank = 'Top3'
                        this.top5BlogList[3].rank = 'Top4'
                        this.top5BlogList[4].rank = 'Top5'
                        //恢复Spinning
                        this.rtbSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //获取分类列表
            getTypeList(){
                Ajax.GetType().then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.typeForUse = res.data
                        this.typeListData = res.data
                        //树形结构构造
                        this.makeTree()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //树形结构构造
            makeTree(){
                this.typeListData.forEach((item)=>{
                    delete item.children;
                });
                //将数据存储为以 id 为 KEY 的 map 索引数据列
                let map = {};
                this.typeListData.forEach((item)=>{
                    map[item.id] = item;
                });
                let tld = [];
                this.typeListData.forEach((item)=>{
                    //以当前遍历项的父id,去map对象中找到索引的id
                    let parent = map[item.parentId];
                    //如果找到索引，那么说明此项不在顶级当中,那么需要把此项添加到他对应的父级中
                    if (parent) {
                        (parent.children || ( parent.children = [] )).push(item);
                    } else {
                        //如果没有在map中找到对应的索引id,那么直接把当前的item添加到tld结果集中作为顶级
                        tld.push(item);
                    }
                });
                this.typeListData = tld
                //恢复Spinning
                this.rbbSpinning = false
            },
            //按照分类获取博文
            getTypeBlog(selectedKeys, info){
                //开启Spinning
                this.lbSpinning = true
                //构造遍历所需的值
                let nowType = info.node.title
                let nowTypeID = ''
                //从树中获取对应分类的分类ID
                for(let item of this.typeForUse){
                    if(nowType == item.topic){
                        nowTypeID = item.id
                    }
                }
                //制造参数
                let params = {
                    'userId': this.id,
                    'id': nowTypeID,
                }
                Ajax.GetTypeBlog(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.blogListData = res.data
                        //恢复Spinning
                        this.lbSpinning = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //恢复Spinning
                        this.lbSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
        }
    };
</script>

<style lang="scss" scoped>
    .ClientHome{
        width: 100%;
        height: calc(100vh - 80px);
        padding: 10px;
        .leftBox{
            width: 100%;
            height: 800px;
            padding: 10px;
            overflow-y: auto;
            .oneBlogCard{
                border-radius: 20px;
                .blogInfoBox{
                    display: flex;
                    align-items: baseline;
                    justify-content: space-between;
                    .infoLeft{
                        display: flex;
                        align-items: baseline;
                        justify-content: center;
                    }
                    .infoRight{
                        display: flex;
                        align-items: baseline;
                        justify-content: center;
                    }
                }
            }
        }
        .rightBox{
            width: 100%;
            height: 800px;
            border-radius: 20px;
            .rightTopBox{
                height: 30%;
                margin-bottom: 20px;
                padding: 20px 30px;
                border-radius: 20px;
                background-color: white;
                .oneTop{
                    display: flex;
                    justify-content: space-between;
                }
            }
            .rightBottomBox{
                height: 65%;
                padding: 20px 30px;
                border-radius: 20px;
                background-color: white;
                .dropdownBox{
                    margin-top: 20px;
                }
            }
        }
        .ant-menu .ant-menu-inline .ant-menu-root .ant-menu-light{
            height: 400px;
            overflow-y: auto !important;
        }
    } 
</style>