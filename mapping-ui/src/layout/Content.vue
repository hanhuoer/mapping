<template>
  <div>
    <router-view :key="key"/>
  </div>
</template>

<script>
export default {
  name: "Content",
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>

</style>