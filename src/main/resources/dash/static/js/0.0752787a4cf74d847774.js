webpackJsonp([0],{AkbF:function(t,a){},iRIX:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var i={render:function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("div",{staticStyle:{padding:"25px"}},[e("div",{staticClass:"menu-btn"},[e("i",{staticClass:"el-icon-circle-plus-outline"}),t._v(" "),e("div",{staticStyle:{"margin-left":"5px"},on:{click:t.createApp}},[t._v("新建应用")])])]),t._v(" "),e("div",{staticStyle:{position:"absolute",display:"flex",height:"calc(100% - 154px)",width:"100%"}},[0===t.apps.length?e("div",{staticStyle:{"font-size":"24px",color:"#777777","text-align":"center",width:"100%"}},[t._v("什么都没有...\n    ")]):t._e(),t._v(" "),e("div",{staticStyle:{padding:"0 25px 25px 25px",width:"100%",overflow:"auto"}},[e("el-row",{attrs:{gutter:20,justify:"space-between"}},t._l(t.apps,function(a){return e("el-col",{key:a.id,attrs:{xs:12,sm:8,md:6,lg:4,xl:2}},[e("div",{staticClass:"app-card",on:{click:function(e){return t.$router.push("/dash/app/"+a.id)}}},[e("el-image",{staticClass:"el-upload-list__item-thumbnail",staticStyle:{width:"50px",height:"50px","border-radius":"50%",border:"3px solid #dedede","box-shadow":"0px 0px 20px 0px #bdbdbd"},attrs:{slot:"default",fit:"cover",src:t.$api+a.icon},slot:"default"},[e("div",{staticClass:"image-slot",attrs:{slot:"placeholder"},slot:"placeholder"},[t._v("\n                加载中"),e("span",{staticClass:"dot"},[t._v("...")])])]),t._v(" "),e("div",{staticStyle:{"margin-top":"10px"}},[t._v(t._s(a.appName))])],1)])}),1)],1)])])},staticRenderFns:[]};var s=e("VU/8")({name:"AppList",data:function(){return{apps:[]}},mounted:function(){this.loadAppList()},methods:{createApp:function(){var t=this;t.$http.post("/api/dash/app/create").then(function(a){0===a.data.code&&t.$router.push("/dash/app/"+a.data.data.id)})},loadAppList:function(){var t=this;t.$http.post("/api/dash/app/list").then(function(a){0===a.data.code&&(t.apps=a.data.data)})}}},i,!1,function(t){e("AkbF")},"data-v-e32663d8",null);a.default=s.exports}});
//# sourceMappingURL=0.0752787a4cf74d847774.js.map