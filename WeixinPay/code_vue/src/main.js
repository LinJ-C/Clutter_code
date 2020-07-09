import Vue from 'vue'
import App from './App.vue'
import router from './router'
import QRCode from 'qrcodejs2'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios,axios)
Vue.config.productionTip = false
Vue.prototype.qrCode = QRCode
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
