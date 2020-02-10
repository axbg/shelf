<template>
  <div style="height:100%; width: 100%;">
    <div v-if="!loaded" class="spinner-container">
      <pulse-loader color="#FFC0CB" size="50px"></pulse-loader>
    </div>
    <div v-else class="home-container">
      <div class="sidenav pale-pink">
        <md-list>
          <md-list-item>
            <md-avatar>
              <img src="https://placeimg.com/40/40/people/6" alt="People" />
            </md-avatar>
            <span class="md-list-item-text pointer">Mary Johnson</span>
          </md-list-item>
        </md-list>
        <md-field>
          <md-icon>search</md-icon>
          <label>Search</label>
          <md-input v-model="search" style="width:50%"></md-input>
        </md-field>
        <md-list>
          <md-ripple class="pointer">
            <md-list-item>
              <md-icon>add</md-icon>
              <span class="md-list-item-text">Add New Bookmark</span>
            </md-list-item>
          </md-ripple>
          <md-ripple>
            <md-list-item>
              <md-icon>move_to_inbox</md-icon>
              <span class="md-list-item-text">All</span>
            </md-list-item>
          </md-ripple>
          <md-ripple class="pointer">
            <md-list-item @click="logout">
              <md-icon class="accent-pink">logout</md-icon>
              <span class="md-list-item-text accent-pink">Logout</span>
            </md-list-item>
          </md-ripple>
        </md-list>
      </div>
      <div class="sidenav-short pale-pink">
        <md-list>
          <md-list-item>
            <md-avatar>
              <img src="https://placeimg.com/40/40/people/6" alt="People" />
            </md-avatar>
          </md-list-item>
        </md-list>
        <md-field>
          <label style="margin-left: 15px;">Search</label>
          <md-input v-model="search" style="width:50%;"></md-input>
        </md-field>
        <md-list class="left-margined">
          <md-ripple class="pointer">
            <md-list-item>
              <md-icon>add</md-icon>
            </md-list-item>
          </md-ripple>
          <md-ripple class="pointer">
            <md-list-item>
              <md-icon>move_to_inbox</md-icon>
            </md-list-item>
          </md-ripple>
          <md-ripple class="pointer">
            <md-list-item @click="logout">
              <md-icon class="accent-pink">logout</md-icon>
            </md-list-item>
          </md-ripple>
        </md-list>
      </div>
      <div class="content">
        <ShelfCard
          v-for="(item, index) in items"
          v-bind:key="index"
          v-bind:id="item.id"
          v-bind:title="item.title"
          v-bind:url="item.url"
        />
      </div>
    </div>
  </div>
</template>

<script>
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import ShelfCard from "@/components/ShelfCard";
export default {
  components: {
    PulseLoader,
    ShelfCard
  },
  name: "home",
  props: ["baseUrl"],
  data: () => ({
    search: null,
    loaded: false,
    searchDelay: null,
    items: []
  }),
  beforeMount: async function() {
    try {
      const result = await fetch(this.baseUrl + "/item/", {
        credentials: "include"
      });

      if (result.status === 200) {
        this.items = await result.json();
        this.loaded = true;
        return;
      }

      throw "Unauthenticated";
    } catch (err) {
      this.$router.push("/login");
    }
  },
  methods: {
    deleteItem: function() {},
    logout: async function() {
      await fetch(this.baseUrl + "/user/logout", {
        method: "POST",
        credentials: "include"
      });
      this.$router.push("/login");
    }
  },
  watch: {
    search: function() {
      if (this.searchDelay != null) {
        clearTimeout(this.searchDelay);
      }
      this.searchDelay = setTimeout(() => {
        alert("searched for " + this.search);
      }, 700);
    }
  }
};
</script>

<style>
.spinner-container {
  height: 100%;
  padding-top: 40vh;
}
.home-container {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 300px 1fr;
  overflow: hidden;
  color: #d23669;
}
.sidenav {
  height: 100%;
  width: 100%;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.19), 0 0px 6px rgba(0, 0, 0, 0.2);
  padding: 15px;
}
.sidenav-short {
  display: none;
  height: 100%;
  width: 100%;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.19), 0 0px 6px rgba(0, 0, 0, 0.2);
  padding: 10px;
}
.pointer {
  cursor: pointer;
  user-select: none;
}
.content {
  height: 100%;
  width: 100%;
  display: grid;
  padding: 30px;
  column-gap: 20px;
  row-gap: 20px;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  grid-auto-rows: 180px;
  overflow-y: auto;
}
.pale-pink {
  background-color: #fff7f8;
}
.accent-pink {
  color: #d23669;
}
.md-list-item-content > .md-icon:last-child {
  margin-left: 0px !important;
}
.left-margined {
  margin-left: 8px !important;
}
@media only screen and (max-width: 900px) {
  .home-container {
    grid-template-columns: 100px 1fr;
  }
  .sidenav {
    display: none;
  }
  .sidenav-short {
    display: block;
  }
}
@media only screen and (max-width: 400px) {
  .content {
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    grid-auto-rows: 300px;
    padding: none;
    row-gap: 0;
  }
  .big-title {
    display: none;
  }
  .card-title {
    display: block;
  }
}
</style>
