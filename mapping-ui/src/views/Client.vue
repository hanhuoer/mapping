<template>
    <div class="client">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card class="box-card">
                    <div class="clearfix" slot="header">
                        <span>Client List</span>
                        <el-button @click="handleDialogClientAdd" style="float: right; padding: 3px 0"
                                   type="text">Add
                        </el-button>
                    </div>
                    <el-table :data="clientList" @current-change="handleClientCurrentChange" highlight-current-row
                              ref="clientTable" style="width: 100%">
                        <el-table-column type="index" width="50"></el-table-column>
                        <el-table-column label="id" property="id"></el-table-column>
                        <el-table-column label="name" property="name"></el-table-column>
                        <el-table-column
                                fixed="right"
                                label="operation"
                                width="100">
                            <template slot-scope="scope">
                                <el-button @click="handleClientUpdate(scope.row)" size="small" type="text">update
                                </el-button>
                                <el-popconfirm @confirm="handleClientDelete(scope.row)"
                                               cancel-button-text='cancel'
                                               confirm-button-text='confirm'
                                               icon="el-icon-info"
                                               icon-color="red"
                                               title="Are you sure?">
                                    <el-button size="small" slot="reference" type="text">delete</el-button>
                                </el-popconfirm>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-row>
                    <el-col :span="24">
                        <el-card class="box-card">
                            <div class="clearfix" slot="header">
                                <span>Proxy</span>
                                <el-button @click="handleProxyAddBtnClick" style="float: right; padding: 3px 0"
                                           type="text">Add
                                </el-button>
                            </div>
                            <el-table :data="proxyList" @current-change="handleProxyCurrentChange" highlight-current-row
                                      ref="singleTable" style="width: 100%">
                                <el-table-column type="index" width="50"></el-table-column>
                                <el-table-column label="name" property="name"></el-table-column>
                                <el-table-column label="server port" property="serverPort"></el-table-column>
                                <el-table-column label="client port" property="clientPort"></el-table-column>
                                <el-table-column label="client host" property="clientHost"></el-table-column>
                            </el-table>
                        </el-card>
                    </el-col>

                    <el-col :span="24" style="padding: 20px 0">
                        <el-card class="box-card">
                            <div class="clearfix" slot="header">
                                <span>Control</span>
                            </div>
                            <el-button @click="handleRefresh" icon="el-icon-refresh-left"></el-button>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>

        <el-dialog :visible.sync="dialogClientVisible" title="Add Client" width="30%">
            <el-form :model="dialogClientFormData">
                {{dialogClientIsUpdate}}
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

        <el-dialog :visible.sync="dialogProxyVisible" title="Add Proxy" width="30%">
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
                dialogProxyVisible: false,
                dialogProxyFormData: {}
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
            handleClientCurrentChange: function (row) {
                if (row === null || row === "") return;
                this.currentClientSelect = row.id;
                this.proxyList = this.findProxyList(row.id);
            },
            handleProxyCurrentChange: function (row) {
            },
            handleProxyAddBtnClick: function () {
                if (this.currentClientSelect === null || this.currentClientSelect === "") {
                    this.$message({
                        type: 'warning',
                        message: 'please select one of client.'
                    });
                } else {
                    this.dialogProxyVisible = true;
                }
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
                    clientPort: this.dialogProxyFormData.clientPort,
                    clientHost: this.dialogProxyFormData.clientHost
                };

                let proxyList = this.findProxyList(id);
                if (proxyList === null) {
                    proxyList = [];
                }
                proxyList.push(proxy);
                client.proxyList = proxyList;

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
            handleRefresh: function () {
                refresh().then(response => {
                    console.log(response);
                    this.$message({
                        type: 'success',
                        message: response.message
                    })
                }).catch(e => {

                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .client {
    }
</style>
