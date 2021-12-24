<template>
    <div class="ClientLayout">
        <!-- 顶部导航 -->
        <a-layout>
            <a-layout-header class="header">
                <!-- 顶部左侧LOGO -->
                <div class="topLeft">
                    <img src="../../assets/Images/logo_long.png"
                        style="height: 70px; margin-right: 10px">
                </div>
                <div class="topRight">
                    <!-- 头像 -->
                    <a-avatar :src="icon"
                        style="width: 40px; height: 40px"/>
                    <a-divider type="vertical" 
                        style="color: black; margin:0 20px"/>
                    <!-- 下拉菜单 -->
                    <a-dropdown :trigger="['click']">
                        <a class="nameText" @click="e => e.preventDefault()">
                            {{nickname || '用户名'}}
                            <a-icon type="down" />
                        </a>
                        <a-menu slot="overlay">
                            <a-menu-item v-if="level != 0" key="0" @click="goAdmin()">
                                <a>前往管理端</a>
                            </a-menu-item>
                            <a-menu-divider v-if="level != 0" />
                            <a-menu-item key="1" @click="logOut()">
                                退出登录
                            </a-menu-item>
                        </a-menu>
                    </a-dropdown>
                </div>
            </a-layout-header>
        </a-layout>
        <!-- 路由渲染块 -->
        <a-layout>
            <a-layout-content
                :style="{ height: 'calc(100vh - 100px)', 
                            margin: '10px'}">
                <div class="mainPage">
                    <router-view></router-view>
                </div>
            </a-layout-content>
        </a-layout> 
    </div>
</template>

<script>
    export default {
        name: "clientlayout",
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',
                icon: '',
            };
        },
        created(){
            this.getSession()
        },
        methods:{
            //存取session
            getSession(){
                //获取用户信息
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //跳转管理端
            goAdmin(){
                //跳转界面
                this.$router.push({path: '/adminHome'})
                //保存当前端型
                window.sessionStorage.setItem('whichPage','admin');
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
                    },
                })
            },
        }
    }
</script>

<style lang="scss" scoped>
    .ClientLayout{
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