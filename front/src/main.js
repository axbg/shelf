import Vue from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import VueParticles from "vue-particles";
import GSignInButton from "vue-google-signin-button";
import InfiniteLoading from "vue-infinite-loading";

Vue.config.productionTip = false;

Vue.use(VueMaterial);
Vue.use(VueParticles);
Vue.use(GSignInButton);
Vue.use(InfiniteLoading, {
  slots: {
    noResults: "That's all for now 😍",
    noMore: "That's all for now 😍"
  }
});

new Vue({
  render: h => h(App)
}).$mount("#app");
