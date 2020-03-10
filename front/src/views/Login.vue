<template>
  <div class="login-container">
    <div class="pale-pink">
      <vue-particles
        color="#D23669"
        :particleOpacity="0.4"
        :particlesNumber="5"
        shapeType="polygon"
        :particleSize="40"
        linesColor="#D23669"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="1"
        :linesDistance="1"
        :moveSpeed="3"
      >
      </vue-particles>
    </div>
    <div class="foreground">
      <h1 class="login-title">shelf</h1>
      <br />
      <g-signin-button
        :params="googleSignInParams"
        @success="onSignInSuccess"
        @error="onSignInError"
      >
        Sign in with Google
      </g-signin-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  props: ["baseUrl"],
  data: function() {
    return {
      googleSignInParams: {
        client_id: this.$googleClientId
      }
    }
  },
  methods: {
    async onSignInSuccess(googleUser) {
      const loginResponse = await fetch(this.baseUrl + "/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({
          gToken: googleUser.getAuthResponse().id_token
        })
      });

      if (loginResponse.status === 200) {
        window.localStorage.setItem(
          "firstname",
          googleUser.getBasicProfile().getGivenName()
        );
        window.localStorage.setItem(
          "photo",
          googleUser.getBasicProfile().getImageUrl()
        );
        location.reload();
      }
    },
    onSignInError(error) {
      alert("An error occurred while signing in");
    }
  }
};
</script>

<style>
.login-container {
  height: 100%;
}
.foreground {
  position: fixed;
  top: 35%;
  left: 0;
  right: 0;
  user-select: none;
}
.login-title {
  font-size: 4em;
  color: #d23669;
  margin: 0 auto;
}
.g-signin-button {
  cursor: pointer;
  display: inline-block;
  padding: 4px 8px;
  border-radius: 3px;
  background-color: #d23669;
  color: white;
}
.particles-wrapper {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100vh;
}
#particles-js {
  width: 100%;
  height: 100vh;
}
</style>
