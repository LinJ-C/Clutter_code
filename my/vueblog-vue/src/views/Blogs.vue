<template>
    <div >
        <Header></Header>
        <div class="head">
            <div class="count">
                <span  class="el-icon-view">{{count}}</span>
            </div>
        </div>
        <div class="block">
            <el-timeline>
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
                    <el-card>
                        <h4>
                            <router-link :to="{name:'BlogDetail', params:{blogId: blog.id}}">
                            {{blog.title}}
                            </router-link>
                        </h4>
                        <p>{{blog.description}}</p>
                        <span>{{blog.username}}</span>
                    </el-card>
                </el-timeline-item>
            </el-timeline>

            <el-pagination class="mpage"
                    background
                    layout="prev, pager, next"
                           :current-page="currentPage"
                           :page-size="pageSize"
                           :total="total"
                            @current-change="page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    export default {
        name: "Blogs",
        components: {Header},
        data(){
            return{
                blogs:{},
                currentPage:1,
                total:0,
                pageSize:5,
                count:7,
                show:1,
                type:null,
                info:null,
            }
        },
        methods:{
            page(currentPage){
                const  _this =this
                _this.$axios.get("/blogs?currentPage="+currentPage).then(res=>{
                    //console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                 })
            },
            page_type(currentPage){
                const  _this =this
                _this.$axios.get("/blogs/"+_this.type+"?currentPage="+currentPage).then(res=>{
                    //console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            },
            page_info(currentPage){
                const  _this =this
                this.$axios.get("/ownblog?currentPage="+currentPage,
                    {
                        headers: {
                            "Authorization": localStorage.getItem("token")
                        }
                    })
                    .then(res => {
                        console.log(res)
                        _this.blogs = res.data.data.records
                        _this.currentPage = res.data.data.current
                        _this.total = res.data.data.total
                        _this.pageSize = res.data.data.size
                    })
            },
        },
        created() {
            this.type = this.$route.query.type
            this.info = this.$route.query.info
            console.log(this.type)
            if(sessionStorage.getItem("con")==null){
                sessionStorage.setItem("con",'7')
            }
            else{
                this.count = parseInt(sessionStorage.getItem("con"))+1
                sessionStorage.setItem("con",this.count)
            }
            if (this.type!=null){
                this.page_type(1)
                this.count=this.count+1
            }
            else if(this.info==1){
                this.page_info(1)
                this.count=this.count+1
            }
            else{
                this.page(1)
                this.count=this.count+1
            }
        },
        watch: {
            $route (to, from) {
                this.$router.go(0)
            }
        },

    }

</script>

<style scoped>
    .mpage{
        margin: 0 auto;
        text-align: center;
    }
    .count{
        text-align: right;
        margin: 0 4em;
    }
</style>