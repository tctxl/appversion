<template>
  <div class="bg">
    <transition mode="out-in" name="fade">
    <router-view/>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
    }
  }
}
</script>

<style>
html, body {
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
  background-color: #828282;
}

.bg {
  height: 100%;
  overflow: hidden;
  background-image: url("./assets/bg.png");
}

.fade-enter {
  opacity: 1
}

.fade-leave {
  opacity: 0
}


.fade-leave-active {
  -webkit-animation-name: fadeOut;
  -webkit-animation-duration: .2s;
  -webkit-animation-iteration-count: 1;
  -webkit-animation-delay: 0s
}

.fade-enter-active {
  -webkit-animation-name: fadeIn;
  -webkit-animation-duration: .2s;
  -webkit-animation-iteration-count: 1;
  -webkit-animation-delay: 0s
}

@-webkit-keyframes fadeIn {
  0% {
    -webkit-transform: scale(.6);
    transform: scale(.6);
    opacity: 0
  }
  25% {
    -webkit-transform: scale(.7);
    transform: scale(.7);
    opacity: .25
  }
  50% {
    -webkit-transform: scale(.8);
    transform: scale(.8);
    opacity: .5
  }
  75% {
    -webkit-transform: scale(.9);
    transform: scale(.9);
    opacity: .75
  }
  to {
    -webkit-transform: scale(1);
    transform: scale(1);
    opacity: 1
  }
}

@-webkit-keyframes fadeOut {
  0% {
    -webkit-transform: scale(1);
    transform: scale(1);
    opacity: 1
  }
  25% {
    -webkit-transform: scale(.9);
    transform: scale(.9);
    opacity: .75
  }
  50% {
    -webkit-transform: scale(.8);
    transform: scale(.8);
    opacity: .5
  }
  75% {
    -webkit-transform: scale(.7);
    transform: scale(.7);
    opacity: .25
  }
  to {
    -webkit-transform: scale(.6);
    transform: scale(.6);
    opacity: 0
  }
}
</style>
