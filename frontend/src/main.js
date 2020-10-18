// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from 'axios'
import qs from 'qs'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import { Loading,Message } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import cookie from 'vue-cookie'
Vue.prototype.$cookie = cookie;

const http = axios.create({
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
  },
  method: 'post',
  baseURL: process.env.API,
  transformRequest: [function (data) {
    return qs.stringify(data);
  }]
})

Vue.prototype.$api = process.env.API;
Vue.prototype.$apk = process.env.DOWNLOAD_APK_URL;
Vue.prototype.$http = http;
Vue.prototype.$user = {
  token:null
};
let token = cookie.get('token');
if(token){
  Vue.prototype.$user.token = token;
}
let loading;
http.interceptors.request.use(
  function (config) {
    if (Vue.prototype.$user.token) {
      config.headers['Authorization'] = Vue.prototype.$user.token
    }
    loading = Loading.service({
      lock: true,
      text: '加载中……',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    return config;
  },
  function (error) {
    if(loading)loading.close();
    return Promise.reject(error);
  }
);

http.interceptors.response.use(
  function (res) {
    if(loading)loading.close();
    if(res.data.code !== 0){
      if(res.data.code===305){
        router.push('/');
        cookie.delete('token')
      }
      Message.error(res.data.msg);
    }
    return res;
  },
  function (error) {
    if(loading)loading.close();
    return Promise.reject(error);
  }
);

Vue.use(ElementUI);

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
