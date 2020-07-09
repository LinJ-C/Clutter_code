<template>
 <div>
     <div class="qr_code" id="qrCode" ref="qrCodeDiv"></div>
 </div>
</template>

<script>
  import QRCode from 'qrcodejs2';
  export default {
    name: "About",
    data() {
      return {
          text:''
      }
    },
    mounted: function () {
      this.$nextTick(function () {
          const _this =this;
          this.axios.get("http://localhost:8080/weixin/pay/create/native?outtradeno=500001012&totalfee=1").then((res)=>{
              // console.log(res)
              var data = res.data.map
              // console.log(data.code_url)
              this.text = data.code_url
              console.log(this.text)
              this.bindQRCode();
          })
      })
    },
    methods: {
      bindQRCode: function () {
        new QRCode(this.$refs.qrCodeDiv, {
          text: this.text,
          width: 200,
          height: 200,
          colorDark: "#333333", //二维码颜色
          colorLight: "#ffffff", //二维码背景色
          correctLevel: QRCode.CorrectLevel.L//容错率，L/M/H
        })
      }
    },
  }
</script>
<style scoped>
  .qr_code{
      position: absolute;
      text-align: center;
      margin: 0 auto;
      top: 20%;
      left: 43%;
      width: 200px;
      height: 200px;
  }

</style>