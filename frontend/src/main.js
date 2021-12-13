import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import AntDesign from 'ant-design-vue'
import echarts from 'echarts'
import _ from 'lodash'


import './utils/Js/fd_logger'
import 'ant-design-vue/dist/antd.css';
import './assets/font_alibaba/iconfont.css'

Vue.use(AntDesign);
Vue.prototype.$echarts = echarts

window.app = {
    version:''
};
window.app.version = "v1.0.0"
console.log("%c当前版本:","color: #1dbfac; font-size: 30px; font-weight: bold",window.app.version)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
