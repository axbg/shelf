<template>
  <div class="home-container">
    <div class="sidenav">
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
        <md-list-item>
          <md-icon>add</md-icon>
          <span class="md-list-item-text pointer">Add New Bookmark</span>
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <md-icon style="color: black">move_to_inbox</md-icon>
          <span class="md-list-item-text pointer">All</span>
        </md-list-item>
      </md-list>
      <p class="logout">Logout</p>
    </div>
    <div class="sidenav-short">
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
      <md-list>
        <md-list-item>
          <md-icon class="pointer">add</md-icon>
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <md-icon style="color: black" class="pointer">move_to_inbox</md-icon>
        </md-list-item>
      </md-list>
    </div>
    <div class="content">
      <md-card md-with-hover>
        <md-ripple>
          <md-card-header>
            <div class="md-title">Card with hover effect</div>
            <div class="md-subhead">It also have a ripple</div>
          </md-card-header>

          <md-card-content>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio
            itaque ea, nostrum odio. Dolores, sed accusantium quasi non.
          </md-card-content>

          <md-card-actions>
            <md-button>Action</md-button>
            <md-button>Action</md-button>
          </md-card-actions>
        </md-ripple>
      </md-card>
      <md-card md-with-hover>
        <md-ripple>
          <md-card-header>
            <div class="md-title">Card with hover effect</div>
            <div class="md-subhead">It also have a ripple</div>
          </md-card-header>

          <md-card-content>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio
            itaque ea, nostrum odio. Dolores, sed accusantium quasi non.
          </md-card-content>

          <md-card-actions>
            <md-button>Action</md-button>
            <md-button>Action</md-button>
          </md-card-actions>
        </md-ripple>
      </md-card>
      <md-card md-with-hover>
        <md-ripple>
          <md-card-header>
            <div class="md-title">Card with hover effect</div>
            <div class="md-subhead">It also have a ripple</div>
          </md-card-header>

          <md-card-content>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio
            itaque ea, nostrum odio. Dolores, sed accusantium quasi non.
          </md-card-content>

          <md-card-actions>
            <md-button>Action</md-button>
            <md-button>Action</md-button>
          </md-card-actions>
        </md-ripple>
      </md-card>
      <md-card md-with-hover>
        <md-ripple>
          <md-card-header>
            <div class="md-title">Card with hover effect</div>
            <div class="md-subhead">It also have a ripple</div>
          </md-card-header>

          <md-card-content>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio
            itaque ea, nostrum odio. Dolores, sed accusantium quasi non.
          </md-card-content>

          <md-card-actions>
            <md-button>Action</md-button>
            <md-button>Action</md-button>
          </md-card-actions>
        </md-ripple>
      </md-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "home",
  data: () => ({
    search: null
  })
};
</script>

<style>
.home-container {
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: 300px 1fr;
  overflow: hidden;
}
.sidenav {
  height: 100%;
  width: 100%;
  background-color: whitesmoke;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.19), 0 0px 6px rgba(0, 0, 0, 0.2);
  padding: 15px;
}
.sidenav-short {
  display: none;
  height: 100%;
  width: 100%;
  background-color: whitesmoke;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.19), 0 0px 6px rgba(0, 0, 0, 0.2);
  padding: 10px;
}
.logout {
 text-align: left;
 margin-top: 68vh;
}
.pointer {
  cursor: pointer;
}
.content {
  height: 100%;
  width: 100%;
  display: grid;
  padding: 30px;
  grid-gap: 20px;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  grid-auto-rows: 300px;
  overflow-y: auto;
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
    grid-auto-rows: 350px;
  }
}
</style>
