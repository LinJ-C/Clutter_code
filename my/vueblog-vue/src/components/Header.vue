<template>
    <div class="m-content">
        <h3>欢迎来到Linwar的博客</h3>
        <div class="block">
            <el-avatar shape="square" :size="50" :src="user.avatar">
            </el-avatar>
            <div>{{user.username}}</div>
        </div>
        <div class="maction">
            <span><el-link href="/blogs">主页</el-link></span>
                <el-divider direction="vertical"></el-divider>
            <span><el-link href="/blog/add" type="success">发表文章</el-link></span>

            <el-divider direction="vertical"></el-divider>
            <span><el-link v-show="!haslogin" @click="login"type="primary">登录</el-link></span>

            <span><el-link v-show="haslogin" @click="logout" type="danger">退出</el-link></span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
               user:{
                   username:"请先登录",
                   avatar: "http://linwar.top/static/img/Lin_war.png"
               },
               haslogin:false
            }
        },
        methods:{
            logout(){
                const _this = this
                _this.$axios.get("/logout",{
                    headers:{
                        "Authorization":localStorage.getItem("token")
                    }
                }).then(res=>{
                    _this.$store.commit("REMOVE_INFO")
                    _this.$router.push("/login")
                })
            },
            login(){
                const _this = this
                _this.$router.push("/login")
            }
        },


        created() {
            if(this.$store.getters.getUser.username){
                this.user.username=this.$store.getters.getUser.username
                this.user.avatar=this.$store.getters.getUser.avatar
                this.haslogin = true
            }
        }

    }
</script>

<style scoped>
    .m-content{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
    }
    .maction{
        margin: 10px 0;
    }

</style>