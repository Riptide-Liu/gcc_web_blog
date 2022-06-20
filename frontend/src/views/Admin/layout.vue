<template>
    <div class="AdminLayout">
        <a-layout>
            <!-- 左侧边栏 -->
            <a-layout-sider
                :style="{ height: '1080px', 
                            width: '200px',
                            padding: '30px', 
                            background: '-webkit-linear-gradient(#8C4AF2 0%, #585CFC 100%)'}">
                <!-- Logo框 -->
                <div class="leftTopLogo">
                    <img src="../../assets/Images/logo.png" style="width: 100px; height: 100px">
                </div>
                <!-- 导航栏内容 -->
                <div class="leftButtonBox">
                    <!-- 首页 -->
                    <div class="oneButton">
                        <a-button
                            id="homeBtn"
                            ghost
                            :class="{ 'leftButtonOff': true, 'leftButtonOn': homeClickOrNot }"
                            @mouseover.native="leftBtnIn('homeBtn')"
                            @mouseout.native="leftBtnOut('homeBtn')"
                            @click="goNewPage('homeBtn')">
                            <div class="content" v-if="homeBtnStatu == 'showOut' && homeClickOrNot == false">
                                <img  src="../../assets/Images/data_out.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px; color: #B5B5B5">首页</span>
                            </div>
                            <div class="content" v-else-if="homeBtnStatu == 'showIn' || homeClickOrNot == true">
                                <img src="../../assets/Images/data_in.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px">首页</span>
                            </div>
                        </a-button>
                    </div>
                    <!-- 用户列表 -->
                    <div class="oneButton" v-if="level == 2">
                        <a-button
                            id="userBtn"
                            ghost
                            :class="{ 'leftButtonOff': true, 'leftButtonOn': userClickOrNot }"
                            @mouseover.native="leftBtnIn('userBtn')"
                            @mouseout.native="leftBtnOut('userBtn')"
                            @click="goNewPage('userBtn')">
                            <div class="content" v-if="userBtnStatu == 'showOut' && userClickOrNot == false">
                                <img  src="../../assets/Images/user_out.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px; color: #B5B5B5">用户列表</span>
                            </div>
                            <div class="content" v-else-if="userBtnStatu == 'showIn' || userClickOrNot == true">
                                <img src="../../assets/Images/user_in.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px">用户列表</span>
                            </div>
                        </a-button>
                    </div>
                    <!-- 分类列表 -->
                    <div class="oneButton" v-if="level == 2">
                        <a-button
                            id="typeBtn"
                            ghost
                            :class="{ 'leftButtonOff': true, 'leftButtonOn': typeClickOrNot }"
                            @mouseover.native="leftBtnIn('typeBtn')"
                            @mouseout.native="leftBtnOut('typeBtn')"
                            @click="goNewPage('typeBtn')">
                            <div class="content" v-if="typeBtnStatu == 'showOut' && typeClickOrNot == false">
                                <img  src="../../assets/Images/type_out.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px; color: #B5B5B5">分类列表</span>
                            </div>
                            <div class="content" v-else-if="typeBtnStatu == 'showIn' || typeClickOrNot == true">
                                <img src="../../assets/Images/type_in.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px">分类列表</span>
                            </div>
                        </a-button>
                    </div>
                    <!-- 博客列表 -->
                    <div class="oneButton">
                        <a-button
                            id="blogBtn"
                            ghost
                            :class="{ 'leftButtonOff': true, 'leftButtonOn': blogClickOrNot }"
                            @mouseover.native="leftBtnIn('blogBtn')"
                            @mouseout.native="leftBtnOut('blogBtn')"
                            @click="goNewPage('blogBtn')">
                            <div class="content" v-if="blogBtnStatu == 'showOut' && blogClickOrNot == false">
                                <img  src="../../assets/Images/blog_out.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px; color: #B5B5B5">博客列表</span>
                            </div>
                            <div class="content" v-else-if="blogBtnStatu == 'showIn' || blogClickOrNot == true">
                                <img src="../../assets/Images/blog_in.png"
                                    style="width:22px; height:22px" />
                                <span style="margin-left: 20px">博客列表</span>
                            </div>
                        </a-button>
                    </div>
                </div>
            </a-layout-sider>
            <!-- 右侧栏目 -->
            <a-layout>
                <!-- 顶部导航 -->
                <a-layout-header class="header">
                    <!-- 顶部左侧LOGO -->
                    <div class="topLeft">
                        <span style="font-size: 22px">GCCBlog后台管理</span>
                    </div>
                    <!-- 顶部右侧个人信息 -->
                    <div class="topRight">
                        <!-- 头像 -->
                        <a-avatar :src="icon"
                            style="width: 40px; height: 40px"/>
                        <a-divider type="vertical" 
                            style="color: black; margin:0 20px"/>
                        <!-- 下拉菜单 -->
                        <a-dropdown :trigger="['click']">
                            <a class="nameText" @click="e => e.preventDefault()">
                                {{nickname || '暂无昵称'}}
                                <a-icon type="down" />
                            </a>
                            <a-menu slot="overlay">
                                <a-menu-item key="0" @click="goNewPage('userinfoBtn')" v-if="nickname != '管理员'">
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
                <!-- 路由渲染块 -->
                <a-layout-content
                    :style="{ height: 'calc(100vh - 80px)', 
                            margin: '20px',
                            padding: '15px'}">
                    <div class="mainPage">
                        <router-view></router-view>
                    </div>
                </a-layout-content>
            </a-layout>
        </a-layout>
    </div>
