import Vue from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import VueParticles from "vue-particles";
import GSignInButton from "vue-google-signin-button";

Vue.config.productionTip = false;
Vue.use(VueMaterial);
Vue.use(VueParticles);
Vue.use(GSignInButton);

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
