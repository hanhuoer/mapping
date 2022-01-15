<template>
    <div class="content">
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

<style lang="scss" scoped>
    .content {
      padding: 20px 20px;
    }
</style>
