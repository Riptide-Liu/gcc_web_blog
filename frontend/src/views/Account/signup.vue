<template>
    <div class="SignUp">
        <!-- 表单框 -->
        <div class="formBox">
            <a-form-model ref="signUpForm" :model="signUpForm" :rules="signUpRules">
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
                <a-form-model-item>
                    <a-button
                        type="primary"
                        style="width: 100%"
                        :loading="signUpForm.submitLoading"
                        @click.native="userSignUp('signUpForm')">
                        提交
                    </a-button>
                </a-form-model-item>
                <a-button
                    type="info"
                    style="width: 100%;"
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
        name:'signup',
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
                //表单校验
                this.$refs.signUpForm.validate(valid => {
                    //成功
                    if(valid){
                        this.signUpForm.submitLoading = true
                        Ajax.Register(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: '注册成功，快登录看看吧',
                                    onOk:() => {
                                        //跳转界面
                                        this.$router.push({path: '/login'})
                                    }
                                });
                                //恢复loading
                                this.signUpForm.submitLoading = true
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.data,
                                });
                                //恢复loading
                                this.signUpForm.submitLoading = true
                            }
                        }).catch(err =>{
                            this.$_error('错误信息：',err);
                        })
                    }
                    //失败
                    else{
                        //失败提示框
                        this.$error({
                            title: '必填项不可为空',
                        });
                    }
                });
            },
        }
    }
</script>

<style lang="scss" scoped>
    .SignUp{
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