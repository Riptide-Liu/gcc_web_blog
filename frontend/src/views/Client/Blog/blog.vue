<template>
    <div class="Blog">
        <a slot="extra" @click="goHome()">返回首页</a>
        <div class="blogBox">
            <!-- 顶部信息框 -->
            <div class="topBox">
                <div class="titleBox">
                    <p>{{title}}</p>
                </div>
                <div class="infoBox">
                    <p>{{type}}</p>
                    <p>作者：{{blogNickname}}</p>
                    <p>{{updateTime}}</p>
                </div>
            </div>
            <!-- 中部文章主体框 -->
            <div class="midBox">
                <p>{{content}}</p>
            </div>
        </div>
        <a-divider style="color: black"/>
        <!-- 底部操作框 -->
        <div class="bottomBox">
            <div class="commontBox">
                <div style="font-size: 30px; margin-bottom: 10px">
                    <span>发表评论：</span>
                </div>
                <mavon-editor
                    @save="editSaveBlog"
                    @change="editUpdateBlog"
                    ref="editor"
                    v-model="blogEditAbout">
                </mavon-editor>
            </div>
            <div class="operationBox">
                <a-button-group style="text-align: right">
                    <a-button style="border: 0px; marigin-right: 20px" shape="circle" icon="like"></a-button>
                    <a-button style="border: 0px" shape="circle" icon="star"></a-button>
                </a-button-group>
            </div>
        </div>
    </div>
</template>

<script>
    import Ajax from "../../../api/index";
    import {mavonEditor} from 'mavon-editor';
    import 'mavon-editor/dist/css/index.css';
    import _ from 'lodash';
    export default {
        name: "blog",
        components: {mavonEditor},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                blogId: 1,
                title: "如何踢进任意球",
                type: "体育",
                blogNickname: "C罗",
                content: "爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角爱神的箭阿斯利康大家啊老实交代拉屎大师登记卡上的加深对健康暗汲石底井奥斯卡连接的卡拉时间段加上来的家世界按实际刻录机阿斯卡纶大家卡视角",
                updateTime: "2021/12/13 14：11",
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
            //返回首页
            goHome(){
                this.$router.push({path: '/clientHome'})
            }
        }
    };
</script>

<style lang="scss" scoped>
    .Blog{
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
            .midBox{

            }
        }
        .bottomBox{
            background-color: white;
            padding: 10px 30px 30px 30px;
            border-radius: 40px;
        }
    } 
</style>