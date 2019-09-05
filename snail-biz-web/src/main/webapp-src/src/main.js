import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';
import axios from 'axios'

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'zh',
    messages
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title ='蓝海豚管理后台';
    if (to.path === '/login') {
        next();
    }else{
        axios.get('heartbeat', {}).then((res) => {
            let data = res.data;
            if(data.code === '0'){
                if (data.data === 1){
                    next('/login');
                }else{
                    next();
                }
            }else{
                next('/login');
            }
        }).catch(e => {
            console.error({e});
            next('/login');
        })
    }
});

new Vue({
    router,
    i18n,
    render: h => h(App)
}).$mount('#app');
