<template>
    <div class="AdminHome">
        <a-row :gutter="30">
            <!-- 今日访客量 -->
            <a-col :span="15">
                <div class="leftBox">
                    <!-- 日期选择按钮 -->
                    <div class="buttonBox">
                        <a-button-group style="text-align: right">
                            <!-- 当日 -->
                            <a-button style="border: 0px; marigin-right: 20px"
                                @click="changeTimeBox('day')">
                                <img src="../../assets/Images/day.png" 
                                    style="width: 20px; height: 20px">
                            </a-button>
                            <!-- 近七日 -->
                            <a-button style="border: 0px"
                                @click="changeTimeBox('week')">
                                <img src="../../assets/Images/week.png" 
                                    style="width: 20px; height: 20px">
                            </a-button>
                        </a-button-group>
                    </div>
                    <!-- 当日框 -->
                    <div class="dayBox" v-if="nowBox == 'day'">
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
                    <!-- 近七日框 -->
                    <div class="weekBox" v-else-if="nowBox == 'week'">
                        <div class="title">
                            <span>近7日访客量</span>
                        </div>
                        <div class="chartsBox">
                            <div id="visitorChart" style="text-align: center; width: 100%; height: 600px"></div>
                        </div>
                    </div>
                </div>
            </a-col>
            <!-- 最新评论 -->
            <a-col :span="9">
                <div class="rightBox">
                    <div class="topBox">
                        <span style="font-size: 30px">最新评论</span>
                    </div>
                    <!-- 最新评论列表 -->
                    <a-spin tip="正在加载..."
                        spinning="nclSpinning">
                        <div class="listBox">
                            <div style="text-align: center">
                                <span v-if="newComment.length == 0">当前暂无评论</span>
                            </div>
                            <!-- 单条评论 -->
                            <a-card 
                                v-for="item of newComment"
                                :key="item.id"
                                :title="item.articleTitle" 
                                style="margin-bottom: 15px">
                                <a slot="extra" @click="goOneBlog(item.articleId)">详情</a>
                                <div v-html="item.content"></div>
                                <p>{{item.createTime}}</p>
                            </a-card>
                        </div>
                    </a-spin>
                </div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import ICountUp from "vue-countup-v2";
    import echarts from 'echarts'
    export default {
        name: "adminhome",
        components: {ICountUp},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                nowBox: 'day',

                delay: 1000,
                options: {
                    useEasing: true,
                    useGrouping: true,
                    separator: ",",
                    decimal: ".",
                    prefix: "",
                    suffix: "",
                },
                
                todayVisit: 0,
                weekVisit: [],

                newComment: [],
                nclSpinning: true
            };
        },
        created(){
            this.getSession()
            this.getTodayVisitor()
            this.getNewComment()
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //切换日期
            changeTimeBox(time){
                if(time == 'day'){
                    this.nowBox = 'day'
                    this.getTodayVisitor()
                }else if(time == 'week'){
                    this.nowBox = 'week'
                    this.getWeekVisitor()
                }
            },
            //获取今日总访客量
            getTodayVisitor(){
                //制造参数
                let params = {
                    "userID": this.id,
                };
                Ajax.GetTodayVisitor(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.todayVisit = res.data
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //获取近7日总访客量
            getWeekVisitor(){
                //制造参数
                let params = {
                    "userID": this.id,
                };
                Ajax.GetWeekVisitor(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.weekVisit = res.data
                        //构造图表
                        this.makeChart()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //构造图表
            makeChart(){
                let chartData = this.weekVisit
                let option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            label: {
                                backgroundColor: '#6a7985'
                            }
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    yAxis:{
                        type: 'value'
                    },
                    xAxis:{
                        type: 'category',
                        data: [],
                        axisLabel: {
                            show: true,
                            interval:0,
                            rotate:40,
                        },
                    },
                    series:{
                            name: '人次',
                            type: 'line',
                            smooth: true,
                            data: [],
                            color: '#3490DE',
                    },
                };
                for(let item of chartData){
                    option.xAxis.data.push(item.ymd)
                    option.series.data.push(item.visitor)
                }
                // 初始化Echart
                let visitorChart = echarts.init(document.getElementById('visitorChart'));
                visitorChart.setOption(option);
                // 兼容分辨率改变
                window.addEventListener('resize',() => {
                    visitorChart.resize();
                })
            },
            //获取最新评论
            getNewComment(){
                //制造参数
                let params = {
                    "userID": this.id,
                };
                Ajax.GetCommentsByUserId(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.newComment = res.data
                        //恢复Spinning
                        this.nclSpinning = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                        //恢复Spinning
                        this.nclSpinning = false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //跳转博客详情
            goOneBlog(id){
                //跳转界面
                this.$router.push({path: '/clientBlog'})
                //存储当前评论所在博客ID
                window.sessionStorage.setItem("nowBlogID",id)
            },
        }
    };
</script>

<style lang="scss" scoped>
    .AdminHome{
        width: 100%;
        height: 100%;
        padding: 10px 20px 20px 20px;
        border-radius: 40px;
        .leftBox{
            width: 100%;
            height: 100%;
            min-height: 700px;
            background-color: white;
            border-radius: 40px;
            text-align: center;
            .buttonBox{
                padding: 20px;
                text-align: right;
            }
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
            height: 100%;
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