import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";

const baseUrl = "http://localhost:8080/api";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
    props: { baseUrl: baseUrl }
  },
  {
    path: "/login",
    name: "login",
    component: Login,
    props: { baseUrl: baseUrl }
  }
];

const router = new VueRouter({
  routes
});

export default router;
