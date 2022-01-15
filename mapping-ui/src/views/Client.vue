<template>
  <div class="client">
    client page.
    <el-button @click="handleRefresh">refresh</el-button>
  </div>
</template>

<script>
import {refresh} from "@/api/client"

export default {
  name: "Client",
  methods: {
    handleRefresh: function () {
      refresh().then(response => {
        console.log(response)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .client {
  }
</style>
