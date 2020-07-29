<template>
    <div class="m-body">
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

            <el-divider direction="vertical"></el-divider>
            <span>
                <el-link    type="warning" v-show="haslogin" >
                    <router-link :to="{name:'Blogs', query:{info: 1}} " >
                        私密空间
                    </router-link>
                </el-link>
            </span>

        </div>

    </div>

        <div class="type" >
            <el-tag class="bq" v-for="type in typelist" >
                <router-link :to="{name:'Blogs', query:{type: type.type}} " >
                    {{type.type}}
                </router-link>
<!--                <a href="/blogs?type="+{{type.type}}>{{type.type}}</a>-->
            </el-tag>
        </div>
    </div>

</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
               typelist:[],
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
            },

             ne:function(type){
                 this.$router.push("/blogs", query={ type: type});
            }
        },

        beforeCreate() {
            this.$axios.get('/types').then(res=>{
                console.log('在这里')
                console.log(res.data)
                console.log(res.data.data[0].type)
                this.typelist = res.data.data

            })
        },
        created() {
            if(this.$store.getters.getUser.username){
                this.user.username=this.$store.getters.getUser.username
                this.user.avatar=this.$store.getters.getUser.avatar
                this.haslogin = true
            }
        },


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
    .type{
        position: fixed;
        top: 200px;
        left: 3%;
        width: 400px;
        height: 1000px;
    }
    .bq{
        flex: 0 0 25%;
        margin: 20px;
    }

</style>