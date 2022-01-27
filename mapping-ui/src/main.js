import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './permission';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '../public/css/element-variables.scss';
import {log} from './log'

Vue.config.productionTip = false;

Vue.prototype.log = log;

Vue.use(ElementUI);

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
