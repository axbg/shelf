<template>
  <div id="app">
    <div v-if="!loaded" class="spinner-container">
      <pulse-loader color="#FFC0CB" size="50px"></pulse-loader>
    </div>
    <div v-else class="app-container">
      <HomeView
        v-if="authenticated"
        v-bind:baseUrl="baseUrl"
        v-bind:items="items"
        v-bind:size="size"
      />
      <LoginView v-else v-bind:baseUrl="baseUrl" @login="attemptLogin" />
    </div>
  </div>
</template>

<script>
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
export default {
  components: {
    PulseLoader,
    HomeView,
    LoginView,
  },
  name: "App",
  data: function () {
    return {
      baseUrl: this.$baseUrl,
      loaded: false,
      authenticated: false,
      size: null,
      items: [],
    };
  },
  beforeMount: async function () {
    try {
      this.getItemListSize();
      const result = await fetch(
        this.baseUrl + "/item?page=0&size=" + this.size,
        {
          credentials: "include",
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
      this.loaded = true;
    }
  },
  methods: {
    getItemListSize: function () {
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
    },
    attemptLogin: async function (userData) {
      try {
        this.loaded = false;

        const [id_token, firstname, photo] = userData.split("#");

        const loginResponse = await fetch(this.baseUrl + "/user/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
          body: JSON.stringify({
            gToken: id_token,
          }),
        });

        if (loginResponse.status === 200) {
          window.localStorage.setItem("firstname", firstname);
          window.localStorage.setItem("photo", photo);
          location.reload();
        }
      } catch (err) {
        alert("An error occurred while connecting to the back-end");
        this.loaded = true;
      }
    },
  },
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
::-webkit-scrollbar {
  width: 2px;
}

::-webkit-scrollbar-track {
  background: #fff7f8;
}

::-webkit-scrollbar-thumb,
::-webkit-scrollbar-thumb:hover {
  background: #d23669;
}

.spinner-container {
  height: 100%;
  padding-top: 40vh;
}
</style>
