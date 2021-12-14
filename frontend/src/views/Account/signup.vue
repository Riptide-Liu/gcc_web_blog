<template>
    <div class="Login">
        <!-- 表单背景框 -->
        <div class="formBox">
            <!-- 表单提交框 -->
            <a-form-model 
                ref="signUpForm" :model="signUpForm" :rules="signUpRules">
                <!-- 账号框 -->
                <span>用户名：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="username">
                    <a-input 
                        type="text" 
                        v-model="signUpForm.username" 
                        placeholder="请输入用户名"
                        allowClear/>
                </a-form-model-item>
                <!-- 密码框 -->
                <span>密码：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="password">
                    <a-input-password
                        type="password"
                        v-model="signUpForm.password"
                        placeholder="请输入密码"/>
                </a-form-model-item>
                <span>确认密码：</span>
                <a-form-model-item style="margin-bottom: 20px" prop="cheakPassword">
                    <a-input-password
                        type="password"
                        v-model="signUpForm.cheakPassword"
                        placeholder="请再次输入密码"/>
                </a-form-model-item>
                <!-- 提交按钮 -->
                <a-form-model-item size="large" style="text-align:center">
                    <a-button
                        type="primary"
                        style="width: 100%; margin-top: 20px;"
                        :loading="signUpForm.submitLoading"
                        @click.native="userSignUp('signUpForm')">
                        提交
                    </a-button>
                </a-form-model-item>
                <a-button
                    type="info"
                    style="width: 100%; margin-top: 0px;"
                    @click.native="goLogin()">
                    返回登录
                </a-button>
            </a-form-model>
        </div>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name:'login',
        data() {
            // 自定义密码校验
            const cheakAgain = async(rule, value, callback) => {
                if(this.signUpForm.password != this.signUpForm.cheakPassword){
                    return callback(new Error('两次输入密码不一致！'));
                }else if(value.length < 1){
                    return callback(new Error('密码不能为空'));
                }
            };
            return {
                signUpForm: {
                    username: "",
                    password: "",
                    cheakPassword: "",
                    submitLoading: false,
                },
                signUpRules: {
                    username: [{ required: true, message:'用户名不能为空', trigger: "blur" }],
                    password: [
                        { required: true, message:'密码不能为空', trigger: "blur" },
                        { min: 8, message:'密码最少需要输入8位', trigger: 'blur'}
                    ],
                    cheakPassword: [
                        { required: true, validator: cheakAgain, trigger: "blur" },
                    ],
                },
            };
        },
        computed:{
            
        },
        created(){

        },
        mounted(){

        },
        methods:{
            //返回登录页
            goLogin(){
                this.$router.push({path: '/login'})
            },
            //用户注册
            userSignUp(){
                //制造参数
                let params = {
                    "username": this.signUpForm.username,
                    "password": this.signUpForm.password,
                };
                console.log("params:",params)
                this.$refs.signUpForm.validate(valid => {
                    if(valid){
                        alert('submit!');
                    }else{
                        console.log('error submit!!');
                        return false;
                    }
                });
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