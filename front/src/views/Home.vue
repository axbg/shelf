<template>
  <div class="home-container">
    <Sidenav v-bind:baseUrl="baseUrl" @search-result="loadSearchResult" />
    <div id="content" v-if="loaded" class="content">
      <back-to-top />
      <ShelfCard
        v-for="(item, index) in displayItems"
        v-bind:key="index"
        v-bind:id="item.id"
        v-bind:title="item.title"
        v-bind:url="item.url"
        v-bind:photo="item.photo"
        @remove-item="removeItem"
      />
      <infinite-loading
        v-if="displayInfinite"
        @infinite="scrollHandler"
        class="infinite-loader"
        :distance="20"
        spinner="waveDots"
      />
    </div>
    <div v-else class="spinner-container">
      <pulse-loader color="#FFC0CB" size="50px"></pulse-loader>
    </div>
  </div>
</template>

<script>
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import ShelfCard from "@/components/ShelfCard";
import Sidenav from "@/components/Sidenav";
import BackToTop from "@/components/BackToTop";
export default {
  components: {
    PulseLoader,
    ShelfCard,
    Sidenav,
    BackToTop
  },
  name: "home",
  data: () => ({
    page: 1,
    loaded: true,
    displayInfinite: true,
    displayItems: [],
    backupItems: []
  }),
  props: ["baseUrl", "items", "size"],
  beforeMount: function() {
    this.appendLocalItems(this.items);
    if (this.items.length < this.size) {
      this.displayInfinite = false;
    }
  },
  methods: {
    appendLocalItems: function(items) {
      this.backupItems.push(...items);
      this.displayItems = [...this.backupItems];
    },
    load: async function(inBetween) {
      this.loaded = false;
      await inBetween();
      this.loaded = true;
    },
    loadSearchResult: async function(result) {
      if (result !== "") {
        this.displayInfinite = false;
        this.load(async () => {
          const unparsedItems = await fetch(
            this.baseUrl + "/item/search?title=" + result,
            { credentials: "include" }
          );
          this.displayItems = await unparsedItems.json();
        });
      } else {
        this.displayItems = [...this.backupItems];
        this.displayInfinite = true;
      }
    },
    removeItem: async function(itemId) {
      if (confirm("You sure?")) {
        await fetch(this.baseUrl + "/item/" + itemId, {
          method: "DELETE",
          credentials: "include"
        });
        this.displayItems = this.displayItems.filter(
          item => item.id !== itemId
        );
        this.backupItems = this.backupItems.filter(item => item.id !== itemId);
      }
    },
    scrollHandler: async function($state) {
      const requestedItems = await fetch(
        this.baseUrl + "/item?page=" + this.page + "&size=" + this.size,
        { method: "GET", credentials: "include" }
      );

      const fetchedItems = await requestedItems.json();
      this.appendLocalItems(fetchedItems);
      this.page++;
      if (
        typeof fetchedItems === "undefined" ||
        fetchedItems.length < this.size
      ) {
        $state.complete();
      } else {
        $state.loaded();
      }
    }
  }
};
</script>

<style>
.full-sized {
  width: 100%;
  height: 100%;
}
.home-container {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 300px 1fr;
  overflow: hidden;
  color: #d23669;
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
  overflow-y: scroll;
}
.infinite-loader {
  grid-column: 1 / -1;
  height: 60px;
  padding: 0 !important;
  color: red;
}
@media only screen and (max-width: 900px) {
  .home-container {
    grid-template-columns: 100px 1fr;
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
