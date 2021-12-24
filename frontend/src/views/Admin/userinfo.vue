<template>
    <div class="UserInfo">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">个人中心</span>
            <a-button
                v-if="nowBox == 'show'"
                type="primary"
                class="topButton"
                @click="boxJump('edit')">
                信息编辑
            </a-button>
            <a-button
                v-else-if="nowBox == 'edit'"
                type="primary"
                class="topButton"
                @click="boxJump('show')">
                返回
            </a-button>
        </div>
        <!-- 信息框 -->
        <div class="midBox">
            <!-- 展示框 -->
            <div class="showBox" v-if="nowBox == 'show'">
                <a-form-model ref="infoForm" :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                    <a-form-model-item ref="username" label="用户名" prop="username">
                        <span style="width:100%">{{username}}</span>
                    </a-form-model-item>
                    <a-form-model-item ref="nickname" label="昵称" prop="nickname">
                        <span style="width:100%">{{nickname}}</span>
                    </a-form-model-item>
                    <a-form-model-item ref="email" label="邮箱" prop="email">
                        <span style="width:100%">{{email}}</span>
                    </a-form-model-item>
                </a-form-model>
            </div>
            <!-- 编辑框 -->
            <div class="editBox" v-else-if="nowBox == 'edit'">
                <a-tabs default-active-key="1">
                    <!-- 基础信息 -->
                    <a-tab-pane key="1" tab="基础信息">
                        <div class="numBox">
                            <a-form-model 
                                ref="editForm" :model="editForm" :rules="editRules" 
                                :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                                <a-form-model-item ref="username" label="用户名" prop="username">
                                    <a-input
                                        type="text"
                                        v-model="editForm.username"
                                        placeholder="用户名"
                                        style="width:100%"
                                        disabled/>
                                </a-form-model-item>
                                <a-form-model-item ref="nickname" label="昵称" prop="nickname">
                                    <a-input
                                        type="text"
                                        v-model="editForm.nickname"
                                        placeholder="昵称"
                                        style="width:100%"/>
                                </a-form-model-item>
                                <a-form-model-item ref="email" label="邮箱" prop="email">
                                    <a-input
                                        type="text"
                                        v-model="editForm.email"
                                        placeholder="邮箱"
                                        style="width:100%"/>
                                </a-form-model-item>
                            </a-form-model>
                        </div>
                        <!-- 底部按钮 -->
                        <div class="bottomBox">
                            <a-button
                                v-if="nowBox == 'edit'"
                                type="primary"
                                class="bottomButton"
                                :loading="loading"
                                @click="editUserInfo()">
                                提交保存
                            </a-button>
                        </div>
                    </a-tab-pane>
                    <!-- 密码修改 -->
                    <a-tab-pane key="2" tab="密码修改">
                        <div class="numBox">
                            <a-form-model 
                                ref="passwordForm" :model="passwordForm" :rules="passwordRules" 
                                :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                                <a-form-model-item ref="old_password" label="原密码" prop="old_password">
                                    <a-input-password
                                        type="password"
                                        v-model="passwordForm.old_password"
                                        placeholder="请输入原密码"
                                        style="width:100%"/>
                                </a-form-model-item>
                                <a-form-model-item ref="password" label="新密码" prop="password">
                                    <a-input-password
                                        type="password"
                                        v-model="passwordForm.password"
                                        placeholder="请输入新密码"
                                        style="width:100%"/>
                                </a-form-model-item>
                                <a-form-model-item ref="cheakPassword" label="确认密码" prop="cheakPassword">
                                    <a-input-password
                                        type="password"
                                        v-model="passwordForm.cheakPassword"
                                        placeholder="请再次输入密码"
                                        style="width:100%"/>
                                </a-form-model-item>
                            </a-form-model>
                        </div>
                        <!-- 底部按钮 -->
                        <div class="bottomBox">
                            <a-button
                                v-if="nowBox == 'edit'"
                                type="primary"
                                class="bottomButton"
                                :loading="loading"
                                @click="changePassword()">
                                提交保存
                            </a-button>
                        </div>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </div>
        
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name: "userinfo",
        data() {
            // 自定义邮箱校验
            const cheakEmail = async(rule, value, callback) => {
                let emailRules = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
                if( !emailRules.test(this.editForm.email)){
                    return callback(new Error('邮箱格式不正确'));
                }
            };
            // 自定义密码校验
            const cheakAgain = async(rule, value, callback) => {
                if(this.passwordForm.password != this.passwordForm.cheakPassword){
                    return callback(new Error('两次输入密码不一致！'));
                }
            };
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                labelCol: { span: 4 },
                wrapperCol: { span: 18 },

                nowBox: 'show',

                loading: false,
                editForm: {},
                editRules: {
                    nickname: [{ required: true, message:'昵称不能为空', trigger: "blur" }],
                    email: [
                        { required: true, message:'邮箱不能为空', trigger: "blur" },
                        { required: true, validator: cheakEmail, trigger: "blur" },
                    ],
                },

                passwordForm: {},
                passwordRules: {
                    old_password: [{ required: true, message:'原密码不能为空', trigger: "blur" }],
                    password: [
                        { required: true, message:'新密码不能为空', trigger: "blur" },
                        { min: 8, message:'密码最少需要输入8位', trigger: 'blur'}
                    ],
                    cheakPassword: [
                        { required: true, message:'确认密码不能为空', trigger: "blur" },
                        { required: true, validator: cheakAgain, trigger: "blur" },
                    ],
                },
            };
        },
        created(){
            this.getSession()
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //跳转编辑框
            boxJump(where){
                if(where == 'edit'){
                    this.nowBox = 'edit'
                    this.editForm = JSON.parse(sessionStorage.getItem('userInfo'))
                }else if(where == 'show'){
                    this.nowBox = 'show'
                }
            },
            //编辑个人信息
            editUserInfo(){
                //制造参数
                let params = {
                    "id": this.id,
                    "nickname": this.editForm.nickname,
                    "email": this.editForm.email,
                }
                //表单校验
                this.$refs.editForm.validate(valid => {
                    //成功
                    if(valid){
                        this.loading = true
                        Ajax.EditUserInfo(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //赋值
                                this.username = res.data.username
                                this.nickname = res.data.nickname
                                this.email = res.data.email
                                this.id = res.data.id
                                this.level = res.data.level
                                this.enable = res.data.enable
                                //存储数据
                                window.sessionStorage.setItem('userInfo',JSON.stringify(res.data))
                                //恢复loading
                                this.loading = false;
                                //刷新视图
                                this.getSession()
                                //回到查看框
                                this.nowBox = 'show'
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                            }else if(res.code == 400){
                                //恢复loading
                                this.loading = false;
                                //刷新视图
                                this.getSession()
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
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
            //修改密码
            changePassword(){
                //制造参数
                let params = {
                    "id": this.id,
                    "old_password": this.passwordForm.old_password,
                    "password": this.passwordForm.password,
                }
                //表单校验
                this.$refs.passwordForm.validate(valid => {
                    //成功
                    if(valid){
                        this.loading = true
                        Ajax.ChangePassword(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //恢复loading
                                this.loading = false;
                                //回到查看框
                                this.nowBox = 'show'
                                //成功提示框
                                this.$success({
                                    title: res.data,
                                });
                            }else if(res.code == 400){
                                //恢复loading
                                this.loading = false;
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
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
    };
</script>

<style lang="scss" scoped>
    .UserInfo{
        width: 100%;
        height: 100%;
        background-color: white;
        padding: 10px 20px 20px 20px;
        border-radius: 40px;
        .topBox{
            display: flex;
            justify-content: space-between;
            margin: 10px 5px 20px 5px;
        }
        .bottomBox{
            text-align: right;
            padding-right: 17px;
            margin: 20px 5px 10px 5px;
        }
    } 
</style>