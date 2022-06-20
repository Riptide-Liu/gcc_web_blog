<template>
    <div class="Type">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">博文分类列表</span>
            <a-button
                type="primary"
                class="topButton"
                @click="openModal('add')">
                新建分类
            </a-button>
        </div>
        <!-- 表格框 -->
        <div class="tableBox">
            <a-table :columns="typeListColumns" 
                :data-source="typeListData"
                :loading="tableLoading">
                <div class="buttonGroud"
                    slot="operation"
                    slot-scope="text, record">
                    <a-button 
                        type="primary"
                        size="small"
                        style="margin-bottom: 5px"
                        @click="openModal('edit',record)">
                        编辑
                    </a-button>
                    <a-popconfirm
                        title="是否确定删除该分类？"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="deleteType(record)">
                        <a-button 
                            type="danger"
                            size="small"
                            style="margin-bottom: 5px">
                            删除
                        </a-button>
                    </a-popconfirm>
                </div>
            </a-table>
        </div>
        <!-- 新建弹窗 -->
        <a-modal
            v-model="typeAddVisible"
            title="分类信息新增"
            okText="确定"
            cancelText="取消"
            width="500px"
            centered
            destroyOnClose
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="addType()">
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="typeAddForm" :model="typeAddForm" :rules="typeAddRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                    <a-form-model-item ref="topic" label="分类名" prop="topic">
                        <a-input
                            type="text"
                            v-model="typeAddForm.topic"
                            placeholder="分类名"
                            style="width:300px"/>
                    </a-form-model-item>
                    <a-form-model-item ref="topicLevel" label="所属层级" prop="topicLevel">
                        <a-select 
                            v-model="typeAddForm.topicLevel"
                            placeholder="所属层级"
                            style="width:300px"
                            @change="getParent('add')">
                            <a-select-option v-for="item in levelOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>
                    <a-form-model-item ref="parentName" label="父分类" prop="parentName">
                        <a-input
                            v-if="typeAddForm.topicLevel == 1"
                            type="text"
                            v-model="typeAddForm.parentName"
                            placeholder="无"
                            style="width:300px"
                            disabled/>
                        <a-select 
                            v-else
                            v-model="typeAddForm.parentName"
                            placeholder="父分类"
                            style="width:300px"
                            @change="saveParentId('add')">
                            <a-select-option v-for="item in parentOptions" 
                                :key="item.id"
                                :value="item.topic">
                                {{item.topic}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>
                </a-form-model>
            </div>
        </a-modal>
        <!-- 编辑弹窗 -->
        <a-modal
            v-model="typeEditVisible"
            title="分类信息编辑"
            okText="确定"
            cancelText="取消"
            width="500px"
            centered
            destroyOnClose
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="editType()">
            <!-- 信息框 -->
            <div style="margin-bottom: 20px">
                <span>分类ID：</span>
                <span>{{typeEditForm.id}}</span>
            </div>
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="typeEditForm" :model="typeEditForm" :rules="typeEditRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                    <a-form-model-item ref="topic" label="分类名" prop="topic">
                        <a-input
                            type="text"
                            v-model="typeEditForm.topic"
                            placeholder="分类名"
                            style="width:300px"/>
                    </a-form-model-item>
                    <a-form-model-item ref="topicLevel" label="所属层级" prop="topicLevel">
                        <a-select 
                            v-model="typeEditForm.topicLevel"
                            placeholder="所属层级"
                            style="width:300px"
                            @change="getParent('edit')">
                            <a-select-option v-for="item in levelOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>
                    <a-form-model-item ref="parentName" label="父分类" prop="parentName">
                        <a-input
                            v-if="typeEditForm.topicLevel == '一级' || typeEditForm.topicLevel == 1"
                            type="text"
                            v-model="typeEditForm.parentName"
                            placeholder="无"
                            style="width:300px"
                            disabled/>
                        <a-select 
                            v-else
                            v-model="typeEditForm.parentName"
                            placeholder="父分类"
                            style="width:300px"
                            @change="saveParentId('edit')">
                            <a-select-option v-for="item in parentOptions" 
                                :key="item.id"
                                :value="item.id">
                                {{item.topic}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>
                </a-form-model>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name: "type",
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
                typeAddVisible: false,
                typeEditVisible: false,
                
                labelCol: { span: 4 },
                wrapperCol: { span: 18 },

                typeListData: [],
                typeListColumns: [
                    { title: '分类ID', dataIndex: 'id', key: 'id', width: 150, align: 'center' },
                    { title: '分类名', dataIndex: 'topic', key: 'topic', width: 150, align: 'center' },
                    { title: '所属层级', dataIndex: 'topicLevel', key: 'topicLevel', width: 150, align: 'center' },
                    { title: '父分类', dataIndex: 'parentName', key: 'parentName', width: 150, align: 'center' },
                    { 
                        title: '操作', 
                        key: 'operation',
                        width: 150, 
                        align: 'center',
                        scopedSlots: { customRender: 'operation' },
                    },
                ],

                typeAddForm: {
                    parentName: ''
                },
                typeAddRules: {
                    topic: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    topicLevel: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    parentName: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                },

                typeEditForm: {},
                typeEditRules: {
                    topic: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    topicLevel: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    parentName: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                },

                levelOptions: [
                    {value:1,text:'一级'},
                    {value:2,text:'二级'},
                    {value:3,text:'三级'},
                ],

                parentOptions: [],
            };
        },
        created(){
            this.getSession()
            this.getType()
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //获取分类列表
            getType(){
                this.tableLoading = true
                Ajax.GetType().then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        this.typeListData = res.data
                        //转换等级字段数据
                        for(let item of this.typeListData){
                            for(let itemL of this.levelOptions){
                                if(item.topicLevel == itemL.value){
                                    item.topicLevel = itemL.text
                                }else if(item.topicLevel == 1 || item.topicLevel == '一级'){
                                    item.parentName = '无'
                                }
                            }
                        }
                        //恢复loading
                        this.tableLoading = false
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //恢复loading
                        this.tableLoading = false
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //根据所传级别获取父分类
            getParent(which){
                //制造参数
                let params = {}
                if(which == 'add'){
                    params = {
                        "topicLevel": this.typeAddForm.topicLevel
                    }
                }else if(which == 'edit'){
                    for(let item of this.levelOptions){
                        if(this.typeEditForm.topicLevel == item.text){
                            this.typeEditForm.topicLevel = item.value
                        }
                    }
                    params = {
                        "topicLevel": this.typeEditForm.topicLevel
                    }
                }
                Ajax.GetParent(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.parentOptions = res.data
                        //父分类归零
                        if(which == 'add'){
                            this.typeAddForm.parentName = ''
                        }
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.data,
                        });
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //存储父分类ID
            saveParentId(which){
                if(which == 'add'){
                    for(let item of this.parentOptions){
                        if(this.typeAddForm.parentName == item.topic){
                            this.typeAddForm.parentId = item.id
                        }
                    }
                }else if(which == 'edit'){
                    for(let item of this.parentOptions){
                        if(this.typeEditForm.parentName == item.id){
                            this.typeEditForm.parentId = item.id
                        }
                    }
                }
            },
            //新增分类
            addType(){
                let params = {
                    "topic": this.typeAddForm.topic,
                    "topicLevel": this.typeAddForm.topicLevel,
                    "parentId": this.typeAddForm.parentId,
                }
                //表单校验
                this.$refs.typeAddForm.validate(valid => {
                    //成功
                    if(valid){
                        this.setLoading = true
                        Ajax.AddType(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //关闭弹窗
                                this.typeAddVisible = false
                                //刷新视图
                                this.typeAddForm = {parentName: ''},
                                this.getType()
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //刷新视图
                                this.getType()
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
            //编辑分类
            editType(){
                //制造参数
                let params = {
                    "id": this.typeEditForm.id,
                    "topic": this.typeEditForm.topic,
                    "topicLevel": this.typeEditForm.topicLevel,
                    "parentId": this.typeEditForm.parentId,
                };
                //表单校验
                this.$refs.typeEditForm.validate(valid => {
                    //成功
                    if(valid){
                        this.setLoading = true
                        Ajax.EditType(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //关闭弹窗
                                this.typeEditVisible = false
                                //刷新视图
                                this.getType()
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
                                //恢复loading
                                this.setLoading = false;
                                //刷新视图
                                this.getType()
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
            //删除分类
            deleteType(record){
                //制造参数
                let params = {
                    "id": record.id,
                };
                Ajax.DeleteType(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getType()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getType()
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //打开弹窗
            openModal(how,record){
                if(how == 'add'){
                    this.typeAddVisible = true
                }else if(how == 'edit'){
                    this.typeEditVisible = true
                    this.typeEditForm = record
                    this.getParent('edit')
                }
            },
        }
    };
</script>

<style lang="scss" scoped>
    .Type{
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