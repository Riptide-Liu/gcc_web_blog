<template>
    <div class="Home">
        <a-row :gutter="30">
            <!-- 今日访客量 -->
            <a-col :span="15">
                <div class="leftBox">
                    <div class="title">
                        <span>今日访客量</span>
                    </div>
                    <div class="numBox">
                        <ICountUp
                            :delay="delay"
                            :endVal="todayVisit"
                            :options="options"
                            :immediate="true"
                            class="scoreNum"
                        />
                        <span style="font-size: 30px">人次</span>
                    </div>
                </div>
            </a-col>
            <!-- 最新评论 -->
            <a-col :span="9">
                <div class="rightBox">
                    <div class="topBox">
                        <span style="font-size: 30px">最新评论</span>
                    </div>
                    <div class="listBox">
                        <a-card 
                            v-for="item of newComment"
                            :key="item.title"
                            :title="item.title" 
                            style="margin-bottom: 15px">
                            <a slot="extra" href="#">详情</a>
                            <p>{{item.comment}}</p>
                            <p>{{item.updateTime}}</p>
                        </a-card>
                    </div>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import ICountUp from "vue-countup-v2";
    import _ from 'lodash';
    export default {
        name: "home",
        components: {ICountUp},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                delay: 1000,
                todayVisit: 777,
                options: {
                    useEasing: true,
                    useGrouping: true,
                    separator: ",",
                    decimal: ".",
                    prefix: "",
                    suffix: "",
                },

                newComment: [
                    {
                        title: "体育ABC",
                        comment: "这也太好笑了吧?哈哈哈哈哈哈哈",
                        updateTime: "2021/12/08 10:41",
                    },
                    {
                        title: "体育ABC",
                        comment: "过来看？有傻逼",
                        updateTime: "2021/12/08 10:42",
                    },
                    {
                        title: "体育ABC",
                        comment: "小编你写NM呢",
                        updateTime: "2021/12/08 10:43",
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
        }
    };
</script>

<style lang="scss" scoped>
    .Home{
        width: 100%;
        height: 100%;
        padding: 10px 20px 20px 20px;
        border-radius: 40px;
        .leftBox{
            width: 100%;
            height: 800px;
            min-height: 700px;
            background-color: white;
            border-radius: 40px;
            text-align: center; 
            .title{
                font-size: 50px; 
                padding: 50px
            }
            .numBox {
                text-align: center;
                .scoreNum{
                    font-size: 333px;
                }
            }
        }
        .rightBox{
            width: 100%;
            height: 800px;
            background-color: white;
            border-radius: 40px;
            overflow-y: auto;
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