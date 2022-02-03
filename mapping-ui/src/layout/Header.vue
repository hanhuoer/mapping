<template>
    <div class="header">
        <div class="logo">
            <router-link to="/">Mapping</router-link>
        </div>
        <div class="nav-btn-container">
            <!--<router-link class="nav-btn" to="/">Home</router-link>-->
            <!--<router-link class="nav-btn" to="/client">Client</router-link>-->
            <!--<router-link class="nav-btn" to="/about">About</router-link>-->
        </div>
        <div class="nav-user-container">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link nav-user-avatar">
                    <i class="nav-btn el-icon-user"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="handleSignOut">Sign out</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import {getToken, removeToken, setToken} from "../utils/auth";
    import {getExpireAt, getRefreshLeastSeconds, refresh} from "../api/auth";

    export default {
        name: "Header",
        data() {
            return {
                defaultRefreshLessSeconds: 120,
                serverRefreshLessSeconds: null,
                lastTokenExpireAt: null,
                getServerRefreshLessSecondsFailedTimes: 0,
                getLastTokenExpireAtFailedTimes: 0,
                doRefreshLock: true
            }
        },
        created() {
            this.getTokenExpireAt();
            this.getServerRefreshLessSeconds();
            setTimeout(() => {
                this.doRefresh();
            }, 10000);
        },
        methods: {
            doRefresh: function () {
                setTimeout(() => {
                    let currentToken = getToken();
                    if (currentToken === undefined || currentToken === null || currentToken === "") {
                        this.$router.push("/login");
                        return;
                    }
                    if (this.doRefreshLock) {
                        this.doRefreshLock = false;
                        if (this.checkRefreshTiming()) {
                            this.log.debug("start refreshing the token");
                            refresh().then(response => {
                                if (response.code === "20000") {
                                    let token = response.data;
                                    this.log.debug(`the new token: ${token}`);
                                    if (token !== null && token !== "") {
                                        setToken(token);
                                        this.log.debug(`save the new token`);
                                        this.getTokenExpireAt();
                                        this.getServerRefreshLessSeconds();
                                    }
                                }
                                this.doRefreshLock = true;
                                this.doRefresh();
                            }).catch(error => {
                                this.doRefreshLock = true;
                                this.doRefresh();
                            });
                        } else {
                            this.log.debug("do not need refresh the token");
                            this.doRefreshLock = true;
                            this.doRefresh();
                        }
                    }
                }, 10000);
            },
            checkRefreshTiming: function () {
                let now = new Date();
                let expireAt = this.lastTokenExpireAt;
                if (expireAt === null || expireAt <= 0) {
                    if (this.getLastTokenExpireAtFailedTimes < 5) {
                        this.getTokenExpireAt();
                        this.getLastTokenExpireAtFailedTimes += 1;
                    }
                    return false;
                }
                if (expireAt > now.getTime()) {
                    let lessSeconds = this.serverRefreshLessSeconds;
                    if (lessSeconds === null) {
                        if (lessSeconds <= 0) {
                            this.getServerRefreshLessSecondsFailedTimes = 6;
                        }
                        lessSeconds = this.defaultRefreshLessSeconds;

                        if (this.getServerRefreshLessSecondsFailedTimes < 5) {
                            this.getServerRefreshLessSeconds();
                            this.getServerRefreshLessSecondsFailedTimes += 1;
                        }
                    }
                    let expireSeconds = (expireAt - now.getTime()) / 1000;
                    this.log.debug(`expires in ${expireSeconds} seconds, refresh the token ${lessSeconds} seconds before expiration`);
                    return expireSeconds < lessSeconds;
                }
                return false;
            },
            getTokenExpireAt: function () {
                getExpireAt().then(response => {
                    this.lastTokenExpireAt = response.data;
                    this.log.debug(`expiration time at: ${new Date(response.data).toISOString()}, timestamp: ${response.data}`);
                })
            },
            getServerRefreshLessSeconds: function () {
                getRefreshLeastSeconds().then(response => {
                    this.serverRefreshLessSeconds = response.data;
                    this.log.debug(`minimum seconds refresh: ${response.data}`);
                })
            },
            handleSignOut: function () {
                removeToken();
                // this.$message({
                //     type: 'success',
                //     message: 'success'
                // });
                this.$router.push("/login");
            }
        }
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
            cursor: pointer;
        }

        .nav-user-container {
            .nav-user-avatar {
                font-size: 20px;
            }
        }
    }

</style>
