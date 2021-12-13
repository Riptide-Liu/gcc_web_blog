<template>
    <div class="Layout">
            
            <a-layout>
                <!-- 顶部导航 -->
                <a-layout-header class="header">
                    <!-- 顶部左侧LOGO -->
                    <div class="topLeft">
                        <img src="../../../assets/Images/admin_logo.png"
                            style="width: 40px; height: 40px; margin-right: 10px">
                        <span style="font-size: 22px">GCCBlog后台管理</span>
                    </div>
                    <div class="topRight">
                        <a-divider type="vertical" 
                            style="color: black; margin:0 20px;"/>
                        <!-- 头像 -->
                        <a-avatar :src="icon"
                            style="width: 40px; height: 40px; margin-right: 20px"/>
                        <!-- 下拉菜单 -->
                        <a-dropdown :trigger="['click']">
                            <a class="nameText" @click="e => e.preventDefault()">
                                {{nickname || '用户名'}}
                                <a-icon type="down" />
                            </a>
                            <a-menu slot="overlay">
                                <a-menu-item key="0" @click="goNewPage('userinfoBtn')">
                                    <a>个人中心</a>
                                </a-menu-item>
                                <a-menu-item key="1" @click="goNewPage('clientBtn')">
                                    <a>前往客户端</a>
                                </a-menu-item>
                                <a-menu-divider />
                                <a-menu-item key="2" @click="logOut()">
                                    退出登录
                                </a-menu-item>
                            </a-menu>
                        </a-dropdown>
                    </div>
                </a-layout-header>
            </a-layout>
            
            <a-layout>
                <!-- 侧边栏 -->
                <a-layout-sider
                    :style="{ height: 'calc(100vh - 100px)', 
                              width: 'calc(100vh - 100px)',
                              padding: '30px', 
                              background: '#f0f2f5 100%', }">
                    <div class="leftButtonBox">
                        <!-- vue绑定多个class -->
                        <a-button
                            shape="circle"
                            id="homeBtn"
                            :class="{ 'leftButton': true, 'blackBg': mainClickOrNot }"
                            @mouseover.native="leftBtnIn('homeBtn')"
                            @mouseout.native="leftBtnOut('homeBtn')"
                            @click="goNewPage('homeBtn')">
                            <img v-if="mainBtnStatu == 'showOut' && mainClickOrNot == false" 
                                src="../../../assets/Images/main_out.png"
                                style="width:22px; height:22px" />
                            <img v-else-if="mainBtnStatu == 'showIn' || mainClickOrNot == true"
                                src="../../../assets/Images/main_in.png"
                                style="width:22px; height:22px" />
                        </a-button>

                        <a-button
                            shape="circle"
                            id="userBtn"
                            :class="{ 'leftButton': true, 'blackBg': userClickOrNot }"
                            @mouseover.native="leftBtnIn('userBtn')"
                            @mouseout.native="leftBtnOut('userBtn')"
                            @click="goNewPage('userBtn')">
                            <img v-if="userBtnStatu == 'showOut' && userClickOrNot == false" 
                                src="../../../assets/Images/state_out.png"
                                style="width:22px; height:22px" />
                            <img v-else-if="userBtnStatu == 'showIn' || userClickOrNot == true"
                                src="../../../assets/Images/state_in.png"
                                style="width:22px; height:22px" />
                        </a-button>

                        <a-button
                            shape="circle"
                            id="typeBtn"
                            :class="{ 'leftButton': true, 'blackBg': typeClickOrNot }"
                            @mouseover.native="leftBtnIn('typeBtn')"
                            @mouseout.native="leftBtnOut('typeBtn')"
                            @click="goNewPage('typeBtn')">
                            <img v-if="typeBtnStatu == 'showOut' && typeClickOrNot == false" 
                                src="../../../assets/Images/risk_out.png"
                                style="width:22px; height:22px" />
                            <img v-else-if="typeBtnStatu == 'showIn' || typeClickOrNot == true"
                                src="../../../assets/Images/risk_in.png"
                                style="width:22px; height:22px" />
                        </a-button>

                        <a-button
                            shape="circle"
                            id="blogBtn"
                            :class="{ 'leftButton': true, 'blackBg': blogClickOrNot }"
                            @mouseover.native="leftBtnIn('blogBtn')"
                            @mouseout.native="leftBtnOut('blogBtn')"
                            @click="goNewPage('blogBtn')">
                            <img v-if="blogBtnStatu == 'showOut' && blogClickOrNot == false" 
                                src="../../../assets/Images/time_out.png"
                                style="width:22px; height:22px" />
                            <img v-else-if="blogBtnStatu == 'showIn' || blogClickOrNot == true"
                                src="../../../assets/Images/time_in.png"
                                style="width:22px; height:22px" />
                        </a-button>
                    </div>
                </a-layout-sider>
                <!-- 路由渲染块 -->
                <a-layout-content
                    :style="{ height: 'calc(100vh - 100px)', 
                              margin: '20px', 
                              padding: '15px'}">
                    <div class="mainPage">
                        <router-view></router-view>
                    </div>
                </a-layout-content>
            </a-layout>
            
    </div>
