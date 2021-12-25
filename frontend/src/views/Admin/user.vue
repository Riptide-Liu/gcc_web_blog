<template>
    <div class="User">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">用户列表</span>
        </div>
        <!-- 表格框 -->
        <div class="tableBox">
            <a-table :columns="userListColumns" 
                :data-source="userListData"
                :loading="tableLoading">
                <div class="ableText"
                    slot="enable"
                    slot-scope="text, record">
                    <span v-if="record.enable == '1'">
                        正常
                    </span>
                    <span v-else-if="record.enable == '0'">
                        封禁
                    </span>
                </div>
                <div class="buttonGroud"
                    slot="operation"
                    slot-scope="text, record">
                    <a-popconfirm
                        v-if="record.enable == 1 && record.nickname != '管理员'"
                        title="是否封禁该用户？"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="disableUser(record)">
                        <a-button 
                            type="danger"
                            size="small"
                            style="margin-bottom: 5px">
                            封禁
                        </a-button>
                    </a-popconfirm>
                    <a-popconfirm
                        v-if="record.enable == 0"
                        title="是否解封封该用户？"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="enableUser(record)">
                        <a-button 
                            type="success"
                            size="small"
                            style="margin-bottom: 5px">
                            解封
                        </a-button>
                    </a-popconfirm>
                    <a-button 
                        type="primary"
                        size="small"
                        style="margin-bottom: 5px"
                        @click="openEditModal(record)">
                        编辑
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
            width="500px"
            centered
            destroyOnClose
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="editUserInfo()">
            <!-- 信息框 -->
            <div style="margin-bottom: 20px">
                <span>用户ID：</span>
                <span>{{userForm.id}}</span>
            </div>
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="userForm" :model="userForm" :rules="userRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">

                    <a-form-model-item ref="nickname" label="昵称" prop="nickname">
                        <a-input
                            type="text"
                            v-model="userForm.nickname"
                            placeholder="昵称"
                            style="width:300px"/>
                    </a-form-model-item>

                    <a-form-model-item ref="email" label="邮箱" prop="email">
                        <a-input
                            type="text"
                            v-model="userForm.email"
                            placeholder="邮箱"
                            style="width:300px"/>
                    </a-form-model-item>

                </a-form-model>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name: "user",
        data() {
            // 自定义邮箱校验
            const cheakEmail = async(rule, value, callback) => {
                let emailRules = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
                if( !emailRules.test(this.userForm.email)){
                    return callback(new Error('邮箱格式不正确'));
                }
            };
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
                
                labelCol: { span: 4 },
                wrapperCol: { span: 18 },

                userListData: [],
                userListColumns: [
                    { title: '用户ID', dataIndex: 'id', key: 'id', width: 150, align: 'center' },
                    { title: '用户名', dataIndex: 'username', key: 'username', width: 150, align: 'center' },
                    { title: '昵称', dataIndex: 'nickname', key: 'nickname', width: 150, align: 'center' },
                    { title: '邮箱', dataIndex: 'email', key: 'email', width: 150, align: 'center' },
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
                userRules: {
                    nickname: [{ required: true, message:'昵称不能为空', trigger: "blur" }],
                    email: [
                        { required: true, message:'邮箱不能为空', trigger: "blur" },
                        { required: true, validator: cheakEmail, trigger: "blur" },
                    ],
                },
            };
        },
        created(){
            this.getSession()
            this.getUserList()
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //获取用户列表
            getUserList(){
                this.tableLoading = true
                Ajax.GetUserList().then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.userListData = res.data
                        //恢复loading
                        this.tableLoading = false;
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //恢复loading
                        this.tableLoading = false;
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //封禁用户
            disableUser(record){
                //制造参数
                let params = {
                    "id": record.id
                }
                Ajax.DisableUser(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.data,
                        });
                        //刷新视图
                        this.getUserList()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getUserList()
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //解封用户
            enableUser(record){
                //制造参数
                let params = {
                    "id": record.id
                }
                Ajax.EnableUser(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.data,
                        });
                        //刷新视图
                        this.getUserList()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getUserList()
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //打开编辑弹窗
            openEditModal(record){
                this.userEditVisible = true
                this.userForm = record
            },
            //编辑用户信息
            editUserInfo(){
                let params = {
                    "id": this.userForm.id,
                    "nickname": this.userForm.nickname,
                    "email": this.userForm.email,
                }
                //表单校验
                this.$refs.userForm.validate(valid => {
                    //成功
                    if(valid){
                        this.setLoading = true
                        Ajax.EditUserInfo(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //关闭弹窗
                                this.userEditVisible = false
                                //刷新视图
                                this.getUserList()
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //刷新视图
                                this.getUserList()
                                return false
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