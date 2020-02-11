<template>
  <div id="app">
    <div v-if="!loaded" class="spinner-container">
      <pulse-loader color="#FFC0CB" size="50px"></pulse-loader>
    </div>
    <div v-else class="app-container">
      <Home
        v-if="authenticated"
        v-bind:baseUrl="baseUrl"
        v-bind:items="items"
        v-bind:size="size"
      />
      <Login v-else v-bind:baseUrl="baseUrl" />
    </div>
  </div>
</template>

<script>
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
export default {
  components: {
    PulseLoader,
    Home,
    Login
  },
  name: "App",
  data: () => ({
    baseUrl: "http://localhost:8080/api",
    loaded: false,
    authenticated: false,
    size: null,
    items: []
  }),
  beforeMount: async function() {
    try {
      this.getItemListSize();
      const result = await fetch(
        this.baseUrl + "/item?page=0&size=" + this.size,
        {
          credentials: "include"
        }
      );
      if (result.status === 200) {
        this.items = await result.json();
        this.authenticated = true;
        this.loaded = true;
        return;
      }
      throw "Unauthenticated";
    } catch (err) {
      this.authenticated = false;
      this.loaded = true;
    }
  },
  methods: {
    getItemListSize: function() {
      switch (true) {
        case window.innerWidth > 1800:
          this.size = 25;
          break;
        case window.innerWidth > 1450:
          this.size = 20;
          break;
        case window.innerWidth > 1145:
          this.size = 15;
          break;
        case window.innerWidth > 690:
          this.size = 10;
          break;
        default:
          this.size = 5;
          break;
      }
    }
  }
};
</script>

<style>
#app,
.app-container {
  height: 100%;
  width: 100%;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.spinner-container {
  height: 100%;
  padding-top: 40vh;
}
</style>
