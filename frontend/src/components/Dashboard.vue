<template>
  <div>
    <div class="box">
      <transition mode="out-in" name="fade">
      <router-view/>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
}
</script>

<style scoped>
.box {
  box-shadow: rgba(0, 0, 0, .12) 0 0 20px;
  border-radius: 20px;
  background-color: white;
  position: absolute;
  top: 50px;
  bottom: 50px;
  width: calc(100% - 100px);
  left: 50px;
}

</style>