</template>

<script>
    export default {
        name: "adminlayout",
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

                homeClickOrNot: false,
                userClickOrNot: false,
                typeClickOrNot: false,
                blogClickOrNot: false,
                homeBtnStatu: 'showOut',
                userBtnStatu: 'showOut',
                typeBtnStatu: 'showOut',
                blogBtnStatu: 'showOut',
            };
        },
        created(){
            this.getSession()
            this.getNowPage()
        },
        watch: {

        },
        methods:{
            //存取session
            getSession(){
                //跳转到此页时，初始化正确导航栏状态
                if(sessionStorage.getItem('whichPage') == 'admin'){
                    this.homeClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }
                //获取用户信息
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //判断当前路由
            getNowPage(){
                //切割URL获得当前页面名称
                let nowPage = (window.location.hash).split('#/')[1]
                //根据名称切换对应导航栏状态
                if(nowPage == 'adminHome'){
                    this.homeClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(nowPage == 'user'){
                    this.homeClickOrNot = false
                    this.userClickOrNot = true
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(nowPage == 'type'){
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = true
                    this.blogClickOrNot = false
                }else if(nowPage == 'adminBlog'){
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = true
                }else if(nowPage == 'userinfo'){
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }
            },
            //监听昵称变化
            getNewName(){
                window.addEventListener('userInfo', () => {
                    this.nickname = JSON.parse(sessionStorage.getItem('userInfo')).nickname;
                });
            },
            //导航栏hover状态判断
            leftBtnIn(btnName){
                document.getElementById(btnName).style.backgroundColor = "black";
                if(btnName == 'homeBtn'){
                    this.homeBtnStatu = 'showIn'
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
                    this.homeBtnStatu = 'showOut'
                }else if(btnName == 'userBtn'){
                    this.userBtnStatu = 'showOut'
                }else if(btnName == 'typeBtn'){
                    this.typeBtnStatu = 'showOut'
                }else if(btnName == 'blogBtn'){
                    this.blogBtnStatu = 'showOut'
                }
            },
            //跳转对应界面
            goNewPage(btnName){
                if(btnName == 'homeBtn'){
                    this.$router.push({path: '/adminHome'})
                    this.homeClickOrNot = true
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'userBtn'){
                    this.$router.push({path: '/user'})
                    this.homeClickOrNot = false
                    this.userClickOrNot = true
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'typeBtn'){
                    this.$router.push({path: '/type'})
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = true
                    this.blogClickOrNot = false
                }else if(btnName == 'blogBtn'){
                    this.$router.push({path: '/adminBlog'})
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = true
                }else if(btnName == 'userinfoBtn'){
                    this.$router.push({path: '/userinfo'})
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                }else if(btnName == 'clientBtn'){
                    this.$router.push({path: '/clientHome'})
                    this.homeClickOrNot = false
                    this.userClickOrNot = false
                    this.typeClickOrNot = false
                    this.blogClickOrNot = false
                    //保存当前端型
                    window.sessionStorage.setItem('whichPage','client');
                }
            },
            // 退出登录
            logOut(){
                this.$confirm({
                    okText: '确定',
                    cancelText: '取消',
                    title:"是否确定要退出登录？",
                    onOk:() => {
                        //删除登陆状态
                        window.sessionStorage.removeItem('loginOrNot')
                        window.sessionStorage.removeItem('userInfo')
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
    .AdminLayout{
        .leftTopLogo{
            margin-bottom: 30px;
            text-align: center;
        }
        .leftButtonBox{
            text-align: center;
            .leftButtonOff{
                width: 140px;
                height: 30px;
                border: 0;
                margin: 20px 0;
                background-color: #484849;
            }
            .leftButtonOn{
                width: 140px;
                height: 30px;
                border: 0;
                margin: 20px 0;
                background-color: white;
            }
            .ant-btn:hover, .ant-btn:focus {
                color: white !important;
            }
        }
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
    }
</style>