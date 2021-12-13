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
                <a-tabs default-active-key="1" @change="callback">
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
                                        style="width:100%"/>
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
                                @click="boxJump('creat')">
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

                                <a-form-model-item ref="checkpassword" label="确认密码" prop="checkpassword">
                                    <a-input-password
                                        type="password"
                                        v-model="passwordForm.checkpassword"
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
                                @click="boxJump('creat')">
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
    import Ajax from "../../../api/index";
    import _ from 'lodash';
    export default {
        name: "userinfo",
        components: {},
        data() {
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

                editForm: [],
                editRules: [

                ],

                passwordForm: [],
                passwordRules: [

                ],
            };
        },
        computed:{

        },
        created(){
            this.getSession()
        },
        mounted(){
            
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userinfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
                console.log('userinfo:',this,{username,nickname,email,id,level,enable})
            },
            //跳转编辑框
            boxJump(where){
                if(where == 'edit'){
                    this.nowBox = 'edit'
                    this.editForm = JSON.parse(sessionStorage.getItem('userinfo'))
                }else if(where == 'show'){
                    this.nowBox = 'show'
                }
            },
            //tabs跳转
            callback(value){
                console.log("value:",value)
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