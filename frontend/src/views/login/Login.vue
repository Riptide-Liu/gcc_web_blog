<template>
    <div class="Login">
        <!-- 背景框 -->
        <div class="background">
            <div class="box">
                <!-- Logo框 -->
                <div class="logoBox">
                    <img 
                        src="../../assets/Images/market_portal.png"
                        style="width: 150px; height: 150px">
                    <span class="logoText">GCCBlog后台管理</span>
                </div>
                <!-- 表单背景框 -->
                <div class="formBox">
                    <!-- 表单提交框 -->
                    <a-form 
                        ref="loginForm" :model="loginForm" :rules="loginRules">
                        <!-- 账号框 -->
                        <a-form-item style="margin-bottom: 20px" prop="username">
                            <a-input 
                                type="text" 
                                v-model="loginForm.username" 
                                placeholder="请输入用户名"
                                allowClear/>
                        </a-form-item>
                        <!-- 密码框 -->
                        <a-form-item style="margin-bottom: 20px" prop="password">
                            <a-input-password
                                type="password"
                                v-model="loginForm.password"
                                placeholder="请输入密码"
                                @keyup.native.enter="UserLogin('loginForm')"/>
                        </a-form-item>
                        <!-- 记住账号密码选框 -->
                        <div class="editBox" >
                            <a-checkbox 
                                style="color: white;"
                                v-model="loginForm.remember" 
                                @change="rememberPassword"  >
                                记住密码
                            </a-checkbox>
                        </div>
                        <!-- 登录按钮 -->
                        <a-form-item size="large" style="text-align:center">
                            <a-button
                                type="primary"
                                style="width: 100%; margin-top: 20px;"
                                :loading="loginForm.loginLoading"
                                @click.native="UserLogin('loginForm')">
                                登录
                            </a-button>
                        </a-form-item>
                    </a-form>
                </div>
            </div>
        </div>
        
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name:'login',
        data() {
            return {
                loginForm: {
                    loginLoading: false,
                    remember: false,
                    username: "",
                    password: ""
                },
                loginRules: {
                    username: [{ required: true, message:'用户名不能为空', trigger: "blur" }],
                    password: [{ required: true, message:'密码不能为空', trigger: "blur" }],
                }
            };
        },
        computed:{
            
        },
        created(){
            this.rememberSave()
        },
        mounted(){

        },
        methods:{
            //记住密码校验
            rememberSave(){
                let rem = JSON.parse(localStorage.getItem('remember'));
                if (rem) {
                    this.loginForm.username = rem.username;
                    this.loginForm.password = rem.password;
                    this.loginForm.remember = true;
                }
            },
            //记住密码
            rememberPassword(val){
                let rem={
                    username:this.loginForm.username,
                    password:this.loginForm.password
                };
                val?localStorage.setItem('remember',JSON.stringify(rem)):localStorage.removeItem('remember');
            },
            //用户登录
            UserLogin(){
                //制造参数
                let params = {
                    "username": this.loginForm.username,
                    "password": this.loginForm.password,
                };
                console.log("params:",params)
                Ajax.UserLogin(params).then(res =>{
                    this.$_info('成功返回：',res);
                    console.log("res:",res)
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.msg,
                        });
                        console.log("打断点")
                        //恢复loading
                        this.setLoading = false;
                        //保存登陆状态
                        window.sessionStorage.setItem('loginOrNot','yes');
                        window.sessionStorage.setItem('userinfo',JSON.stringify(res.data));
                        //跳转界面
                        this.$router.push({path: '/home'})
                    }else if(res.code == '400'){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                        //恢复loading
                        this.setLoading = false;
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                    console.log("err:",err)
                })
            },
        }
    }
</script>

<style lang="scss" scoped>
    .Login{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        .background{
            width: 100%;
            height: 100%;
            background: url(../../assets/Images/Start_page@3x.png) no-repeat;
            background-size: cover;
            background-attachment: fixed;
            background-position: center center;
            
            // background: #c8d4dc;
            .box{
                position: absolute;
                margin-top: 50px;
                left: 50%;
                transform: translate(-50%); //绝对定位回调
                .logoBox{
                    text-align: center;
                    margin: 20px 0;
                    .logoText{
                        display: block;
                        font-size: 30px;
                        font-weight: bold;
                        color: rgba(0,0,0,0.3),#232f3d;
                    }
                }
                .formBox{
                    width: 350px;
                    padding: 50px;
                    background-color: rgba(0,0,0,0.3);
                    .editBox{
                        .warningText{
                            float: right;
                            font-size: 12px;
                            color: #232f3d;
                        }
                    }
                }
            }
        }
    }

    .termsBox{
        height: 500px;
        overflow: auto;
    }
</style>