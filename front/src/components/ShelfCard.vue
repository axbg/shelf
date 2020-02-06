<template>
  <div @click="openPage">
    <md-card v-bind:id="id" class="pale-pink" md-with-hover>
      <md-card-header>
        <md-card-header-text class="big-title">
          <p>
            {{ title }}
          </p>
        </md-card-header-text>
        <md-card-media style="margin: 0 auto;">
          <img src="/img/icons/favicon-32x32.png" alt="Avatar" />
        </md-card-media>
      </md-card-header>
      <md-card-content class="card-content card-title">
        <md-card-header-text>
          <p>
            {{ title }}
          </p>
        </md-card-header-text>
      </md-card-content>
      <md-card-actions md-alignment="right">
        <md-button @click="remove" style="color: #d23669">remove</md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script>
export default {
  name: "shelf-card",
  props: {
    id: Number,
    title: String,
    url: String,
    photo: String,
    topic: String
  },
  methods: {
    openPage: function() {
      alert(this.url);
    },
    remove: function(event) {
      event.stopPropagation();
      alert("removing");
    }
  }
};
</script>

<style>
.md-card-header {
  padding-bottom: 0px !important;
  user-select: none;
}
.big-title {
  display: block;
  font-size: 1.2em;
  overflow-y: scroll;
  overflow-x: hidden;
  height: 100px;
  padding: 2px;
}
.big-title::-webkit-scrollbar {
  display: none;
}
.card-title {
  display: none;
  font-size: 16px;
  height: 130px;
  overflow: hidden;
}

@media only screen and (max-width: 400px) {
  .big-title {
    display: none;
  }
  .card-title {
    display: block;
  }
}
</style>
