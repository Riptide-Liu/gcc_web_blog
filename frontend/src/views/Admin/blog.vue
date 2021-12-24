<template>
    <div class="AdminBlog">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">博文列表</span>
            <a-button
                v-if="nowBox == 'list'"
                type="primary"
                class="topButton"
                @click="boxJump('add')">
                新增博文
            </a-button>
            <a-button
                v-else-if="nowBox == 'add'"
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
                        <a-popconfirm
                            title="是否确定删除这篇博文？"
                            ok-text="确定"
                            cancel-text="取消"
                            @confirm="deleteBlog(record)">
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
            <!-- 编写框 -->
            <div class="addBox" v-else-if="nowBox == 'add'">
                <a-form-model 
                    ref="addForm" :model="addForm" :rules="editRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">                
                    <a-form-model-item ref="title" label="博文名称" prop="title">
                        <a-input
                            type="text"
                            v-model="addForm.title"
                            placeholder="博文名称"
                            style="width:100%"/>
                    </a-form-model-item>
                    <a-form-model-item ref="topic" label="博文分类" prop="topic">
                        <a-select 
                            v-model="addForm.topic"
                            placeholder="博文分类"
                            style="width:100%">
                            <a-select-option v-for="item in typeOptions" 
                                :key="item.id"
                                :value="item.id">
                                {{item.topic}}
                            </a-select-option>
                        </a-select>
                    </a-form-model-item>
                    <a-form-model-item ref="blogAbout" label="博文内容" prop="blogAbout">
                        <mavon-editor
                            style="z-index: 1"
                            @save="addSaveBlog"
                            @change="addUpdateBlog"
                            ref="editor"
                            v-model="blogAddAbout">
                        </mavon-editor>
                    </a-form-model-item>
                </a-form-model>
            </div>
        </div>
        <!-- 底部按钮 -->
        <div class="bottomBox">
            <a-button
                v-if="nowBox == 'add'"
                type="primary"
                class="bottomButton"
                :loading="setLoading"
                @click="addBlog()">
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
            destroyOnClose
            :maskClosable="false"
            :confirm-loading="setLoading"
            @ok="editBlog()">
            <!-- 编辑框 -->
            <div class="editBox">
                <a-form-model 
                    ref="editForm" :model="editForm" :rules="editRules" 
                    :label-col="labelCol" :wrapper-col="wrapperCol" labelAlign="left">
                    <a-form-model-item ref="title" label="博文名称" prop="title">
                        <a-input
                            type="text"
                            v-model="editForm.title"
                            placeholder="博文名称"
                            style="width:100%"/>
                    </a-form-model-item>
                    <a-form-model-item ref="topic" label="博文分类" prop="topic">
                        <a-select 
                            v-model="editForm.topic"
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
                            style="z-index: 1"
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
    export default {
        name: "adminblog",
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
                blogAddDraft: '',
                blogAddAbout: '',
                blogAddAboutHtml: '',
                blogEditDraft: '',
                blogEditAbout: '',
                blogEditAboutHtml: '',

                tableLoading: false,
                setLoading: false,
                blogEditVisible: false,
                
                labelCol: { span: 3 },
                wrapperCol: { span: 18 },

                blogListData: [],
                blogListColumns: [
                    { title: '博文ID', dataIndex: 'id', key: 'id', width: 150, align: 'center' },
                    { title: '博文名', dataIndex: 'title', key: 'title', width: 150, align: 'center' },
                    { title: '博文所属分类', dataIndex: 'topic', key: 'topic', width: 150, align: 'center' },
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

                addForm: { title: '', },
                editForm: {},
                editRules: {
                    title: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    topic: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                    content: [{ required: true, message:'该项不能为空', trigger: "blur" }],
                },
                typeListData: [],
                typeOptions: [],
            };
        },
        mounted(){
            //根据权限等级显示不同表格内容
            if(this.level == 2 && (this.nickname == '' || this.nickname == null)){
                this.blogListColumns.splice(2,0,{ title: '作者', dataIndex: 'username', key: 'username', width: 150, align: 'center' },)
            }else if(this.level == 2 && (this.nickname != '' || this.nickname != null)){
                this.blogListColumns.splice(2,0,{ title: '作者', dataIndex: 'nickname', key: 'nickname', width: 150, align: 'center' },)
            }
        },
        created(){
            this.getSession()
            this.draftOrNot()
            this.getBlogList()
            this.getTypeList()
        },
        methods:{
            //存取session
            getSession(){
                let {username,nickname,email,id,level,enable,} = JSON.parse(sessionStorage.getItem('userInfo'));
                Object.assign(this,{username,nickname,email,id,level,enable})
            },
            //判断是否存在草稿
            draftOrNot(){
                let cd = window.localStorage.getItem('blogAddDraft')
                let ed = window.localStorage.getItem('blogEditDraft')
                if(cd != '' || cd != null){
                    this.blogAddAbout = cd
                }else if(ed != '' || ed != null){
                    this.blogEditAbout = ed
                }
            },
            //获取博客列表
            getBlogList(){
                //制造参数
                let params = {
                    "id": this.id
                }
                Ajax.GetBlogList(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.blogListData = res.data
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //获取分类列表
            getTypeList(){
                Ajax.GetType().then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //赋值
                        this.typeOptions = res.data
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //跳转新建框
            boxJump(where){
                if(where == 'add'){
                    this.nowBox = 'add'
                }else if(where == 'list'){
                    this.nowBox = 'list'
                }
            },
            //博文删除
            deleteBlog(record){
                //制造参数
                let params = {
                    "id": record.id,
                };
                Ajax.DeleteBlog(params).then(res =>{
                    this.$_info('成功返回：',res);
                    if(res.code == 200){
                        //成功提示框
                        this.$success({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getBlogList()
                    }else if(res.code == 400){
                        //失败提示框
                        this.$error({
                            title: res.msg,
                        });
                        //刷新视图
                        this.getBlogList()
                        return false
                    }
                }).catch(err =>{
                    this.$_error('错误信息：',err);
                })
            },
            //打开编辑弹框
            openEdit(record){
                this.editForm = record
                this.blogEditAbout = record.content
                this.blogEditVisible = true
            },
            //编辑博文
            editBlog(){
                //制造参数
                let params = {
                    "id": this.editForm.id,
                    "title": this.editForm.title,
                    "topicID": this.editForm.topic,
                    "userID": this.id,
                    "content": this.blogEditAboutHtml,
                };
                //表单校验
                this.$refs.editForm.validate(valid => {
                    //成功
                    if(valid){
                        this.setLoading = true
                        Ajax.EditBlog(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                                //刷新视图
                                this.getBlogList()
                                //恢复loading
                                this.setLoading = false;
                                //关闭弹窗
                                this.blogEditVisible = false
                                //删除草稿箱
                                window.localStorage.removeItem('blogEditDraft')
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
                                //刷新视图
                                this.getBlogList()
                                //恢复loading
                                this.setLoading = false;
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
            //新建博文保存草稿
            addSaveBlog(markdown, html){
                console.log("Html:",html);
                this.blogAddDraft = markdown
                window.localStorage.setItem('blogAddDraft',markdown)
                //成功提示框
                this.$message.success({
                    content: '草稿保存成功',
                });
            },
            //新建博文动态更新
            addUpdateBlog(markdown, html){
                console.log("MarkDown:",markdown);
                console.log("Html:",html);
                this.blogAddAboutHtml = html
            },
            //编辑博文保存草稿
            editSaveBlog(markdown, html){
                console.log("Html:",html);
                this.blogEditDraft = markdown
                window.localStorage.setItem('blogEditDraft',markdown)
                //成功提示框
                this.$message.success({
                    content: '草稿保存成功',
                });
            },
            //编辑博文动态更新
            editUpdateBlog(markdown, html){
                console.log("MarkDown:",markdown);
                console.log("Html:",html);
                this.blogEditAboutHtml = html
            },
            //新增博文
            addBlog(){
                //制造参数
                let params = {
                    "title": this.addForm.title,
                    "topicID": this.addForm.topic,
                    "userID": this.id,
                    "content": this.blogAddAboutHtml,
                };
                //表单校验
                this.$refs.addForm.validate(valid => {
                    //成功
                    if(valid){
                        this.setLoading = true
                        Ajax.AddBlog(params).then(res =>{
                            this.$_info('成功返回：',res);
                            if(res.code == 200){
                                //成功提示框
                                this.$success({
                                    title: res.msg,
                                });
                                //刷新视图
                                this.getBlogList()
                                this.nowBox = 'list'
                                //恢复loading
                                this.setLoading = false;
                                //删除草稿箱
                                window.localStorage.removeItem('blogAddDraft')
                                //归零输入框
                                this.addForm = []
                                this.blogAddAbout = ''
                            }else if(res.code == 400){
                                //失败提示框
                                this.$error({
                                    title: res.msg,
                                });
                                //刷新视图
                                this.getBlogList()
                                //恢复loading
                                this.setLoading = false;
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
    .AdminBlog{
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