<template>
    <div class="Blog">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">博文列表</span>
            <a-button
                v-if="nowBox == 'list'"
                type="primary"
                class="topButton"
                @click="boxJump('creat')">
                新增博文
            </a-button>
            <a-button
                v-else-if="nowBox == 'creat'"
                type="primary"
                class="topButton"
                @click="boxJump('list')">
                返回列表
            </a-button>
        </div>
        <!-- 中部主功能框 -->
        <div class="midBox">
            <!-- 列表框 -->
            <div class="listBox" v-if="nowBox == 'list'">
                <a-table :columns="blogListColumns" 
                    :data-source="blogListData"
                    :loading="tableLoading">
                    <div class="buttonGroud"
                        slot="operation"
                        slot-scope="text, record">
                        <a-button 
                            type="primary"
                            size="small"
                            style="margin-bottom: 5px"
                            @click="openEdit(record)">
                            编辑
                        </a-button>
                        <a-button 
                            type="danger"
                            size="small"
                            style="margin-bottom: 5px"
                            @click="deleteBlog()">
                            删除
                        </a-button>
                    </div>
                </a-table>
            </div>
            <!-- 编写框 -->
            <div class="creatBox" v-else-if="nowBox == 'creat'">
                <mavon-editor
                    @save="creatSaveBlog"
                    @change="creatUpdateBlog"
                    ref="editor"
                    v-model="blogCreatAbout">
                </mavon-editor>
            </div>
        </div>
        <!-- 底部按钮 -->
        <div class="bottomBox">
            <a-button
                v-if="nowBox == 'creat'"
                type="primary"
                class="bottomButton"
                @click="boxJump('creat')">
                保存上传
            </a-button>
        </div>
        <!-- 编辑弹窗 -->
        <a-modal
            v-model="blogEditVisible"
            title="博文编辑"
            okText="提交"
            cancelText="取消"
            width="1200px"
            centered
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="addBorrow()">
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="nowBlog" :model="nowBlog" :rules="editRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                
                    <a-form-model-item ref="blogName" label="博文名称" prop="blogName">
                        <a-input
                            type="text"
                            v-model="nowBlog.blogName"
                            placeholder="博文名称"
                            style="width:100%"/>
                    </a-form-model-item>

                    <a-form-model-item ref="blogType" label="博文分类" prop="blogType">
                        <a-select 
                            v-model="nowBlog.blogType"
                            placeholder="博文分类"
                            style="width:100%">
                            <a-select-option v-for="item in typeOptions" 
                                :key="item.value"
                                :value="item.value">
                                {{item.text}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>

                    <a-form-model-item ref="blogAbout" label="博文内容" prop="blogAbout">
                        <mavon-editor
                            @save="editSaveBlog"
                            @change="editUpdateBlog"
                            ref="editor"
                            v-model="blogEditAbout">
                        </mavon-editor>
                    </a-form-model-item>

                </a-form-model>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    import {mavonEditor} from 'mavon-editor';
    import 'mavon-editor/dist/css/index.css';
    import _ from 'lodash';
    export default {
        name: "blog",
        components: {mavonEditor},
        data() {
            return {
                username: '',
                nickname: '',
                email: '',
                id: '',
                level: '',
                enable: '',

                nowBox: 'list',
                blogCreatAbout: '',
                blogEditAbout: '',

                tableLoading: false,
                setLoading: false,
                blogEditVisible: false,

                
                labelCol: { span: 3 },
                wrapperCol: { span: 18 },

                blogListData: [
                    {
                        bId: '1', 
                        blogName: '体育ABC',
                        blogType: '体育', 
                        blogTypeId: '1', 
                        likesNum: '777',
                        commentNum: '777', 
                    },
                ],
                blogListColumns: [
                    { title: '博文ID', dataIndex: 'bId', key: 'bId', width: 150, align: 'center' },
                    { title: '博文名', dataIndex: 'blogName', key: 'blogName', width: 150, align: 'center' },
                    { title: '博文所属分类', dataIndex: 'blogType', key: 'blogType', width: 150, align: 'center' },
                    { title: '点赞数', dataIndex: 'likesNum', key: 'likesNum', width: 150, align: 'center' },
                    { title: '评论数', dataIndex: 'commentNum', key: 'commentNum', width: 150, align: 'center' },
                    { 
                        title: '操作', 
                        key: 'operation',
                        width: 150, 
                        align: 'center',
                        scopedSlots: { customRender: 'operation' },
                    },
                ],

                nowBlog: [],
                editRules: [],
                typeOptions: [
                    {value:'1',text:'体育'},
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
            //跳转新建框
            boxJump(where){
                if(where == 'creat'){
                    this.nowBox = 'creat'
                }else if(where == 'list'){
                    this.nowBox = 'list'
                }
            },
            //博文删除
            deleteBlog(){
                this.$info({
                    title:"是否确认要删除这篇博文？(按ESC取消)",
                    onOk:() => {
                        
                    }
                })
            },
            //打开编辑弹框
            openEdit(record){
                this.nowBlog = record
                this.blogEditVisible = true
            },
            //新建博文保存
            creatSaveBlog(markdown, html){
                console.log("markdown内容:",markdown);
                console.log("html内容:",html);
            },
            //新建博文更新
            creatUpdateBlog(markdown, html){
                console.log("markdown内容:",markdown);
                console.log("html内容:",html);
            },
            //编辑博文保存
            editSaveBlog(markdown, html){
                console.log("markdown内容:",markdown);
                console.log("html内容:",html);
            },
            //编辑博文更新
            editUpdateBlog(markdown, html){
                console.log("markdown内容:",markdown);
                console.log("html内容:",html);
            },
        }
    };
</script>

<style lang="scss" scoped>
    .Blog{
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
            margin: 20px 5px 10px 5px;
        }
    } 
</style>