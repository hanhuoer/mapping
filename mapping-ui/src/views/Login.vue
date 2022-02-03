<template>
    <div class="login">
        <div></div>
        <div class="container">
            <div class="title"></div>
            <el-form :model="formData" :rules="loginRules" class="login-form" label-width="0" ref="ruleForm"
                     status-icon>
                <el-form-item label="" prop="username">
                    <el-input prefix-icon="el-icon-user" type="text"
                              v-model="formData.username"></el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input @keyup.enter.native="handleLogin('ruleForm')" autocomplete="off"
                              prefix-icon="el-icon-lock" type="password"
                              v-model="formData.password"></el-input>
                </el-form-item>
                <el-button @click="handleLogin('ruleForm')" style="width: 100%"
                           type="primary">Sign in
                </el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {signIn} from '../api/auth';
    import {setToken} from '../utils/auth';

    export default {
        name: "Login",
        data() {
            const validateUsername = (rule, value, callback) => {
                if (value === undefined || value === null || value === '') {
                    callback(new Error('Please enter the correct user name'));
                } else {
                    callback();
                }
            };
            const validatePassword = (rule, value, callback) => {
                if (value === undefined || value === null || value === '') {
                    callback(new Error('The password can not be empty'));
                } else {
                    callback();
                }
            };
            return {
                formData: {
                    username: null,
                    password: null
                },
                loginRules: {
                    username: [{required: true, trigger: 'blur', validator: validateUsername}],
                    password: [{required: true, trigger: 'blur', validator: validatePassword}]
                }
            }
        },
        methods: {
            handleLogin: function (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        signIn(this.formData.username, this.formData.password).then(response => {
                            if (response.code === "20000") {
                                setToken(response.data);
                                this.$router.push("/");
                            } else if (response.code === "40007") {
                                this.$message({
                                    type: 'warning',
                                    message: response.message
                                });
                            } else {

                            }
                        }).catch(err => {
                            this.$message({
                                type: "warning",
                                message: err.message
                            });
                        })
                    } else {
                        this.log.error('error submit!!');
                        return false;
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .login {
        height: 100%;
        background-color: #f1f2f3;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;

        .container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 200px;
            width: 280px;
            padding: 60px 20px 30px 20px;;
            background-color: #ffffff;
            box-shadow: 0 0 10px 0 #0000001a;
        }

        .title {
        }

        .login-form {
        }
    }

</style>
