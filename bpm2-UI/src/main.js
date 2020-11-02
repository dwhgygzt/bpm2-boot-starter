import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui'
import VuePlugin from '../packages' // 引入自定义插件
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
// import './styles/index.scss' // global css

Vue.use(ElementUI, { locale })
Vue.use(VuePlugin)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
