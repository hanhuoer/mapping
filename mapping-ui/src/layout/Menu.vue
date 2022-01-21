<template>
    <div class="menu">
        <div class="menu-collapse">
            <div @click="isCollapse = true">
                <i class="el-icon-arrow-left" v-if="!isCollapse"></i>
            </div>
            <div @click="isCollapse = false">
                <i class="el-icon-arrow-right" v-if="isCollapse"></i>
            </div>
        </div>
        <el-menu @close="handleClose" @open="handleOpen"
                 @select="handleSelect" background-color="#333"
                 class="el-menu-vertical-demo menu-body" default-active="1-4-1" text-color="#ffffff"
                 :collapse="isCollapse">
            <el-menu-item index="/client">
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
            },
            handleClose(key, keyPath) {
            },
            handleSelect(key, keyPath) {
                this.$router.push(key);
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
            background-color: #333;
            box-shadow: inset 0 1px 3px -2px burlywood;

            i {
                cursor: pointer;
                color: #fff;

                &:hover {
                    color: burlywood;
                }
            }
        }

        .menu-body {
            flex: 1;

            .el-menu-item {
                i {
                    color: inherit;
                }
            }
        }

        .menu-collapse, .menu-body {
        }
    }
</style>
