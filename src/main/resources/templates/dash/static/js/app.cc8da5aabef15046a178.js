webpackJsonp([5],{"9VJf":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n("//Fk"),r=n.n(a),o=n("7+uW"),u=n("mtWM"),i=n.n(u),s=n("mw3O"),c=n.n(s),p={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"bg"},[t("transition",{attrs:{mode:"out-in",name:"fade"}},[t("router-view")],1)],1)},staticRenderFns:[]};var l=n("VU/8")({name:"App",data:function(){return{}}},p,!1,function(e){n("9VJf")},null,null).exports,d=n("/ocq"),f={name:"Login",data:function(){return{userName:"",userPwd:""}},methods:{onLogin:function(){var e=this,t=this;t.$http.post("/api/dash/login",{userName:t.userName,userPwd:t.userPwd}).then(function(n){console.log(t.userName,t.userPwd,n.data.data),0===n.data.code&&(t.$user.token=n.data.data,e.$cookie.set("token",n.data.data,1),t.$router.push("/dash"))})}}},h={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticStyle:{display:"flex","justify-content":"center"}},[n("div",{staticStyle:{"margin-top":"100px"}},[e._m(0),e._v(" "),n("div",[n("el-input",{attrs:{placeholder:"请输入用户名","prefix-icon":"el-icon-user"},model:{value:e.userName,callback:function(t){e.userName=t},expression:"userName"}})],1),e._v(" "),n("div",{staticStyle:{"margin-top":"10px"}},[n("el-input",{attrs:{type:"password",placeholder:"请输入密码","prefix-icon":"el-icon-lock"},model:{value:e.userPwd,callback:function(t){e.userPwd=t},expression:"userPwd"}})],1),e._v(" "),n("div",{staticStyle:{"margin-top":"10px"}},[n("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary"},on:{click:e.onLogin}},[e._v("登录")])],1)])])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("h5",[this._v("AppVersion Management")])])}]},m=n("VU/8")(f,h,!1,null,null,null).exports;o.default.use(d.a);var v=new d.a({routes:[{path:"/",name:"Login",component:m},{path:"/dash",name:"Dashboard",component:function(){return n.e(2).then(n.bind(null,"TGvd"))},redirect:"/dash/app/list",children:[{path:"app/list",component:function(){return n.e(0).then(n.bind(null,"iRIX"))}},{path:"app/:id",component:function(){return n.e(3).then(n.bind(null,"ecVz"))}}]},{path:"*",component:function(){return n.e(1).then(n.bind(null,"5Iau"))}}]}),y=n("zL8q"),g=n.n(y),k=(n("tvR6"),n("K/Lq")),w=n.n(k);o.default.prototype.$cookie=w.a;var $=i.a.create({headers:{"Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"},method:"post",baseURL:"./",transformRequest:[function(e){return c.a.stringify(e)}]});o.default.prototype.$api="./",o.default.prototype.$apk="",o.default.prototype.$http=$,o.default.prototype.$user={token:null};var b=w.a.get("token");b&&(o.default.prototype.$user.token=b);var _=void 0;$.interceptors.request.use(function(e){return o.default.prototype.$user.token&&(e.headers.Authorization=o.default.prototype.$user.token),_=y.Loading.service({lock:!0,text:"加载中……",background:"rgba(0, 0, 0, 0.7)"}),e},function(e){return _&&_.close(),r.a.reject(e)}),$.interceptors.response.use(function(e){return _&&_.close(),0!==e.data.code&&(305===e.data.code&&(v.push("/"),w.a.delete("token")),y.Message.error(e.data.msg)),e},function(e){return _&&_.close(),r.a.reject(e)}),o.default.use(g.a),o.default.config.productionTip=!1,new o.default({el:"#app",router:v,components:{App:l},template:"<App/>"})},tvR6:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.cc8da5aabef15046a178.js.map