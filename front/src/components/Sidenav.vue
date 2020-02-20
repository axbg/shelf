<template>
  <div style="width:100%;height:100%;">
    <div class="sidenav pale-pink">
      <md-list>
        <md-list-item>
          <md-avatar>
            <img v-bind:src="photo" alt="user-photo" />
          </md-avatar>
          <span class="md-list-item-text">{{ firstname }}</span>
        </md-list-item>
      </md-list>
      <md-field>
        <md-icon>search</md-icon>
        <label>Search</label>
        <md-input v-model="search" class="half-sized accent-pink"></md-input>
      </md-field>
      <md-list>
        <md-ripple class="pointer">
          <md-list-item @click="reset">
            <md-icon class="accent-pink">cancel</md-icon>
            <span class="md-list-item-text accent-pink">Reset Credentials</span>
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
            <img v-bind:src="photo" alt="user-picture" />
          </md-avatar>
        </md-list-item>
      </md-list>
      <md-field>
        <label style="margin-left: 15px;">Search</label>
        <md-input v-model="search" class="half-sized accent-pink"></md-input>
      </md-field>
      <md-list class="left-margined">
        <md-ripple class="pointer">
          <md-list-item @click="reset">
            <md-icon class="accent-pink">cancel</md-icon>
          </md-list-item>
        </md-ripple>
        <md-ripple class="pointer">
          <md-list-item @click="logout">
            <md-icon class="accent-pink">logout</md-icon>
          </md-list-item>
        </md-ripple>
      </md-list>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sidenav",
  props: ["baseUrl"],
  data: () => ({
    firstname: "",
    photo: "",
    search: null,
    searchDelay: null
  }),
  beforeMount: function() {
    this.firstname = window.localStorage.getItem("firstname");
    this.photo = window.localStorage.getItem("photo");
  },
  mounted: async function() {
    if (!this.firstname && !this.photo) {
      const response = await fetch(this.baseUrl + "/user", {
        credentials: "include"
      });
      const profile = await response.json();

      window.localStorage.setItem("firstname", profile.firstName);
      window.localStorage.setItem("photo", profile.photo);
      this.firstname = profile.firstName;
      this.photo = profile.photo;
    }
  },
  methods: {
    reset: async function() {
      await fetch(this.baseUrl + "/user/reset", {
        method: "POST",
        credentials: "include"
      });
      this.clearSession();
    },
    logout: async function() {
      await fetch(this.baseUrl + "/user/logout", {
        method: "POST",
        credentials: "include"
      });
      this.clearSession();
    },
    clearSession: function() {
      window.localStorage.removeItem("firstname");
      window.localStorage.removeItem("photo");
      location.reload();
    }
  },
  watch: {
    search: function() {
      if (this.searchDelay != null) {
        clearTimeout(this.searchDelay);
      }
      this.searchDelay = setTimeout(() => {
        this.$emit("search-result", this.search);
      }, 700);
    }
  }
};
</script>

<style>
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
.pale-pink {
  background-color: #fff7f8;
}
.accent-pink {
  color: #d23669;
}
.half-sized {
  width: 50%;
}
.md-list-item-content > .md-icon:last-child {
  margin-left: 0px !important;
}
.left-margined {
  margin-left: 8px !important;
}
@media only screen and (max-width: 900px) {
  .sidenav {
    display: none;
  }
  .sidenav-short {
    display: block;
  }
}
</style>
