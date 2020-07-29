<template>
    <div>
    <Header></Header>
        <div class="m-content">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" label-position="top">
<!--                <el-form-item label="类别" prop="type">-->
<!--                    <el-input  type="text" maxlength="10" show-word-limit v-model="ruleForm.type" placeholder="Java" style="width: 15em;"></el-input>-->
<!--                </el-form-item>-->


                <el-form-item label="标题" prop="title">
                    <el-input v-model="ruleForm.title"></el-input>
                </el-form-item>

                <el-form-item label="摘要" prop="description">
                    <el-input type="textarea" v-model="ruleForm.description"></el-input>
                </el-form-item>

                <el-form-item label="内容" prop="content">
                    <mavon-editor v-model="ruleForm.content"></mavon-editor>
                </el-form-item>

                <el-form-item label="类别" >
                    <el-autocomplete class="inline-input" v-model="ruleForm.type" :fetch-suggestions="querySearch" placeholder="请输入内容" @select="handleSelect"></el-autocomplete>
                </el-form-item>

                <el-form-item label="是否公开"  >
                    <el-tooltip :content="'所有人可见' + ruleForm.status" placement="top">
                        <el-switch v-model="ruleForm.status" active-color="#13ce66" inactive-color="#ff4949" active-value="1" inactive-value="0"></el-switch>
                    </el-tooltip>
                </el-form-item>

            <el-form-item style="text-align: center;">
                <el-button type="primary" @click="submitForm('ruleForm')">提交编辑</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>

        </el-form>

        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    export default {
        name: "BlogEdit",
        components: {Header},
        data() {
            return {
                ruleForm: {
                    id:'',
                    title: '',
                    description: '',
                    content: '',
                    type:'',
                    status:'',
                    restaurants: [],
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    content: [
                        {required: true, message: '请输入内容', trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this =this
                        this.$axios.post('blog/edit',this.ruleForm,{
                            headers:{
                                "Authorization":localStorage.getItem("token")
                            }
                        }).then(res=>{
                            console.log(res)
                            this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    _this.$router.push("/blogs")
                                }
                            });
                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            querySearch(queryString, cb) {
                var restaurants = this.restaurants;
                var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                    return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            loadAll() {
                return [
                    { "value": "Java", "address": "长宁区新渔路144号" },
                    { "value": "计划", "address": "长宁区新渔路144号" },
                    { "value": "杂记", "address": "长宁区新渔路144号" },
                    { "value": "Mind", "address": "长宁区新渔路144号" },
                    { "value": "Django", "address": "长宁区新渔路144号" },
                    { "value": "Python", "address": "长宁区新渔路144号" },
                    { "value": "Machine Learning", "address": "长宁区新渔路144号" },
                    { "value": "Linux", "address": "长宁区新渔路144号" },
                    { "value": "部署相关", "address": "长宁区新渔路144号" },
                    { "value": "笔记", "address": "长宁区新渔路144号" },
                ];
            },
            handleSelect(item) {
                console.log(item);
            }
        },
        mounted() {
            this.restaurants = this.loadAll();
        },
        created() {
            const _this = this
            const blogId = this.$route.params.blogId
            console.log()
            if(blogId){
                this.$axios.get('/blog/'+blogId).then(res =>{
                    const blog = res.data.data
                    _this.ruleForm.id = blog.id
                    _this.ruleForm.title = blog.title
                    _this.ruleForm.description = blog.description
                    _this.ruleForm.content = blog.content
                })
            }
        }
    }
</script>

<style scoped>
    .m-content{
        margin: 0 auto;
        /*text-align: center;*/

    }

</style>