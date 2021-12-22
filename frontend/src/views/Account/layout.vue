<template>
    <div class="Login">
        <!-- 背景框 -->
        <div class="background">
            <div class="box">
                <!-- Logo框 -->
                <div class="logoBox">
                    <img 
                        src="../../assets/Images/login_logo.png"
                        style="width: 150px; height: 150px">
                    <span class="logoText">GCCBlog</span>
                </div>
                <!-- 表单背景框 -->
                <div class="formBox">
                    <router-view></router-view>
                </div>
            </div>
        </div>
        
    </div>
</template>

<script>
    import Ajax from "../../api/index";
    export default {
        name:'login',
        data() {
            return {
                
            };
        },
        computed:{
            
        },
        created(){
            
        },
        mounted(){

        },
        methods:{
            
        }
    }
</script>

<style lang="scss" scoped>
    .Login{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        .background{
            width: 100%;
            height: 100%;
            background: url(../../assets/Images/Start_page@3x.png) no-repeat;
            background-size: cover;
            background-attachment: fixed;
            background-position: center center;
            
            // background: #c8d4dc;
            .box{
                position: absolute;
                margin-top: 50px;
                left: 50%;
                transform: translate(-50%); //绝对定位回调
                .logoBox{
                    text-align: center;
                    margin: 20px 0;
                    .logoText{
                        display: block;
                        font-size: 30px;
                        font-weight: bold;
                        color: rgba(0,0,0,0.3),#232f3d;
                    }
                }
                .formBox{
                    width: 350px;
                    padding: 50px;
                    background-color: rgba(0,0,0,0.3);
                    .signUpBox{
                        margin-top: 10px
                    }
                }
            }
        }
    }
</style>