import Vue from 'vue'
import App from './App.vue'
import router from './router'
import AntDesign from 'ant-design-vue'
import echarts from 'echarts'

import './utils/Js/fd_logger'
import 'ant-design-vue/dist/antd.css';

Vue.use(AntDesign);
Vue.prototype.$echarts = echarts

//控制台打印当前项目信息
window.app = {
    version: '',
    frontendAuthor: '',
    backendAuthor: '',
};
window.app.version = "v1.0.0"
window.app.frontendAuthor = "陈家洛"
window.app.backendAuthor = "刘华东、黄家俊"
console.log("%c当前版本:","color: #1dbfac; font-size: 30px; font-weight: bold",window.app.version)
console.log("%c前端负责人:","color: #FF9900; font-size: 20px; font-weight: bold",window.app.frontendAuthor)
console.log("%c后端负责人:","color: #FF3300; font-size: 20px; font-weight: bold",window.app.backendAuthor)

// 全局获取缓存数据
Vue.prototype.resetSetItem = function(key, newVal){
    if(key === 'userInfo'){
        // 创建一个StorageEvent事件
        let newStorageEvent = document.createEvent('StorageEvent');
        const storage = {
            setItem: function(k, val){
                sessionStorage.setItem(k, val);
                // 初始化创建的事件
                newStorageEvent.initStorageEvent('setItem', false, false, k, null, val, null, null);
                // 派发对象
                window.dispatchEvent(newStorageEvent)
            }
        }
        return storage.setItem(key, newVal);
    }
  },

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
