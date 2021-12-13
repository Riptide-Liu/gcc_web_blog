<template>
    <div class="Type">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">博文分类列表</span>
            <a-button
                type="primary"
                class="topButton"
                @click="openModal('creat')">
                <!-- <img src="../assets/Images/add.png"
                    style="width:40px; height:40px;" /> -->
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
                    <a-button 
                        type="danger"
                        size="small"
                        style="margin-bottom: 5px"
                        @click="openBorrow('one',record)">
                        删除
                    </a-button>
                </div>
            </a-table>
        </div>
        <!-- 新建弹窗 -->
        <a-modal
            v-model="creatVisible"
            title="分类信息新增"
            okText="确定"
            cancelText="取消"
            width="500px"
            centered
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="addBorrow()">
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="typeCreatForm" :model="typeCreatForm" :rules="typeCreatRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                    
                    <a-form-model-item ref="typeName" label="分类名" prop="typeName">
                        <a-input
                            type="text"
                            v-model="typeCreatForm.typeName"
                            placeholder="分类名"
                            style="width:300px"/>
                    </a-form-model-item>

                    <a-form-model-item ref="tLevel" label="所属层级" prop="tLevel">
                        <a-select 
                            v-model="typeCreatForm.tLevel"
                            placeholder="所属层级"
                            style="width:300px">
                            <a-select-option v-for="item in levelOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>

                    <a-form-model-item ref="parentType" label="父分类" prop="parentType">
                        <a-input
                            v-if="typeCreatForm.tLevel == 1"
                            type="text"
                            v-model="typeCreatForm.parentType"
                            placeholder="父分类"
                            style="width:300px"
                            disabled/>
                        <a-select 
                            v-else
                            v-model="typeCreatForm.parentType"
                            placeholder="父分类"
                            style="width:300px">
                            <a-select-option v-for="item in parentOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
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
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="addBorrow()">
            <!-- 信息框 -->
            <div style="margin-bottom: 20px">
                <span>分类ID：</span>
                <span>{{typeEditForm.tId}}</span>
            </div>
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="typeEditForm" :model="typeEditForm" :rules="typeEditRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">

                    <a-form-model-item ref="typeName" label="分类名" prop="typeName">
                        <a-input
                            type="text"
                            v-model="typeEditForm.typeName"
                            placeholder="分类名"
                            style="width:300px"/>
                    </a-form-model-item>

                    <a-form-model-item ref="tLevel" label="所属层级" prop="tLevel">
                        <a-select 
                            v-model="typeEditForm.tLevel"
                            placeholder="所属层级"
                            style="width:300px">
                            <a-select-option v-for="item in levelOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>

                    <a-form-model-item ref="parentType" label="父分类" prop="parentType">
                        <a-input
                            v-if="typeEditForm.tLevel == 1"
                            type="text"
                            v-model="typeEditForm.parentType"
                            placeholder="父分类"
                            style="width:300px"
                            disabled/>
                        <a-select 
                            v-else
                            v-model="typeEditForm.parentType"
                            placeholder="父分类"
                            style="width:300px">
                            <a-select-option v-for="item in parentOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
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
    import _ from 'lodash';
    export default {
        name: "type",
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
                creatVisible: false,
                typeEditVisible: false,
                
                labelCol: { span: 4 },
                wrapperCol: { span: 18 },

                typeListData: [
                    {
                        tId: '1', 
                        typeName: '体育',
                        tLevel: '1', 
                        parentType: '无',
                        parentId: '0', 
                    },
                    {
                        tId: '2', 
                        typeName: '足球',
                        tLevel: '2', 
                        parentType: '体育',
                        parentId: '1', 
                    }
                ],
                typeListColumns: [
                    { title: '分类ID', dataIndex: 'tId', key: 'tId', width: 150, align: 'center' },
                    { title: '分类名', dataIndex: 'typeName', key: 'typeName', width: 150, align: 'center' },
                    { title: '所属层级', dataIndex: 'tLevel', key: 'tLevel', width: 150, align: 'center' },
                    { title: '父分类', dataIndex: 'parentType', key: 'parentType', width: 150, align: 'center' },
                    { 
                        title: '操作', 
                        key: 'operation',
                        width: 150, 
                        align: 'center',
                        scopedSlots: { customRender: 'operation' },
                    },
                ],

                typeCreatForm: [],
                typeCreatRules: [],

                typeEditForm: [],
                typeEditRules: [],

                nowType: [],

                levelOptions: [
                    {value:'1',text:'一级'},
                    {value:'2',text:'二级'},
                    {value:'3',text:'三级'},
                ],

                parentOptions: [
                    {value:'1',text:'体育'},
                    {value:'2',text:'政治'},
                    {value:'3',text:'娱乐'},
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
            //打开弹窗
            openModal(how,record){
                if(how == 'creat'){
                    this.creatVisible = true
                    console.log("record：",record)
                }else if(how == 'edit'){
                    this.typeEditVisible = true
                    this.typeEditForm = record
                    console.log("record：",record)
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