<template>

    <div>
        <vue-canvas-nest :config="{color:'255,0,0', count: 88}" :el="'#area'"></vue-canvas-nest>
        <el-container>
            <el-header>
            <img class="mlogo" src="http://linwar.top/static/img/Lin_war.png">
            </el-header>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username"></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="ruleForm.password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>

    </div>
</template>

<script>
    import vueCanvasNest from 'vue-canvas-nest'
    export default {
        components: { vueCanvasNest },
        name: "Login",
        data() {
            return {
                config:{ // 配置
                    color: '255,255,255', // 线条颜色
                    pointColor: '0, 255, 0', // 节点颜色
                    opacity: 0.7, // 线条透明度
                    count: 222, // 线条数量
                    zIndex: 0 // 画面层级
                },
                ruleForm: {
                    username: '',
                    password: '',
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'change' },
                        { min: 3, max: 18, message: '长度在 3 到 18 个字符', trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this =this
                        // alert('submit!');
                        this.$axios.post("/login",this.ruleForm).then(res =>{

                            const jwt = res.headers['authorization']
                            const userInfo =res.data.data
                            //把数据共享出去
                            _this.$store.commit("SET_TOKEN",jwt)
                            _this.$store.commit("SET_USERINFO",userInfo)
                            //获取数据
                            console.log(_this.$store.getters.getUser)
                            //跳转到blogs
                            _this.$router.push("/blogs")
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    .el-header, .el-footer {
        background-color: black;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
       /*background-color: #E9EEF3;*/
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
    .mlogo{
        margin-top: 5px;
    }
    .demo-ruleForm{
        max-width: 500px;
        margin: 0 auto;
    }
</style>