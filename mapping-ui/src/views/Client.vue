<template>
    <div class="client">
        <el-row :gutter="20">
            <el-col :lg="10" :md="24" :sm="24" :xl="10" :xs="24">
                <el-card class="client-card">
                    <div class="clearfix" slot="header">
                        <span>Client</span>
                        <i @click="handleDialogClientAdd" class="icon-btn el-icon-plus"
                           style="float: right; padding: 3px 0"></i>
                    </div>
                    <el-table :data="clientList" @current-change="handleClientCurrentChange" highlight-current-row
                              ref="clientTable" style="width: 100%">
                        <el-table-column type="index" width="50"></el-table-column>
                        <el-table-column label="id" min-width="180" property="id"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="name" min-width="100" property="name"></el-table-column>
                        <el-table-column
                                fixed="right"
                                align="center"
                                label="operation"
                                width="100">
                            <template slot-scope="scope">
                                <i @click="handleClientUpdate(scope.row)" class="icon-btn el-icon-edit"></i>
                                <el-popconfirm @confirm="handleClientDelete(scope.row)"
                                               cancel-button-text='cancel'
                                               confirm-button-text='confirm'
                                               icon="el-icon-info"
                                               icon-color="red"
                                               title="Are you sure?">
                                    <i class="icon-btn el-icon-delete" slot="reference"></i>
                                </el-popconfirm>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :lg="14" :md="24" :sm="24" :xl="14" :xs="24">
                <el-row>
                    <el-col :span="24">
                        <el-card class="proxy-card" v-if="!ifProxySetting">
                            <div class="clearfix" slot="header">
                                <span>Proxy</span>
                                <i @click="handleProxyAddBtnClick" class="icon-btn el-icon-plus"
                                   style="float: right; padding: 3px 0"></i>
                            </div>
                            <el-table :data="proxyList" @current-change="handleProxyCurrentChange" highlight-current-row
                                      ref="singleTable" style="width: 100%">
                                <el-table-column type="index" width="50"></el-table-column>
                                <el-table-column label="name" property="name"></el-table-column>
                                <el-table-column label="server port" property="serverPort"></el-table-column>
                                <el-table-column label="client port" property="clientPort"></el-table-column>
                                <el-table-column label="client host" property="clientHost"></el-table-column>
                                <el-table-column
                                        align="center"
                                        fixed="right"
                                        label="operation"
                                        width="100">
                                    <template slot-scope="scope">
                                        <i @click="handleProxyUpdate(scope.row)" class="icon-btn el-icon-edit"></i>
                                        <i @click="handleProxySetting(scope.row)" class="icon-btn el-icon-setting"></i>
                                        <el-popconfirm @confirm="handleProxyDelete(scope.row)"
                                                       cancel-button-text='cancel'
                                                       confirm-button-text='confirm'
                                                       icon="el-icon-info"
                                                       icon-color="red"
                                                       title="Are you sure?">
                                            <i class="icon-btn el-icon-delete" slot="reference"></i>
                                        </el-popconfirm>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-card>

                        <el-card class="setting-card" v-if="ifProxySetting">
                            <div class="clearfix" slot="header">
                                <span>Setting</span>
                                <el-tooltip content="add rules" effect="dark" placement="bottom">
                                    <div slot="content">
                                        <p>
                                            <span>case 1</span>
                                            *<br/>
                                            /api/user/*<br/>
                                        </p>
                                        <p>
                                            <span>case 2</span>
                                            RE:/api/get/user/[0-9]+<br/>
                                        </p>
                                    </div>
                                    <i class="icon-btn el-icon-info"></i>
                                </el-tooltip>
                                <el-tooltip content="close" effect="dark" placement="bottom">
                                    <i @click="handleProxySettingClose" class="icon-btn el-icon-close"
                                       style="float: right; padding: 3px 3px"></i>
                                </el-tooltip>
                                <el-tooltip content="add rules" effect="dark" placement="top">
                                    <i @click="handleProxySettingAdd" class="icon-btn el-icon-plus"
                                       style="float: right; padding: 3px 3px"></i>
                                </el-tooltip>
                            </div>

                            <el-form label-width="auto">
                                <el-form-item :label="'rule' + (item.index + 1)"
                                              class="allow-item"
                                              v-for="(item, index) in allowRulesFormData">
                                    <el-input autocomplete="off" class="allow-input"
                                              v-model="item.path"></el-input>
                                    <div class="allow-input-btn">
                                        <i @click="handleProxySettingDelete(item.index)"
                                           class="allow-input-icon icon-btn el-icon-delete"></i>
                                    </div>
                                </el-form-item>
                            </el-form>
                            <el-button @click="handleProxySettingSubmit('allowRulesForm')">save</el-button>
                        </el-card>
                    </el-col>

                    <el-col :span="24" style="padding: 20px 0">
                        <el-card class="control-card">
                            <div class="clearfix" slot="header">
                                <span>Control</span>
                            </div>
                            <el-button @click="handleRefresh" icon="el-icon-refresh-left"></el-button>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>


        <el-dialog :visible.sync="dialogClientVisible" class="client-dialog" title="Add Client" width="30%">
            <el-form :model="dialogClientFormData">
                <el-form-item label="id" label-width="60px">
                    <el-input autocomplete="off" v-model="dialogClientFormData.id"></el-input>
                </el-form-item>
                <el-form-item label="name" label-width="60px">
                    <el-input autocomplete="off" v-model="dialogClientFormData.name"></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="dialogClientVisible = false">cancel</el-button>
                <el-button @click="handleDialogClientSubmit" type="primary">submit</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="dialogProxyVisible" class="proxy-dialog" title="Add Proxy" width="30%">
            <el-form :model="dialogProxyFormData" label-width="90px">
                <el-form-item label="name">
                    <el-input autocomplete="off" v-model="dialogProxyFormData.name"></el-input>
                </el-form-item>
                <el-form-item label="server port">
                    <el-input autocomplete="off" v-model="dialogProxyFormData.serverPort"></el-input>
                </el-form-item>
                <el-form-item label="client port">
                    <el-input autocomplete="off" v-model="dialogProxyFormData.clientPort"></el-input>
                </el-form-item>
                <el-form-item label="client host">
                    <el-input autocomplete="off" v-model="dialogProxyFormData.clientHost"></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="dialogProxyVisible = false">cancel</el-button>
                <el-button @click="handleDialogProxySubmit" type="primary">submit</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import {add, list, refresh, remove, update} from "@/api/client"

    export default {
        name: "Client",
        data() {
            return {
                metaData: [],
                clientList: [],
                proxyList: [],

                currentClientSelect: null,
                clientDeleteVisible: false,
                dialogClientVisible: false,
                dialogClientIsUpdate: true,
                dialogClientFormData: {},

                currentProxySelectIndex: null,
                proxyDeleteVisible: false,
                dialogProxyVisible: false,
                dialogProxyIsUpdate: true,
                dialogProxyFormData: {},

                currentProxySelectIndexForSetting: null,
                ifProxySetting: false,
                allowRulesFormData: []

            }
        },
        created() {
            this.init();
        },
        watch: {
            metaData: {
                handler(clientList) {
                    this.clientList = [];
                    clientList.forEach(client => {
                        this.clientList.push({
                            id: client.id,
                            name: client.name
                        });
                    });
                    let id = this.currentClientSelect;
                    if (id !== null && id !== "") {
                        this.proxyList = this.findProxyList(id);
                    }
                },
                deep: true
            }
        },
        methods: {
            init: function () {
                this.loadMetaData();
            },
            loadMetaData: function () {
                list().then(response => {
                    if (response.data) {
                        response.data.forEach(c => {
                            let index = 0;
                            if (c.proxyList) {
                                c.proxyList.forEach(p => {
                                    p.index = index++;
                                    if (p.allows) {
                                        let aIndex = 0;
                                        p.allows.forEach(a => {
                                            a.index = aIndex++;
                                        });
                                    }
                                });
                            }
                        });
                    }
                    this.metaData = response.data;
                });
            },
            getClient: function (id) {
                let clients = this.metaData.filter(m => m.id === id);
                if (clients === null || clients.length === 0) {
                    return null;
                }
                return clients[0];
            },
            findProxyList: function (id) {
                let proxyList = [];
                let metaRow = this.metaData.filter(m => m.id === id);
                if (metaRow !== null && metaRow.length > 0) {
                    proxyList = metaRow[0].proxyList;
                }
                return proxyList;
            },

            // client
            handleClientCurrentChange: function (row) {
                if (row === null || row === "") return;
                this.currentClientSelect = row.id;
                this.proxyList = this.findProxyList(row.id);
                this.handleProxySettingClose();
            },
            handleDialogClientAdd: function () {
                this.dialogClientVisible = true;
                this.dialogClientIsUpdate = false;
            },
            handleClientUpdate: function (row) {
                this.dialogClientFormData = {
                    id: row.id,
                    name: row.name,
                    proxyList: this.findProxyList(row.id)
                };

                this.dialogClientVisible = true;
                this.dialogClientIsUpdate = true;
            },
            handleClientDelete: function (row) {
                this.clientDeleteVisible = false;
                let id = row.id;
                remove(id).then(response => {
                    this.$message({
                        type: 'success',
                        message: response.message
                    });
                    this.loadMetaData();
                }).catch(e => {
                    this.$message({
                        type: 'error',
                        message: e.message
                    })
                });
            },
            handleDialogClientSubmit: function () {
                this.dialogClientVisible = false;
                let client = {
                    id: this.dialogClientFormData.id,
                    name: this.dialogClientFormData.name
                };
                if (this.dialogClientIsUpdate) {
                    client.proxyList = this.dialogClientFormData.proxyList;
                    update(client).then(response => {
                        this.$message({
                            type: 'success',
                            message: response.message
                        });
                        this.loadMetaData();
                    }).catch(e => {
                        this.$message(e)
                    });
                } else {
                    add(client).then(response => {
                        this.$message({
                            type: 'success',
                            message: response.message
                        });
                        this.loadMetaData();
                    }).catch(e => {
                        this.$message(e);
                    });
                    // this.metaData.push(client);
                }
                this.dialogClientFormData = {};
            },

            // proxy
            handleProxyCurrentChange: function (row) {
                if (row === null || row === "") return;
                this.currentProxySelectIndex = row.index;
                this.log.debug(JSON.stringify(row));
            },
            handleProxyAddBtnClick: function () {
                if (this.currentClientSelect === null || this.currentClientSelect === "") {
                    this.$message({
                        type: 'warning',
                        message: 'please select one of client.'
                    });
                } else {
                    this.dialogProxyVisible = true;
                    this.dialogProxyIsUpdate = false;
                }
            },
            handleProxyUpdate: function (row) {
                this.log.debug(JSON.stringify(row));
                this.dialogProxyFormData = {
                    name: row.name,
                    serverPort: row.serverPort,
                    clientHost: row.clientHost,
                    clientPort: row.clientPort,
                    allows: row.allows
                };

                this.dialogProxyVisible = true;
                this.dialogProxyIsUpdate = true;
            },
            handleDialogProxySubmit: function () {
                this.dialogProxyVisible = false;
                let id = this.currentClientSelect;
                if (id === null || id === "") {
                    this.$message({
                        type: 'warning',
                        message: 'please select one of client.'
                    });
                    return;
                }
                let client = this.getClient(id);
                if (client === null) {
                    this.$message({
                        type: 'warning',
                        message: 'client not found.'
                    });
                    return;
                }

                let proxy = {
                    name: this.dialogProxyFormData.name,
                    serverPort: this.dialogProxyFormData.serverPort,
                    clientHost: this.dialogProxyFormData.clientHost,
                    clientPort: this.dialogProxyFormData.clientPort,
                    allows: this.dialogProxyFormData.allows
                };

                let proxyList = this.findProxyList(id);
                if (proxyList === null) {
                    proxyList = [];
                }

                let index = this.currentProxySelectIndex;
                if (this.dialogProxyIsUpdate) {
                    for (let i = 0; i < proxyList.length; i++) {
                        if (proxyList[i].index === this.currentProxySelectIndex) {
                            proxyList[i] = proxy;
                            break;
                        }
                    }
                    client.proxyList = proxyList;
                    this.log.debug(`update; ${JSON.stringify(proxyList)}`);
                } else {
                    proxyList.push(proxy);
                    client.proxyList = proxyList;
                    this.log.debug(`add; ${JSON.stringify(proxyList)}`);
                }

                update(client).then(response => {
                    this.$message({
                        type: 'success',
                        message: response.message
                    });
                    this.loadMetaData();
                }).catch(e => {

                });

                this.dialogProxyFormData = {};
            },
            handleProxyDelete: function (row) {
                this.proxyDeleteVisible = false;
                let id = this.currentClientSelect;
                if (id === null || id === "") {
                    this.$message({
                        type: 'warning',
                        message: 'please select one of client.'
                    });
                    return;
                }
                let client = this.getClient(id);
                if (client === null) {
                    this.$message({
                        type: 'warning',
                        message: 'client not found.'
                    });
                    return;
                }

                let proxyList = this.findProxyList(id);
                if (proxyList === null) {
                    proxyList = [];
                }

                let index = this.currentProxySelectIndex;
                proxyList = proxyList.filter(p => p.index !== index);

                client.proxyList = proxyList;

                update(client).then(response => {
                    this.$message({
                        type: 'success',
                        message: response.message
                    });
                    this.loadMetaData();
                }).catch(e => {

                });
            },

            // setting
            handleProxySetting: function (row) {
                this.log.debug(`current proxy: ${JSON.stringify(row)}`);
                this.currentProxySelectIndexForSetting = row.index;
                this.allowRulesFormData = row.allows;
                this.ifProxySetting = true;
            },
            handleProxySettingClose: function () {
                this.ifProxySetting = false;
                this.dialogAllowFormData = [];
                this.currentProxySelectIndexForSetting = null;
            },
            handleProxySettingAdd: function () {
                if (this.allowRulesFormData === null) {
                    this.allowRulesFormData = [];
                }
                this.allowRulesFormData.push({
                    index: this.allowRulesFormData.length,
                    path: null
                });
                this.log.debug(JSON.stringify(this.allowRulesFormData));
            },
            handleProxySettingDelete: function (allowId) {
                this.allowRulesFormData = this.allowRulesFormData
                    .filter(a => a.index !== allowId);
                // re index.
                let index = 0;
                this.allowRulesFormData.forEach(a => {
                    a.index = index++;
                });
                this.log.debug(`allowRulesFormData: ${JSON.stringify(this.allowRulesFormData)}`);
            },
            handleProxySettingSubmit: function (formName) {
                let clientId = this.currentClientSelect;
                if (clientId === null || clientId === "") {
                    this.$message({
                        type: 'warning',
                        message: 'please select one of client.'
                    });
                    return;
                }
                let client = this.getClient(clientId);
                if (client === null) {
                    this.$message({
                        type: 'warning',
                        message: 'client not found.'
                    });
                    return;
                }

                let proxyList = this.findProxyList(clientId);
                if (proxyList === null) {
                    this.$message({
                        type: 'warning',
                        message: 'proxy is not exist.'
                    });
                    return;
                }

                // check rules.
                let allowRulesFormData = this.allowRulesFormData;
                if (allowRulesFormData) {
                    for (let i = 0; i < allowRulesFormData.length; i++) {
                        let allow = allowRulesFormData[i];
                        if (allow.path === undefined || allow.path === null || allow.path === '') {
                            this.$message({
                                type: 'warning',
                                message: `rule${i + 1} can not be empty.`
                            });
                            return;
                        }
                    }
                }

                let proxyIndex = this.currentProxySelectIndexForSetting;
                proxyList.forEach(p => {
                    if (p.index === proxyIndex) {
                        p.allows = allowRulesFormData;
                    }
                });

                this.log.debug(`client: ${JSON.stringify(client)}`);

                update(client).then(response => {
                    this.$message({
                        type: 'success',
                        message: response.message
                    });
                    this.handleProxySettingClose();
                    this.loadMetaData();
                }).catch(e => {
                    this.log.error(JSON.stringify(e));
                });
            },

            // control
            handleRefresh: function () {
                refresh().then(response => {
                    console.log(response);
                    this.$message({
                        type: 'success',
                        message: response.message
                    })
                }).catch(e => {
                    this.log.error(JSON.stringify(e));
                })
            },
        }
    }
</script>

<style lang="scss" scoped>
    .client {
        .icon-btn {
            cursor: pointer;
            padding: 0 2px;
        }

        .client-card {
            margin-bottom: 20px;
        }

        .proxy-card {

        }

        .setting-card {
            .allow-item {
                /deep/ .el-form-item__content {
                    display: flex;
                }
            }

            .allow-input {

            }

            .allow-input-btn {
                display: inline-block;
                /*width: 15%;*/
                /*text-align: center;*/
            }

            .allow-input-icon {
                padding: 0 15px
            }
        }
    }
</style>
