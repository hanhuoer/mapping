<template>
    <div class="menu">
        <div class="menu-collapse">
            <div @click="isCollapse = true">
                <i  class="el-icon-arrow-left" v-if="!isCollapse"></i>
            </div>
            <div @click="isCollapse = false">
                <i class="el-icon-arrow-right" v-if="isCollapse"></i>
            </div>
        </div>
        <el-menu default-active="1-4-1" class="el-menu-vertical-demo menu-body" @open="handleOpen" @close="handleClose"
                 :collapse="isCollapse">
            <el-menu-item index="1">
                <i class="el-icon-user"></i>
                <span slot="title">Client</span>
            </el-menu-item>
            <el-menu-item index="2">
                <i class="el-icon-user"></i>
                <span slot="title">Client</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Menu",
        data() {
            return {
                isCollapse: false
            };
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }

    .menu {
        display: flex;
        flex-direction: column;

        .menu-collapse {
            display: flex;
            flex-direction: row-reverse;
            border-right: solid 1px #e6e6e6;

            i {
                cursor: pointer;
            }
        }

        .menu-body {
            flex: 1
        }

        .menu-collapse, .menu-body {
            background-color: #deb8870a;
        }
    }
</style>
