<template>
    <div class="UserInfo">
        <!-- 顶部按钮 -->
        <div class="topBox">
            <span style="font-size: 20px">个人中心</span>
        </div>
    </div>
</template>

<script>
    import Ajax from "../../api/index";
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
    } 
</style>