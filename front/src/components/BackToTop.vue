<template>
  <div
    v-if="displayBackToTop"
    class="back-top-container pale-pink accent-pink pointer"
    @click="scrollTop"
  >
    <md-icon class="accent-pink">change_history</md-icon>
  </div>
</template>
<script>
export default {
  name: "BackToTop",
  data: () => ({
    displayBackToTop: true,
  }),
  methods: {
    scrollTop: function () {
      const distance = this.$parent.$el.lastChild.scrollTop;
      if (distance > 0) {
        window.requestAnimationFrame(this.scrollTop);
        this.$parent.$el.lastChild.scrollTo(0, distance - distance / 8);
      }
    },
  },
};
</script>
<style>
.back-top-container {
  z-index: 100;
  position: fixed;
  bottom: 2vh;
  right: 2vw;
  height: 30px;
  width: 30px;
  border-radius: 50%;
  border: 1px #d23669 solid;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}
.back-top-container :hover {
  transform: scale(1.5);
}
</style>
