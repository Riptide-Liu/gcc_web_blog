<template>
    <div class="Login">
        <!-- 表单背景框 -->
        <div class="formBox">
            <!-- 表单提交框 -->
            <a-form-model 
                ref="loginForm" :model="loginForm" :rules="loginRules">
                <!-- 账号框 -->
                <span>用户名：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="username">
                    <a-input 
                        type="text" 
                        v-model="loginForm.username" 
                        placeholder="请输入用户名"
                        allowClear/>
                </a-form-model-item>
                <!-- 密码框 -->
                <span>密码：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="password">
                    <a-input-password
                        type="password"
                        v-model="loginForm.password"
                        placeholder="请输入密码"
                        @keyup.native.enter="userLogin('loginForm')"/>
                </a-form-model-item>
                <!-- 去向 -->
                <a-select 
                    style="margin-bottom: 20px"
                    v-model="goWhere"
                    placeholder="请选择登录去向">
                    <a-select-option v-for="item in whereOptions" 
                        :key="item.value"
                        :value="item.value">
                        {{item.text}}
                    </a-select-option>
                </a-select>
                <!-- 记住账号密码选框 -->
                <div class="editBox">
                    <a-checkbox 
                        style="color: white;"
                        v-model="loginForm.remember" 
                        @change="rememberPassword"  >
                        记住密码
                    </a-checkbox>
                </div>
                <div class="signUpBox">
                    <span>还没账号？试着</span>
                    <a @click="goSignUp()">注册</a>
                    <span>账号或者</span>
                    <a @click="visitorLogin()">游客登录</a>
                </div>
                <!-- 登录按钮 -->
                <a-form-model-item size="large" style="text-align:center">
                    <a-button
                        type="primary"
                        style="width: 100%; margin-top: 20px;"
                        :loading="loginForm.loginLoading"
                        @click.native="userLogin('loginForm')">
                        登录
                    </a-button>
                </a-form-model-item>
            </a-form-model>
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
                    username: [{ required: true, message:'用户名不能为空', trigger: "change" }],
                    password: [{ required: true, message:'密码不能为空', trigger: "change" }],
                },
                goWhere: '请选择登录去向',
                whereOptions: [
                    {value:'client',text:'客户端'},
                    {value:'admin',text:'管理端'},
                ],
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
            //跳转注册界面
            goSignUp(){
                this.$router.push({path: '/signup'})
            },
            //游客登录
            visitorLogin(){
                this.$router.push({path: '/clientHome'})
                window.sessionStorage.setItem('userinfo',JSON.stringify(
                    {
                        username: 'visitor',
                        nickname: '游客', 
                        email: 'visitor@visitor.com', 
                        id: 'visitor', 
                        level: '0',
                        enable: '1',
                    }
                ));
            },
            //用户登录
            userLogin(){
                //制造参数
                let params = {
                    "username": this.loginForm.username,
                    "password": this.loginForm.password,
                };
                console.log("params:",params)
                if(this.goWhere == '请选择登录去向'){
                    //失败提示框
                    this.$error({
                        title: '请选择登录的端型',
                    });
                }else if(this.goWhere == 'admin'){
                    this.$router.push({path: '/adminHome'})
                    //保存当前端型
                    window.sessionStorage.setItem('whichPage','admin');
                }else if(this.goWhere == 'client'){
                    this.$router.push({path: '/clientHome'})
                    //保存当前端型
                    window.sessionStorage.setItem('whichPage','client');
                }
                //保存登陆状态
                window.sessionStorage.setItem('loginOrNot','yes');
                window.sessionStorage.setItem('userinfo',JSON.stringify(
                    {
                        username: 'admin',
                        nickname: '管理员', 
                        email: 'admin@admin.com', 
                        id: '1', 
                        level: '2',
                        enable: '1',
                        password: 'admin', 
                        blogNumber: '0', 
                    }
                ));
                // Ajax.UserLogin(params).then(res =>{
                //     this.$_info('成功返回：',res);
                //     console.log("res:",res)
                //     if(res.code == 200){
                //         //成功提示框
                //         this.$success({
                //             title: res.msg,
                //         });
                //         //恢复loading
                //         this.setLoading = false;
                //         //保存登陆状态
                //         window.sessionStorage.setItem('loginOrNot','yes');
                //         window.sessionStorage.setItem('userinfo',JSON.stringify(res.data));
                //         //跳转界面
                //         if(this.goWhere == 'admin'){
                //             this.$router.push({path: '/adminHome'})
                //             //保存当前端型
                //             window.sessionStorage.setItem('whichPage','admin');
                //         }else if(this.goWhere == 'client'){
                //             this.$router.push({path: '/clientHome'})
                //             //保存当前端型
                //             window.sessionStorage.setItem('whichPage','client');
                //         }
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
    }
</script>

<style lang="scss" scoped>
    .Login{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        .formBox{
            .signUpBox{
                margin-top: 10px
            }
        }
    }
</style>