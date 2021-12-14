<template>
    <div class="Home">
        <a-row :gutter="30">
            <!-- 文章预览列表 -->
            <a-col :span="18">
                <div class="leftBox">
                    <div class="blogListBox">
                        <div class="oneBlogCard"
                            v-for="item of blogList"
                            :key="item.id">
                            <a-card 
                                :title="item.title"
                                :bordered="false"
                                style="margin-bottom: 15px; border-radius: 20px;">
                                <a slot="extra" @click="goOneBlog(item.id)">进入</a>
                                <!-- 博文缩略 -->
                                <div class="blogContent">
                                    <p>{{item.content}}</p>
                                </div>
                                <!-- 文章信息 -->
                                <div class="blogInfoBox">
                                    <div class="infoLeft">
                                        <p>{{item.nickname}}</p>
                                        <a-divider type="vertical" 
                                            style="color: black; margin:0 20px;"/>
                                        <p>{{item.type}}</p>
                                    </div>
                                    <div class="infoRight">
                                        <p>{{item.updateTime}}</p>
                                        <a-divider type="vertical" 
                                            style="color: black; margin:0 20px;"/>
                                        <a-button-group style="text-align: right">
                                            <a-button style="border: 0px; marigin-right: 20px" shape="circle" icon="like"></a-button>
                                            <a-button style="border: 0px" shape="circle" icon="star"></a-button>
                                        </a-button-group>
                                    </div>
                                </div>
                            </a-card>
                        </div>
                    </div>
                </div>
            </a-col>
            <!-- 文章分类 -->
            <a-col :span="6">
                <div class="rightBox">
                    <div class="topBox">
                        <span style="font-size: 30px">文章分类</span>
                    </div>
                    <div class="typeListBox">
                        
                    </div>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import _ from 'lodash';
    export default {
        name: "home",
        components: {},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                blogList: [
                    {
                        id: 1,
                        title: "如何踢进任意球",
                        type: "体育",
                        nickname: "C罗",
                        content: "爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角",
                        updateTime: "2021/12/13 14：11",
                    },
                    {
                        id: 2,
                        title: "如何踢进任意球",
                        type: "体育",
                        nickname: "C罗",
                        content: "爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角",
                        updateTime: "2021/12/13 14：11",
                    },
                    {
                        id: 3,
                        title: "如何踢进任意球",
                        type: "体育",
                        nickname: "C罗",
                        content: "爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角",
                        updateTime: "2021/12/13 14：11",
                    },
                    {
                        id: 4,
                        title: "如何踢进任意球",
                        type: "体育",
                        nickname: "C罗",
                        content: "爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角",
                        updateTime: "2021/12/13 14：11",
                    },
                ],

                typeList: [
                    {
                        tId: '1', 
                        typeName: '体育',
                        tLevel: '1', 
                        parentType: '无',
                        parentId: '0', 
                    },
                    {
                        tId: '7', 
                        typeName: '政治',
                        tLevel: '1', 
                        parentType: '无',
                        parentId: '0', 
                    },
                    {
                        tId: '3', 
                        typeName: '855',
                        tLevel: '1', 
                        parentType: '无',
                        parentId: '0', 
                    },
                ],
            };
        },
        computed:{

        },
        created(){
            this.getSession()
        },
        mounted(){
            
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userinfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
                console.log('userinfo:',this,{username,nickname,email,id,level,enable})
            },
            //跳转博客详情
            goOneBlog(id){
                //制造参数
                let params = {
                    "id": id,
                };
                console.log("params:",params)
                this.$router.push({path: '/clientBlog'})
                // Ajax.UserLogin(params).then(res =>{
                //     this.$_info('成功返回：',res);
                //     console.log("res:",res)
                //     if(res.code == 200){
                //         //存储博客信息
                //         window.sessionStorage.setItem('blogInfo',res.data)
                //         //跳转路由
                //         this.$router.push({path: '/clientBlog'})
                //     }else if(res.code == '400'){
                //         //失败提示框
                //         this.$error({
                //             title: res.data,
                //         });
                //         //恢复loading
                //         this.setLoading = false;
                //     }
                // }).catch(err =>{
                //     this.$_error('错误信息：',err);
                //     console.log("err:",err)
                // })
            },
        }
    };
</script>

<style lang="scss" scoped>
    .Home{
        width: 100%;
        height: calc(100vh - 80px);
        padding: 10px;
        .leftBox{
            width: 100%;
            height: 800px;
            // background-color: white;
            padding: 10px;
            overflow-y: auto;
            .oneBlogCard{
                border-radius: 20px;
                .blogInfoBox{
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    .infoLeft{
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }
                    .infoRight{
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }
                }
            }
        }
        .rightBox{
            width: 100%;
            height: 800px;
            background-color: white;
            border-radius: 20px;
            .topBox{
                display: flex;
                justify-content: space-between;
                padding: 20px 30px;
            }
            .listBox{
                padding: 20px 30px;
            }
        }
    } 
</style>