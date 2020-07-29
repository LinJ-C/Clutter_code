<template>
    <div>
        <Header></Header>
        <div class="m-blog">
            <h2 class="m-title">{{blog.title}}</h2>
            <div class="operation">
                <el-link  class="m-operation" v-show="blog.ownBlog">
                    <router-link :to="{name:'BlogEdit', params:{blogid:blog.id}}">
                        <i class="el-icon-edit">编辑</i>
                    </router-link>
                </el-link>
                <el-link  class="m-operation" v-show="blog.ownBlog">
                    <router-link :to="{name:'Blogs'}" @click.native="del" >
                        <i class="el-icon-delete">删除</i>
                    </router-link>
                </el-link>
                <!--                预留功能-->
                <el-link  class="m-operation" v-show="blog.ownBlog">
                    <router-link :to="{name:'Blogs'}" @click.native="top">
                        <i class="el-icon-thumb">置顶</i>
                    </router-link>
                </el-link>
            </div>
            <el-divider>摘要</el-divider>
            <span class="m-description">{{blog.description}}</span>
            <el-divider><i class="el-icon-loading"></i></el-divider>
            <div class="markdown-body" v-html="blog.content"></div>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import 'github-markdown-css/github-markdown.css'
    export default {
        name: "BlogDetail",
        components: {Header},
        data(){
            return{
                blog:{
                    id:'',
                    title:"标题",
                    description:'摘要',
                    content:"内容",
                    ownBlog:false,
                },
            }
        },
        methods: {
            del(blog) {
                const _this = this
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.get('/blog/del/' + this.blog.id,
                        {
                            headers: {
                                "Authorization": localStorage.getItem("token")
                            }
                        })
                        .then(res => {
                            console.log(res)
                        })
                    // _this.$router.push("/blogs")
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.$router.go(0)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            top(blog) {
                const _this = this
                this.$alert('操作成功', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {
                        this.$axios.get('blog/top/' + this.blog.id,
                            {
                                headers: {
                                    "Authorization": localStorage.getItem("token")
                                }
                            })
                            .then(res => {
                                console.log(res)
                            })
                        // _this.$router.push("/blogs")
                        this.$router.go(0)
                    }
                })
            }
        },
        created() {
            const _this = this
            const blogId = this.$route.params.blogId
            if(blogId){
                this.$axios.get('/blog/'+blogId).then(res =>{
                    const blog = res.data.data
                    _this.blog.id = blog.id
                    _this.blog.title = blog.title
                    _this.blog.description = blog.description

                    var MardownIt = require("markdown-it")
                    var md = new MardownIt()

                    var result = md.render(blog.content)
                    _this.blog.content = result
                    if(blog.userId==_this.$store.getters.getUser.id){
                        _this.blog.ownBlog=true
                    }
                })
            }
        }
    }
</script>

<style scoped>

    .m-blog{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 100%;
        min-height: 700px;
    }
    .m-title{
        text-align: center;
        padding-top: 15px;
    }
    .m-description{
        padding-left: 2em;
    }
    .markdown-body{
        padding-left: 1em;
    }
    .m-operation{
        margin: 0px 1em;
        left: 77%;
    }
</style>