</template>

<script>
    import Ajax from "../../../api/index";
    import _ from 'lodash';
    export default {
        name: "layout",
        components: {},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',
                icon: '',

                mainClickOrNot: false,
                userClickOrNot: false,
                typeClickOrNot: false,
                blogClickOrNot: false,
                mainBtnStatu: 'showOut',
                userBtnStatu: 'showOut',
                typeBtnStatu: 'showOut',
                blogBtnStatu: 'showOut',
            };
        },
        computed:{

        },
        created(){
            this.getSession()
            this.getNowPage()
        },
        mounted(){
            
        },
        watch: {

        },
        methods:{
            //存取session
            getSession(){
                if(sessionStorage.getItem('uwhichPage') == 'admin'){
                    console.log("11111111111111")
                    this.mainClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userinfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
                console.log('userinfo:',this,{username,nickname,email,id,level,enable})
            },
            //判断当前路由
            getNowPage(){
                let nowPage = (window.location.hash).split('#/')[1]
                console.log('nowPage:',nowPage)
                if(nowPage == 'adminHome'){
                    this.mainClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(nowPage == 'user'){
                    this.mainClickOrNot = false
                    this.userClickOrNot = true
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(nowPage == 'type'){
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = true
                    this.blogClickOrNot = false
                }else if(nowPage == 'adminBlog'){
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = true
                }else if(nowPage == 'userinfo'){
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }
            },
            //导航栏hover状态判断
            leftBtnIn(btnName){
                document.getElementById(btnName).style.backgroundColor = "black";
                if(btnName == 'homeBtn'){
                    this.mainBtnStatu = 'showIn'
                }else if(btnName == 'userBtn'){
                    this.userBtnStatu = 'showIn'
                }else if(btnName == 'typeBtn'){
                    this.typeBtnStatu = 'showIn'
                }else if(btnName == 'blogBtn'){
                    this.blogBtnStatu = 'showIn'
                }
            },
            leftBtnOut(btnName){
                document.getElementById(btnName).style.backgroundColor = "#f0f2f5";
                if(btnName == 'homeBtn'){
                    this.mainBtnStatu = 'showOut'
                }else if(btnName == 'userBtn'){
                    this.userBtnStatu = 'showOut'
                }else if(btnName == 'typeBtn'){
                    this.typeBtnStatu = 'showOut'
                }else if(btnName == 'blogBtn'){
                    this.blogBtnStatu = 'showOut'
                }
            },
            //跳转对应页面
            goNewPage(btnName){
                if(btnName == 'homeBtn'){
                    this.$router.push({path: '/adminHome'})
                    this.mainClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'userBtn'){
                    this.$router.push({path: '/user'})
                    this.mainClickOrNot = false
                    this.userClickOrNot = true
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'typeBtn'){
                    this.$router.push({path: '/type'})
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = true
                    this.blogClickOrNot = false
                }else if(btnName == 'blogBtn'){
                    this.$router.push({path: '/adminBlog'})
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = true
                }else if(btnName == 'userinfoBtn'){
                    this.$router.push({path: '/userinfo'})
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'clientBtn'){
                    this.$router.push({path: '/clientHome'})
                    this.mainClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                    //保存当前端型
                    window.sessionStorage.setItem('whichPage','client');
                }
            },
            // 退出登录
            logOut(){
                this.$info({
                    title:"是否确认要退出登录？(按ESC取消)",
                    onOk:() => {
                        //删除登陆状态
                        window.sessionStorage.removeItem('loginOrNot')
                        window.sessionStorage.removeItem('userinfo')
                        window.sessionStorage.removeItem('whichPage')
                        //跳转界面
                        this.$router.push({path: '/login'})
                    }
                })
            },
        }
    }
</script>

<style lang="scss" scoped>
    .Layout{
        .header{
            height: 80px;
            background: #FFFFFF;
            padding: 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            .topLeft{
                display: flex;
                align-items: center;
                justify-content: center;
                letter-spacing: 0;
                margin-left: 30px;
            }
            .topRight{
                margin-right: 30px;
                .nameText{
                    font-family: Alibaba-PuHuiTi-M;
                    font-size: 14px;
                    color: #4B4B4B;
                    letter-spacing: 0;
                    text-align: center;
                }
            }
        }
        .leftButtonBox{
            text-align: center;
            .leftButton{
                width: 78px;
                height: 78px;
                border: 0;
                margin: 20px;
                background-color: #f0f2f5;
            }
            .blackBg{
                width: 78px;
                height: 78px;
                border: 0;
                margin: 20px;
                background-color: black !important;
            }
        }
        
    }
</style>