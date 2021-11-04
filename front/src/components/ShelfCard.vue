<template>
  <div>
    <md-dialog-prompt
      :md-active.sync="showDialog"
      v-model="localTitle"
      md-title="Change title"
      md-input-maxlength="150"
      md-input-placeholder="localTitle"
      md-confirm-text="Save"
      @md-confirm="dialogConfirm"
    />
    <md-card v-bind:id="id" class="pale-pink" md-with-hover>
      <a class="clickable-card accent-pink" v-bind:href="url" target="blank">
        <md-card-header>
          <md-card-header-text class="big-title">
            <p>
              {{ title }}
            </p>
          </md-card-header-text>
          <md-card-media style="margin: 0 auto">
            <img v-bind:src="photo" alt="Avatar" />
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
          <md-button @click="displayDialog" style="color: #d23669"
            >edit</md-button
          >
          <md-button @click="remove" style="color: #d23669">remove</md-button>
        </md-card-actions>
      </a>
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
    topic: String,
  },
  data: () => ({
    localTitle: "",
    showDialog: false,
  }),
  created: function () {
    this.syncLocal();
  },
  beforeUpdate: function () {
    this.syncLocal();
  },
  methods: {
    syncLocal: function () {
      this.localTitle = this.title;
    },
    displayDialog: function (event) {
      event.preventDefault();
      event.stopPropagation();
      this.showDialog = true;
    },
    dialogConfirm: function (event) {
      if (event !== this.title) {
        this.$emit("edit-item", this.id + "#" + event);
      }
    },
    remove: function (event) {
      event.preventDefault();
      event.stopPropagation();
      this.$emit("remove-item", this.id);
    },
  },
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
.clickable-card {
  text-decoration: none !important;
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
