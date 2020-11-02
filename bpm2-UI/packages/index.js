import FlowDesigner from './components/FlowDesigner'
import Vue from 'vue'
import './styles/index.scss' // global css
const components = [FlowDesigner]
function install (Vue) {
  components.map(component => {
    Vue.component(component.name, component)
  })
}

if (window.Vue) {
  install(window.Vue)
} else {
  install(Vue)
}

export default install