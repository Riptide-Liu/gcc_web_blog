<template>
    <div class="User">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">用户列表</span>
            <!-- <a-button
                type="primary"
                class="topButton"
                @click="creatUser()">
                新建用户
            </a-button> -->
        </div>
        <!-- 表格框 -->
        <div class="tableBox">
            <a-table :columns="userListColumns" 
                :data-source="userListData"
                :loading="tableLoading">
                <div class="buttonGroud"
                    slot="operation"
                    slot-scope="text, record">
                    <a-button 
                        type="primary"
                        size="small"
                        style="margin-bottom: 5px"
                        @click="openEditModal(record)">
                        编辑
                    </a-button>
                    <a-button 
                        type="danger"
                        size="small"
                        style="margin-bottom: 5px"
                        @click="openBorrow('one',record)">
                        封禁
                    </a-button>
                </div>
            </a-table>
        </div>
        <!-- 编辑弹窗 -->
        <a-modal
            v-model="userEditVisible"
            title="用户信息编辑"
            okText="确定"
            cancelText="取消"
            width="1200px"
            centered
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="addBorrow()">
            <!-- 信息框 -->
            <span>用户ID：</span>
            <span>{{userForm.uId}}</span>
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="userForm" :model="userForm" :rules="userRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                
                    <a-form-model-item ref="userName" label="用户名" prop="userName">
                        <a-input
                            type="text"
                            v-model="userForm.userName"
                            placeholder="用户名"
                            style="width:100%"/>
                    </a-form-model-item>

                    <a-form-model-item ref="uNickname" label="昵称" prop="uNickname">
                        <a-input
                            type="text"
                            v-model="userForm.uNickname"
                            placeholder="昵称"
                            style="width:100%"/>
                    </a-form-model-item>

                    <a-form-model-item ref="uEmail" label="邮箱" prop="uEmail">
                        <a-input
                            type="text"
                            v-model="userForm.uEmail"
                            placeholder="邮箱"
                            style="width:100%"
                            disabled/>
                    </a-form-model-item>

                    <a-form-model-item ref="uPassword" label="密码" prop="uPassword">
                        <a-input-password
                            type="password"
                            v-model="userForm.uPassword"
                            placeholder="密码"
                            style="width:100%"/>
                    </a-form-model-item>

                </a-form-model>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import _ from 'lodash';
    export default {
        name: "user",
        components: {},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                tableLoading: false,
                setLoading: false, 
                userEditVisible: false,
                
                labelCol: { span: 3 },
                wrapperCol: { span: 18 },

                userListData: [
                    {
                        uId: '202100000001', 
                        userName: 'zhangsan.blog',
                        uNickname: 'SanZ', 
                        uEmail: 'zhangsan@163.com', 
                        uPassword: '123456', 
                        blogNumber: '777', 
                    }
                ],
                userListColumns: [
                    { title: '用户ID', dataIndex: 'uId', key: 'uId', width: 150, align: 'center' },
                    { title: '用户名', dataIndex: 'userName', key: 'userName', width: 150, align: 'center' },
                    { title: '昵称', dataIndex: 'uNickname', key: 'uNickname', width: 150, align: 'center' },
                    { title: '邮箱', dataIndex: 'uEmail', key: 'uEmail', width: 150, align: 'center' },
                    // { title: '密码', dataIndex: 'uPassword', key: 'uPassword', width: 150, align: 'center' },
                    { title: '博文数量', dataIndex: 'blogNumber', key: 'blogNumber', width: 150, align: 'center' },
                    { 
                        title: '用户状态', 
                        key: 'enable',
                        width: 150, 
                        align: 'center',
                        scopedSlots: { customRender: 'enable' },
                    },
                    { 
                        title: '操作', 
                        key: 'operation',
                        width: 150, 
                        align: 'center',
                        scopedSlots: { customRender: 'operation' },
                    },
                ],

                userForm: [],
                userRules: [],

                nowUser: [],
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
            //打开编辑弹窗
            openEditModal(record){
                this.userEditVisible = true
                this.userForm = record
            },
        }
    };
</script>

<style lang="scss" scoped>
    .User{
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
    } 
</style>