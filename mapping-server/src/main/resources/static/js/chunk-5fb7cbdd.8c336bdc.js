(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5fb7cbdd"],{"159b":function(t,e,i){var a=i("da84"),n=i("fdbc"),l=i("785a"),o=i("17c2"),r=i("9112"),s=function(t){if(t&&t.forEach!==o)try{r(t,"forEach",o)}catch(e){t.forEach=o}};for(var c in n)n[c]&&s(a[c]&&a[c].prototype);s(l)},"17c2":function(t,e,i){"use strict";var a=i("b727").forEach,n=i("a640"),l=n("forEach");t.exports=l?[].forEach:function(t){return a(this,t,arguments.length>1?arguments[1]:void 0)}},"4b1c":function(t,e,i){},"4de4":function(t,e,i){"use strict";var a=i("23e7"),n=i("b727").filter,l=i("1dde"),o=l("filter");a({target:"Array",proto:!0,forced:!o},{filter:function(t){return n(this,t,arguments.length>1?arguments[1]:void 0)}})},"56f4":function(t,e,i){"use strict";i("4b1c")},"7b94":function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"client"},[i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{lg:10,md:24,sm:24,xl:10,xs:24}},[i("el-card",{staticClass:"client-card"},[i("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[i("span",[t._v("Client")]),i("i",{staticClass:"icon-btn el-icon-plus",staticStyle:{float:"right",padding:"3px 0"},on:{click:t.handleDialogClientAdd}})]),i("el-table",{ref:"clientTable",staticStyle:{width:"100%"},attrs:{data:t.clientList,"highlight-current-row":""},on:{"current-change":t.handleClientCurrentChange}},[i("el-table-column",{attrs:{type:"index",width:"50"}}),i("el-table-column",{attrs:{label:"id","min-width":"180",property:"id","show-overflow-tooltip":""}}),i("el-table-column",{attrs:{label:"name","min-width":"100",property:"name"}}),i("el-table-column",{attrs:{fixed:"right",align:"center",label:"operation",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("i",{staticClass:"icon-btn el-icon-edit",on:{click:function(i){return t.handleClientUpdate(e.row)}}}),i("el-popconfirm",{attrs:{"cancel-button-text":"cancel","confirm-button-text":"confirm",icon:"el-icon-info","icon-color":"red",title:"Are you sure?"},on:{confirm:function(i){return t.handleClientDelete(e.row)}}},[i("i",{staticClass:"icon-btn el-icon-delete",attrs:{slot:"reference"},slot:"reference"})])]}}])})],1)],1)],1),i("el-col",{attrs:{lg:14,md:24,sm:24,xl:14,xs:24}},[i("el-row",[i("el-col",{attrs:{span:24}},[t.ifProxySetting?t._e():i("el-card",{staticClass:"proxy-card"},[i("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[i("span",[t._v("Proxy")]),i("i",{staticClass:"icon-btn el-icon-plus",staticStyle:{float:"right",padding:"3px 0"},on:{click:t.handleProxyAddBtnClick}})]),i("el-table",{ref:"singleTable",staticStyle:{width:"100%"},attrs:{data:t.proxyList,"highlight-current-row":""},on:{"current-change":t.handleProxyCurrentChange}},[i("el-table-column",{attrs:{type:"index",width:"50"}}),i("el-table-column",{attrs:{label:"name",property:"name"}}),i("el-table-column",{attrs:{label:"server port",property:"serverPort"}}),i("el-table-column",{attrs:{label:"client port",property:"clientPort"}}),i("el-table-column",{attrs:{label:"client host",property:"clientHost"}}),i("el-table-column",{attrs:{align:"center",fixed:"right",label:"operation",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("i",{staticClass:"icon-btn el-icon-edit",on:{click:function(i){return t.handleProxyUpdate(e.row)}}}),i("i",{staticClass:"icon-btn el-icon-setting",on:{click:function(i){return t.handleProxySetting(e.row)}}}),i("el-popconfirm",{attrs:{"cancel-button-text":"cancel","confirm-button-text":"confirm",icon:"el-icon-info","icon-color":"red",title:"Are you sure?"},on:{confirm:function(i){return t.handleProxyDelete(e.row)}}},[i("i",{staticClass:"icon-btn el-icon-delete",attrs:{slot:"reference"},slot:"reference"})])]}}],null,!1,526018214)})],1)],1),t.ifProxySetting?i("el-card",{staticClass:"setting-card"},[i("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[i("span",[t._v("Setting")]),i("el-tooltip",{attrs:{content:"add rules",effect:"dark",placement:"bottom"}},[i("div",{attrs:{slot:"content"},slot:"content"},[i("p",[i("span",[t._v("case 1")]),t._v(" *"),i("br"),t._v(" /api/user/*"),i("br")]),i("p",[i("span",[t._v("case 2")]),t._v(" RE:/api/get/user/[0-9]+"),i("br")])]),i("i",{staticClass:"icon-btn el-icon-info"})]),i("el-tooltip",{attrs:{content:"close",effect:"dark",placement:"bottom"}},[i("i",{staticClass:"icon-btn el-icon-close",staticStyle:{float:"right",padding:"3px 3px"},on:{click:t.handleProxySettingClose}})]),i("el-tooltip",{attrs:{content:"add rules",effect:"dark",placement:"top"}},[i("i",{staticClass:"icon-btn el-icon-plus",staticStyle:{float:"right",padding:"3px 3px"},on:{click:t.handleProxySettingAdd}})])],1),i("el-form",{attrs:{"label-width":"auto"}},t._l(t.allowRulesFormData,(function(e,a){return i("el-form-item",{staticClass:"allow-item",attrs:{label:"rule"+(e.index+1)}},[i("el-input",{staticClass:"allow-input",attrs:{autocomplete:"off"},model:{value:e.path,callback:function(i){t.$set(e,"path",i)},expression:"item.path"}}),i("div",{staticClass:"allow-input-btn"},[i("i",{staticClass:"allow-input-icon icon-btn el-icon-delete",on:{click:function(i){return t.handleProxySettingDelete(e.index)}}})])],1)})),1),i("el-button",{on:{click:function(e){return t.handleProxySettingSubmit("allowRulesForm")}}},[t._v("save")])],1):t._e()],1),i("el-col",{staticStyle:{padding:"20px 0"},attrs:{span:24}},[i("el-card",{staticClass:"control-card"},[i("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[i("span",[t._v("Control")])]),i("el-button",{attrs:{icon:"el-icon-refresh-left"},on:{click:t.handleRefresh}})],1)],1)],1)],1)],1),i("el-dialog",{staticClass:"client-dialog",attrs:{visible:t.dialogClientVisible,title:"Add Client",width:"30%"},on:{"update:visible":function(e){t.dialogClientVisible=e}}},[i("el-form",{attrs:{model:t.dialogClientFormData}},[i("el-form-item",{attrs:{label:"id","label-width":"60px"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogClientFormData.id,callback:function(e){t.$set(t.dialogClientFormData,"id",e)},expression:"dialogClientFormData.id"}})],1),i("el-form-item",{attrs:{label:"name","label-width":"60px"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogClientFormData.name,callback:function(e){t.$set(t.dialogClientFormData,"name",e)},expression:"dialogClientFormData.name"}})],1)],1),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(e){t.dialogClientVisible=!1}}},[t._v("cancel")]),i("el-button",{attrs:{type:"primary"},on:{click:t.handleDialogClientSubmit}},[t._v("submit")])],1)],1),i("el-dialog",{staticClass:"proxy-dialog",attrs:{visible:t.dialogProxyVisible,title:"Add Proxy",width:"30%"},on:{"update:visible":function(e){t.dialogProxyVisible=e}}},[i("el-form",{attrs:{model:t.dialogProxyFormData,"label-width":"90px"}},[i("el-form-item",{attrs:{label:"name"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogProxyFormData.name,callback:function(e){t.$set(t.dialogProxyFormData,"name",e)},expression:"dialogProxyFormData.name"}})],1),i("el-form-item",{attrs:{label:"server port"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogProxyFormData.serverPort,callback:function(e){t.$set(t.dialogProxyFormData,"serverPort",e)},expression:"dialogProxyFormData.serverPort"}})],1),i("el-form-item",{attrs:{label:"client port"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogProxyFormData.clientPort,callback:function(e){t.$set(t.dialogProxyFormData,"clientPort",e)},expression:"dialogProxyFormData.clientPort"}})],1),i("el-form-item",{attrs:{label:"client host"}},[i("el-input",{attrs:{autocomplete:"off"},model:{value:t.dialogProxyFormData.clientHost,callback:function(e){t.$set(t.dialogProxyFormData,"clientHost",e)},expression:"dialogProxyFormData.clientHost"}})],1)],1),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(e){t.dialogProxyVisible=!1}}},[t._v("cancel")]),i("el-button",{attrs:{type:"primary"},on:{click:t.handleDialogProxySubmit}},[t._v("submit")])],1)],1)],1)},n=[],l=(i("d3b7"),i("159b"),i("b0c0"),i("4de4"),i("e9c4"),i("b775")),o=i("4328"),r=i.n(o);function s(){return Object(l["a"])({url:"/client/list",method:"get"})}function c(t){return Object(l["a"])({url:"/client/add",method:"post",header:{"Content-Type":"application/json"},data:t})}function d(t){return Object(l["a"])({url:"/client/update",method:"post",header:{"Content-Type":"application/json"},data:t})}function u(t){var e=r.a.stringify({id:t});return Object(l["a"])({url:"/client/delete",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:e})}function f(){return Object(l["a"])({url:"/client/refresh",method:"post"})}var h={name:"Client",data:function(){return{metaData:[],clientList:[],proxyList:[],currentClientSelect:null,clientDeleteVisible:!1,dialogClientVisible:!1,dialogClientIsUpdate:!0,dialogClientFormData:{},currentProxySelectIndex:null,proxyDeleteVisible:!1,dialogProxyVisible:!1,dialogProxyIsUpdate:!0,dialogProxyFormData:{},currentProxySelectIndexForSetting:null,ifProxySetting:!1,allowRulesFormData:[]}},created:function(){this.init()},watch:{metaData:{handler:function(t){var e=this;this.clientList=[],t.forEach((function(t){e.clientList.push({id:t.id,name:t.name})}));var i=this.currentClientSelect;null!==i&&""!==i&&(this.proxyList=this.findProxyList(i))},deep:!0}},methods:{init:function(){this.loadMetaData()},loadMetaData:function(){var t=this;s().then((function(e){e.data&&e.data.forEach((function(t){var e=0;t.proxyList&&t.proxyList.forEach((function(t){if(t.index=e++,t.allows){var i=0;t.allows.forEach((function(t){t.index=i++}))}}))})),t.metaData=e.data}))},getClient:function(t){var e=this.metaData.filter((function(e){return e.id===t}));return null===e||0===e.length?null:e[0]},findProxyList:function(t){var e=[],i=this.metaData.filter((function(e){return e.id===t}));return null!==i&&i.length>0&&(e=i[0].proxyList),e},handleClientCurrentChange:function(t){null!==t&&""!==t&&(this.currentClientSelect=t.id,this.proxyList=this.findProxyList(t.id),this.handleProxySettingClose())},handleDialogClientAdd:function(){this.dialogClientVisible=!0,this.dialogClientIsUpdate=!1},handleClientUpdate:function(t){this.dialogClientFormData={id:t.id,name:t.name,proxyList:this.findProxyList(t.id)},this.dialogClientVisible=!0,this.dialogClientIsUpdate=!0},handleClientDelete:function(t){var e=this;this.clientDeleteVisible=!1;var i=t.id;u(i).then((function(t){e.$message({type:"success",message:t.message}),e.loadMetaData()})).catch((function(t){e.$message({type:"error",message:t.message})}))},handleDialogClientSubmit:function(){var t=this;this.dialogClientVisible=!1;var e={id:this.dialogClientFormData.id,name:this.dialogClientFormData.name};this.dialogClientIsUpdate?(e.proxyList=this.dialogClientFormData.proxyList,d(e).then((function(e){t.$message({type:"success",message:e.message}),t.loadMetaData()})).catch((function(e){t.$message(e)}))):c(e).then((function(e){t.$message({type:"success",message:e.message}),t.loadMetaData()})).catch((function(e){t.$message(e)})),this.dialogClientFormData={}},handleProxyCurrentChange:function(t){null!==t&&""!==t&&(this.currentProxySelectIndex=t.index,this.log.debug(JSON.stringify(t)))},handleProxyAddBtnClick:function(){null===this.currentClientSelect||""===this.currentClientSelect?this.$message({type:"warning",message:"please select one of client."}):(this.dialogProxyVisible=!0,this.dialogProxyIsUpdate=!1)},handleProxyUpdate:function(t){this.log.debug(JSON.stringify(t)),this.dialogProxyFormData={name:t.name,serverPort:t.serverPort,clientHost:t.clientHost,clientPort:t.clientPort,allows:t.allows},this.dialogProxyVisible=!0,this.dialogProxyIsUpdate=!0},handleDialogProxySubmit:function(){var t=this;this.dialogProxyVisible=!1;var e=this.currentClientSelect;if(null!==e&&""!==e){var i=this.getClient(e);if(null!==i){var a={name:this.dialogProxyFormData.name,serverPort:this.dialogProxyFormData.serverPort,clientHost:this.dialogProxyFormData.clientHost,clientPort:this.dialogProxyFormData.clientPort,allows:this.dialogProxyFormData.allows},n=this.findProxyList(e);null===n&&(n=[]);this.currentProxySelectIndex;if(this.dialogProxyIsUpdate){for(var l=0;l<n.length;l++)if(n[l].index===this.currentProxySelectIndex){n[l]=a;break}i.proxyList=n,this.log.debug("update; ".concat(JSON.stringify(n)))}else n.push(a),i.proxyList=n,this.log.debug("add; ".concat(JSON.stringify(n)));d(i).then((function(e){t.$message({type:"success",message:e.message}),t.loadMetaData()})).catch((function(t){})),this.dialogProxyFormData={}}else this.$message({type:"warning",message:"client not found."})}else this.$message({type:"warning",message:"please select one of client."})},handleProxyDelete:function(t){var e=this;this.proxyDeleteVisible=!1;var i=this.currentClientSelect;if(null!==i&&""!==i){var a=this.getClient(i);if(null!==a){var n=this.findProxyList(i);null===n&&(n=[]);var l=this.currentProxySelectIndex;n=n.filter((function(t){return t.index!==l})),a.proxyList=n,d(a).then((function(t){e.$message({type:"success",message:t.message}),e.loadMetaData()})).catch((function(t){}))}else this.$message({type:"warning",message:"client not found."})}else this.$message({type:"warning",message:"please select one of client."})},handleProxySetting:function(t){this.log.debug("current proxy: ".concat(JSON.stringify(t))),this.currentProxySelectIndexForSetting=t.index,this.allowRulesFormData=t.allows,this.ifProxySetting=!0},handleProxySettingClose:function(){this.ifProxySetting=!1,this.dialogAllowFormData=[],this.currentProxySelectIndexForSetting=null},handleProxySettingAdd:function(){null===this.allowRulesFormData&&(this.allowRulesFormData=[]),this.allowRulesFormData.push({index:this.allowRulesFormData.length,path:null}),this.log.debug(JSON.stringify(this.allowRulesFormData))},handleProxySettingDelete:function(t){this.allowRulesFormData=this.allowRulesFormData.filter((function(e){return e.index!==t}));var e=0;this.allowRulesFormData.forEach((function(t){t.index=e++})),this.log.debug("allowRulesFormData: ".concat(JSON.stringify(this.allowRulesFormData)))},handleProxySettingSubmit:function(t){var e=this,i=this.currentClientSelect;if(null!==i&&""!==i){var a=this.getClient(i);if(null!==a){var n=this.findProxyList(i);if(null!==n){var l=this.allowRulesFormData;if(l)for(var o=0;o<l.length;o++){var r=l[o];if(void 0===r.path||null===r.path||""===r.path)return void this.$message({type:"warning",message:"rule".concat(o+1," can not be empty.")})}var s=this.currentProxySelectIndexForSetting;n.forEach((function(t){t.index===s&&(t.allows=l)})),this.log.debug("client: ".concat(JSON.stringify(a))),d(a).then((function(t){e.$message({type:"success",message:t.message}),e.handleProxySettingClose(),e.loadMetaData()})).catch((function(t){e.log.error(JSON.stringify(t))}))}else this.$message({type:"warning",message:"proxy is not exist."})}else this.$message({type:"warning",message:"client not found."})}else this.$message({type:"warning",message:"please select one of client."})},handleRefresh:function(){var t=this;f().then((function(e){console.log(e),t.$message({type:"success",message:e.message})})).catch((function(e){t.log.error(JSON.stringify(e))}))}}},g=h,m=(i("56f4"),i("2877")),p=Object(m["a"])(g,a,n,!1,null,"73180fef",null);e["default"]=p.exports},a640:function(t,e,i){"use strict";var a=i("d039");t.exports=function(t,e){var i=[][t];return!!i&&a((function(){i.call(null,e||function(){throw 1},1)}))}},b0c0:function(t,e,i){var a=i("83ab"),n=i("5e77").EXISTS,l=i("e330"),o=i("9bf2").f,r=Function.prototype,s=l(r.toString),c=/function\b(?:\s|\/\*[\S\s]*?\*\/|\/\/[^\n\r]*[\n\r]+)*([^\s(/]*)/,d=l(c.exec),u="name";a&&!n&&o(r,u,{configurable:!0,get:function(){try{return d(c,s(this))[1]}catch(t){return""}}})},b727:function(t,e,i){var a=i("0366"),n=i("e330"),l=i("44ad"),o=i("7b0b"),r=i("07fa"),s=i("65f0"),c=n([].push),d=function(t){var e=1==t,i=2==t,n=3==t,d=4==t,u=6==t,f=7==t,h=5==t||u;return function(g,m,p,y){for(var x,b,v=o(g),C=l(v),P=a(m,p),D=r(C),w=0,S=y||s,F=e?S(g,D):i||f?S(g,0):void 0;D>w;w++)if((h||w in C)&&(x=C[w],b=P(x,w,v),t))if(e)F[w]=b;else if(b)switch(t){case 3:return!0;case 5:return x;case 6:return w;case 2:c(F,x)}else switch(t){case 4:return!1;case 7:c(F,x)}return u?-1:n||d?d:F}};t.exports={forEach:d(0),map:d(1),filter:d(2),some:d(3),every:d(4),find:d(5),findIndex:d(6),filterReject:d(7)}},e9c4:function(t,e,i){var a=i("23e7"),n=i("da84"),l=i("d066"),o=i("2ba4"),r=i("e330"),s=i("d039"),c=n.Array,d=l("JSON","stringify"),u=r(/./.exec),f=r("".charAt),h=r("".charCodeAt),g=r("".replace),m=r(1..toString),p=/[\uD800-\uDFFF]/g,y=/^[\uD800-\uDBFF]$/,x=/^[\uDC00-\uDFFF]$/,b=function(t,e,i){var a=f(i,e-1),n=f(i,e+1);return u(y,t)&&!u(x,n)||u(x,t)&&!u(y,a)?"\\u"+m(h(t,0),16):t},v=s((function(){return'"\\udf06\\ud834"'!==d("\udf06\ud834")||'"\\udead"'!==d("\udead")}));d&&a({target:"JSON",stat:!0,forced:v},{stringify:function(t,e,i){for(var a=0,n=arguments.length,l=c(n);a<n;a++)l[a]=arguments[a];var r=o(d,null,l);return"string"==typeof r?g(r,p,b):r}})}}]);
//# sourceMappingURL=chunk-5fb7cbdd.8c336bdc.js.map