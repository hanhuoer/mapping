<template>
    <div class="header">
        <div class="logo">
            <router-link to="/">Mapping</router-link>
        </div>
        <div class="nav-btn-container">
            <router-link class="nav-btn" to="/">Home</router-link>
            <router-link class="nav-btn" to="/client">Client</router-link>
            <router-link class="nav-btn" to="/about">About</router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style lang="scss" scoped>
    .header {
        display: flex;
        flex-direction: row;
        padding: 0 20px;
        background-color: #333;
        box-shadow: 0 0 10px 0 #333;
        height: 60px;
        align-items: center;

        a {
            color: burlywood;
            text-decoration: none;
        }

        .logo {
            flex: 1;
        }

        .nav-btn-container {
        }

        .logo, .nav-btn-container {

        }

        .nav-btn {
            padding: 0 10px;
        }
    }

</style>
