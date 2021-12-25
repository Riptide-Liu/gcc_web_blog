<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<style lang="scss">
    #app {
        font-family: "Avenir", Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        width: 100%;
        height: 100%;
        color: #2c3e50;
        min-width: 1200px;
    }

    html,body{
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
    }

    .ant-form-item-label-left{
        width: 65px !important;
    }

    .ant-form-horizontal>.ant-form-item .ant-col-18{
        width: calc(100% - 123px);
    }

    .ant-modal-title{
        text-align: center !important;
    }
</style>
