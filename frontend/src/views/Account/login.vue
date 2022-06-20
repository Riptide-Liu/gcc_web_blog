<template>
    <div class="Login">
        <!-- 表单框 -->
        <div class="formBox">
            <a-form-model ref="loginForm" :model="loginForm" :rules="loginRules">
                <!-- 账号框 -->
                <span>用户名：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="username">
                    <a-input 
                        type="text" 
                        v-model="loginForm.username" 
                        placeholder="请输入用户名"
                        allowClear
                        @change="unRemember()"/>
                </a-form-model-item>
                <!-- 密码框 -->
                <span>密码：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="password">
                    <a-input-password
                        type="password"
                        v-model="loginForm.password"
                        placeholder="请输入密码"
                        @change="unRemember()"
                        @keyup.native.enter="userLogin('loginForm')"/>
                </a-form-model-item>
                <!-- 去向 -->
                <span>登录去向：</span>
                <a-select 
                    style="margin: 5px 0 20px 0"
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
                        @change="rememberPassword">
                        记住密码
                    </a-checkbox>
                </div>
                <!-- 引导信息框 -->
                <div class="signUpBox">
                    <span>还没账号？试着</span>
                    <a @click="goSignUp()">注册</a>
                    <span>账号或者</span>
                    <a @click="visitorLogin()">游客登录</a>
                </div>
                <!-- 登录按钮 -->
                <a-form-model-item style="margin-bottom: 0px; text-align:center">
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
                    username: "",
                    password: "",
                    remember: false,
                    loginLoading: false,
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
        created(){
            this.rememberCheck()
        },
        methods:{
            //进入页面时的记住密码校验
            rememberCheck(){
                let rem = JSON.parse(localStorage.getItem('userRemember'));
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
                val?localStorage.setItem('userRemember',JSON.stringify(rem)):localStorage.removeItem('userRemember');
            },
            //取消记住密码
            unRemember(){
                this.loginForm.remember = false
            },
            //跳转注册界面
            goSignUp(){
                this.$router.push({path: '/signup'})
            },
            //游客登录
            visitorLogin(){
                this.$router.push({path: '/clientHome'})
                window.sessionStorage.setItem('userInfo',JSON.stringify(
                    {
                        username: 'visitor',
                        nickname: '游客', 
                        email: 'visitor@visitor.com', 
                        id: '', 
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
                //表单校验
                this.$refs.loginForm.validate(valid => {
                    //成功
                    if(valid){
                        this.loginForm.loginLoading = true
                        Ajax.UserLogin(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //恢复loading
                                this.loginForm.loginLoading = false;
                                //保存登陆状态
                                window.sessionStorage.setItem('loginOrNot','yes');
                                window.sessionStorage.setItem('userInfo',JSON.stringify(res.data));
                                //跳转界面
                                if(this.goWhere == 'admin'){
                                    this.$router.push({path: '/adminHome'})
                                    //保存当前端型
                                    window.sessionStorage.setItem('whichPage','admin');
                                }else if(this.goWhere == 'client'){
                                    this.$router.push({path: '/clientHome'})
                                    //保存当前端型
                                    window.sessionStorage.setItem('whichPage','client');
                                }
                                //成功提示框
                                this.$message.success({
                                    content: '登录成功',
                                });
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.data,
                                });
                                //恢复loading
                                this.loginForm.loginLoading = false;
                            }
                        }).catch(err =>{
                            this.$_error('错误信息：',err);
                        })
                    }
                    //失败
                    else{
                        if(this.goWhere == '请选择登录去向'){
                            //失败提示框
                            this.$error({
                                title: '请选择登录的端型',
                            });
                        }else{
                            //失败提示框
                            this.$error({
                                title: '必填项不可为空',
                            });
                        }
                    }
                })
            },
            //绕过登录
            byelogin(){
                if(this.goWhere == '请选择登录去向'){
                    //失败提示框
                    this.$error({
                        title: '请选择登录的端型',
                    });
                }else{
                    //保存登陆状态
                    window.sessionStorage.setItem('loginOrNot','yes');
                    window.sessionStorage.setItem('userInfo',JSON.stringify(
                        {
                            username: 'admin',
                            nickname: '管理员', 
                            email: 'admin@admin.com', 
                            id: '1', 
                            level: '2',
                            enable: '1',
                        }
                    ));
                    //跳转界面
                    if(this.goWhere == 'admin'){
                        this.$router.push({path: '/adminHome'})
                        //保存当前端型
                        window.sessionStorage.setItem('whichPage','admin');
                    }else if(this.goWhere == 'client'){
                        this.$router.push({path: '/clientHome'})
                        //保存当前端型
                        window.sessionStorage.setItem('whichPage','client');
                    }
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    .Login{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        span{
            color: white;
        } 
        .formBox{
            .signUpBox{
                margin-top: 10px
            }
        }
    }
</style>