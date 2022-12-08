import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './vue-router'


const app = createApp(App)

app.use(ElementPlus)
app.use(router)
app.mount('#app')

// 引入vue-pro-table
import Vue3ProTable from "vue3-pro-table";
app.use(Vue3ProTable);

//引入element plus icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}