(function(e){function t(t){for(var r,o,i=t[0],s=t[1],u=t[2],l=0,d=[];l<i.length;l++)o=i[l],Object.prototype.hasOwnProperty.call(c,o)&&c[o]&&d.push(c[o][0]),c[o]=0;for(r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r]);f&&f(t);while(d.length)d.shift()();return a.push.apply(a,u||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],r=!0,o=1;o<n.length;o++){var i=n[o];0!==c[i]&&(r=!1)}r&&(a.splice(t--,1),e=s(s.s=n[0]))}return e}var r={},o={app:0},c={app:0},a=[];function i(e){return s.p+"js/"+({}[e]||e)+"."+{"chunk-2d21a3d2":"93ab7fc5","chunk-2d22d746":"6d88259c","chunk-35a1626b":"47f0519f","chunk-5cd91346":"d08a45de","chunk-5fb7cbdd":"8c336bdc"}[e]+".js"}function s(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.e=function(e){var t=[],n={"chunk-35a1626b":1,"chunk-5cd91346":1,"chunk-5fb7cbdd":1};o[e]?t.push(o[e]):0!==o[e]&&n[e]&&t.push(o[e]=new Promise((function(t,n){for(var r="css/"+({}[e]||e)+"."+{"chunk-2d21a3d2":"31d6cfe0","chunk-2d22d746":"31d6cfe0","chunk-35a1626b":"8aa87b35","chunk-5cd91346":"7f387d08","chunk-5fb7cbdd":"725bc17d"}[e]+".css",c=s.p+r,a=document.getElementsByTagName("link"),i=0;i<a.length;i++){var u=a[i],l=u.getAttribute("data-href")||u.getAttribute("href");if("stylesheet"===u.rel&&(l===r||l===c))return t()}var d=document.getElementsByTagName("style");for(i=0;i<d.length;i++){u=d[i],l=u.getAttribute("data-href");if(l===r||l===c)return t()}var f=document.createElement("link");f.rel="stylesheet",f.type="text/css",f.onload=t,f.onerror=function(t){var r=t&&t.target&&t.target.src||c,a=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");a.code="CSS_CHUNK_LOAD_FAILED",a.request=r,delete o[e],f.parentNode.removeChild(f),n(a)},f.href=c;var h=document.getElementsByTagName("head")[0];h.appendChild(f)})).then((function(){o[e]=0})));var r=c[e];if(0!==r)if(r)t.push(r[2]);else{var a=new Promise((function(t,n){r=c[e]=[t,n]}));t.push(r[2]=a);var u,l=document.createElement("script");l.charset="utf-8",l.timeout=120,s.nc&&l.setAttribute("nonce",s.nc),l.src=i(e);var d=new Error;u=function(t){l.onerror=l.onload=null,clearTimeout(f);var n=c[e];if(0!==n){if(n){var r=t&&("load"===t.type?"missing":t.type),o=t&&t.target&&t.target.src;d.message="Loading chunk "+e+" failed.\n("+r+": "+o+")",d.name="ChunkLoadError",d.type=r,d.request=o,n[1](d)}c[e]=void 0}};var f=setTimeout((function(){u({type:"timeout",target:l})}),12e4);l.onerror=l.onload=u,document.head.appendChild(l)}return Promise.all(t)},s.m=e,s.c=r,s.d=function(e,t,n){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)s.d(n,r,function(t){return e[t]}.bind(null,r));return n},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="/",s.oe=function(e){throw console.error(e),e};var u=window["webpackJsonp"]=window["webpackJsonp"]||[],l=u.push.bind(u);u.push=t,u=u.slice();for(var d=0;d<u.length;d++)t(u[d]);var f=l;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";n("85ec")},"175e":function(e,t,n){},"1af1":function(e,t,n){},3786:function(e,t,n){"use strict";n.d(t,"d",(function(){return i})),n.d(t,"c",(function(){return s})),n.d(t,"b",(function(){return l})),n.d(t,"a",(function(){return d}));var r=n("1da1"),o=(n("96cf"),n("b775")),c=n("4328"),a=n.n(c);function i(e,t){var n=a.a.stringify({username:e,password:t});return Object(o["a"])({url:"/auth/sign/in",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:n})}function s(){return u.apply(this,arguments)}function u(){return u=Object(r["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(o["a"])({url:"/auth/refresh",method:"post"}));case 1:case"end":return e.stop()}}),e)}))),u.apply(this,arguments)}function l(){return Object(o["a"])({url:"/auth/get/refresh/least/seconds",method:"get"})}function d(){return Object(o["a"])({url:"/auth/get/expire/at",method:"get"})}},"3ba8":function(e,t,n){},"3d81":function(e,t,n){"use strict";n("a134")},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("2b0e"),o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},c=[],a=(n("034f"),n("2877")),i={},s=Object(a["a"])(i,o,c,!1,null,null,null),u=s.exports,l=n("a18c"),d=n("1da1"),f=(n("96cf"),n("5f87")),h=["/login"];l["a"].beforeEach(function(){var e=Object(d["a"])(regeneratorRuntime.mark((function e(t,n,r){var o;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:o=Object(f["a"])(),o?"/login"===t.path?r({path:"/"}):r():-1!==h.indexOf(t.path)?r():r("/login");case 2:case"end":return e.stop()}}),e)})));return function(t,n,r){return e.apply(this,arguments)}}());var p=n("5c96"),v=n.n(p),m=(n("0fae"),n("175e"),n("99af"),{debug:null,info:null,warn:null,error:null}),g="production";m.debug=function(e){"development"===g&&console.log("%c".concat((new Date).toISOString()," --- DEBUG: ").concat(e),"color: #5c962c")},m.info=function(e){"development"===g&&console.log("%c".concat((new Date).toISOString()," ---  INFO: ").concat(e),"color: #000000")},m.warn=function(e){"development"===g&&console.log("%c".concat((new Date).toISOString()," ---  WARN: ").concat(e),"color: #a68a0d")},m.error=function(e){"development"===g&&console.log("%c".concat((new Date).toISOString()," --- ERROR: ").concat(e),"color: #f0524f")},r["default"].config.productionTip=!1,r["default"].prototype.log=m,r["default"].use(v.a),new r["default"]({router:l["a"],render:function(e){return e(u)}}).$mount("#app")},"5f87":function(e,t,n){"use strict";n.d(t,"a",(function(){return a})),n.d(t,"c",(function(){return i})),n.d(t,"b",(function(){return s}));var r=n("852e"),o=n.n(r),c="Authorization";function a(){return o.a.get(c)}function i(e){return o.a.set(c,e)}function s(){return o.a.remove(c)}},"7cb5":function(e,t,n){"use strict";n("ec65")},"7e51":function(e,t,n){"use strict";n("1af1")},"85ec":function(e,t,n){},a134:function(e,t,n){},a18c:function(e,t,n){"use strict";n("d3b7"),n("3ca3"),n("ddb0");var r=n("2b0e"),o=n("8c4f"),c=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"layout"},[n("Header",{staticClass:"header-container"}),n("div",{staticClass:"body-container"},[n("Menu",{staticClass:"menu-container"}),n("Content",{staticClass:"content-container"})],1)],1)},a=[],i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"header"},[n("div",{staticClass:"logo"},[n("router-link",{attrs:{to:"/"}},[e._v("Mapping")])],1),n("div",{staticClass:"nav-btn-container"}),n("div",{staticClass:"nav-user-container"},[n("el-dropdown",{attrs:{size:"small",trigger:"click"}},[n("span",{staticClass:"el-dropdown-link nav-user-avatar"},[n("i",{staticClass:"nav-btn el-icon-user"})]),n("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[n("el-dropdown-item",{nativeOn:{click:function(t){return e.handleSignOut.apply(null,arguments)}}},[e._v("Sign out")])],1)],1)],1)])},s=[],u=(n("99af"),n("5f87")),l=n("3786"),d={name:"Header",data:function(){return{defaultRefreshLessSeconds:120,serverRefreshLessSeconds:null,lastTokenExpireAt:null,getServerRefreshLessSecondsFailedTimes:0,getLastTokenExpireAtFailedTimes:0,doRefreshLock:!0}},created:function(){var e=this;this.getTokenExpireAt(),this.getServerRefreshLessSeconds(),setTimeout((function(){e.doRefresh()}),1e4)},methods:{doRefresh:function(){var e=this;setTimeout((function(){var t=Object(u["a"])();void 0!==t&&null!==t&&""!==t?e.doRefreshLock&&(e.doRefreshLock=!1,e.checkRefreshTiming()?(e.log.debug("start refreshing the token"),Object(l["c"])().then((function(t){if("20000"===t.code){var n=t.data;e.log.debug("the new token: ".concat(n)),null!==n&&""!==n&&(Object(u["c"])(n),e.log.debug("save the new token"),e.getTokenExpireAt(),e.getServerRefreshLessSeconds())}e.doRefreshLock=!0,e.doRefresh()})).catch((function(t){e.doRefreshLock=!0,e.doRefresh()}))):(e.log.debug("do not need refresh the token"),e.doRefreshLock=!0,e.doRefresh())):e.$router.push("/login")}),1e4)},checkRefreshTiming:function(){var e=new Date,t=this.lastTokenExpireAt;if(null===t||t<=0)return this.getLastTokenExpireAtFailedTimes<5&&(this.getTokenExpireAt(),this.getLastTokenExpireAtFailedTimes+=1),!1;if(t>e.getTime()){var n=this.serverRefreshLessSeconds;null===n&&(n<=0&&(this.getServerRefreshLessSecondsFailedTimes=6),n=this.defaultRefreshLessSeconds,this.getServerRefreshLessSecondsFailedTimes<5&&(this.getServerRefreshLessSeconds(),this.getServerRefreshLessSecondsFailedTimes+=1));var r=(t-e.getTime())/1e3;return this.log.debug("expires in ".concat(r," seconds, refresh the token ").concat(n," seconds before expiration")),r<n}return!1},getTokenExpireAt:function(){var e=this;Object(l["a"])().then((function(t){e.lastTokenExpireAt=t.data,e.log.debug("expiration time at: ".concat(new Date(t.data).toISOString(),", timestamp: ").concat(t.data))}))},getServerRefreshLessSeconds:function(){var e=this;Object(l["b"])().then((function(t){e.serverRefreshLessSeconds=t.data,e.log.debug("minimum seconds refresh: ".concat(t.data))}))},handleSignOut:function(){Object(u["b"])(),this.$router.push("/login")}}},f=d,h=(n("b181"),n("2877")),p=Object(h["a"])(f,i,s,!1,null,"ac29cd9c",null),v=p.exports,m=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[e._v("footer")])},g=[],b={name:"Footer"},k=b,w=Object(h["a"])(k,m,g,!1,null,"2b48f4c5",null),S=w.exports,O=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"menu"},[n("div",{staticClass:"menu-collapse"},[n("div",{on:{click:function(t){e.isCollapse=!0}}},[e.isCollapse?e._e():n("i",{staticClass:"el-icon-arrow-left"})]),n("div",{on:{click:function(t){e.isCollapse=!1}}},[e.isCollapse?n("i",{staticClass:"el-icon-arrow-right"}):e._e()])]),n("el-menu",{staticClass:"el-menu-vertical-demo menu-body",attrs:{"background-color":"#333","default-active":"1-4-1","text-color":"#ffffff",collapse:e.isCollapse},on:{close:e.handleClose,open:e.handleOpen,select:e.handleSelect}},[n("el-menu-item",{attrs:{index:"/overview"}},[n("i",{staticClass:"el-icon-view"}),n("span",{attrs:{slot:"title"},slot:"title"},[e._v("Overview")])]),n("el-menu-item",{attrs:{index:"/client"}},[n("i",{staticClass:"el-icon-user"}),n("span",{attrs:{slot:"title"},slot:"title"},[e._v("Client")])])],1)],1)},y=[],C={name:"Menu",data:function(){return{isCollapse:!1,screenWidth:document.documentElement.clientWidth}},created:function(){this.adaptScreenSize()},mounted:function(){this.getScreenWidth()},watch:{screenWidth:function(e){this.log.debug(e),this.adaptScreenSize()}},methods:{handleOpen:function(e,t){},handleClose:function(e,t){},handleSelect:function(e,t){this.$router.push(e)},getScreenWidth:function(){var e=this;window.onresize=function(){return function(){e.screenWidth=document.documentElement.clientWidth}()}},adaptScreenSize:function(){this.isCollapse=this.screenWidth<768}}},j=C,x=(n("7e51"),Object(h["a"])(j,O,y,!1,null,"06a98678",null)),R=x.exports,T=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"content"},[n("router-view",{key:e.key})],1)},E=[],L={name:"Content",computed:{key:function(){return this.$route.path}}},_=L,A=(n("3d81"),Object(h["a"])(_,T,E,!1,null,"72c6a33a",null)),P=A.exports,F={name:"Index",components:{Header:v,Footer:S,Menu:R,Content:P}},$=F,M=(n("7cb5"),Object(h["a"])($,c,a,!1,null,"acce9624",null)),D=M.exports;r["default"].use(o["a"]);var W=o["a"].prototype.push;o["a"].prototype.push=function(e){return W.call(this,e).catch((function(e){return e}))};var I=[{path:"/login",component:function(){return n.e("chunk-35a1626b").then(n.bind(null,"a55b"))}},{path:"/",component:D,children:[{path:"/",component:function(){return n.e("chunk-2d21a3d2").then(n.bind(null,"bb51"))}},{path:"/about",component:function(){return n.e("chunk-2d22d746").then(n.bind(null,"f820"))}},{path:"/client",component:function(){return n.e("chunk-5fb7cbdd").then(n.bind(null,"7b94"))}},{path:"/overview",component:function(){return n.e("chunk-5cd91346").then(n.bind(null,"2226"))}}]}],z=new o["a"]({routes:I});t["a"]=z},b181:function(e,t,n){"use strict";n("3ba8")},b775:function(e,t,n){"use strict";n("d3b7");var r,o=n("bc3a"),c=n.n(o),a=n("5c96"),i=n("a18c"),s=n("5f87"),u="production";r="development"===u?"http://127.0.0.1:18000":"";var l=c.a.create({baseURL:r,timeout:5e3});l.interceptors.request.use((function(e){return e.headers["Authorization"]=Object(s["a"])(),e}),(function(e){return Promise.reject(e)})),l.interceptors.response.use((function(e){return e.data}),(function(e){try{var t=e.response.status;if(!(t>=200&&t<500))return Promise.reject(e.response.data);var n=e.response.data.code;if("40001"!==n&&"40002"!==n&&"40003"!==n&&"40004"!==n&&"40005"!==n&&"40006"!==n)return Promise.reject(e.response.data);a["Message"].warning("login"),Object(s["b"])(),i["a"].push("/login")}catch(r){return a["Message"].error(r||"Has error"),Promise.reject(e.response.data)}})),t["a"]=l},ec65:function(e,t,n){}});
//# sourceMappingURL=app.5b8c6bde.js.map