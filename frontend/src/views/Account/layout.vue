<template>
    <div class="AccountLayout">
        <!-- 背景框 -->
        <div class="background">
            <!-- 主体框 -->
            <div class="mainBox">
                <!-- Logo框 -->
                <div class="logoBox">
                    <img src="../../assets/Images/logo.png"
                        style="width: 200px; height: 200px">
                </div>
                <!-- 表单框 -->
                <div class="formBox">
                    <router-view></router-view>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name:'accountlayout',
    }
</script>

<style lang="scss" scoped>
    .AccountLayout{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        overflow: auto;
        .background{
            width: 100%;
            height: 100%;
            background: url(../../assets/Images/account_background.png) no-repeat;
            background-size: cover;
            background-attachment: fixed;
            background-position: center center;
            .mainBox{
                position: absolute;
                margin-top: 50px;
                left: 50%;
                transform: translate(-50%); //绝对定位回调
                .logoBox{
                    text-align: center;
                    margin: 20px 0;
                }
                .formBox{
                    width: 313px;
                    padding: 30px;
                    background-color: rgba(0,0,0,0.3);
                }
            }
        }
    }
</style>