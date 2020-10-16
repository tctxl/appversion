<template>
  <div style="display: flex;justify-content: center;">
    <div style="margin-top: 100px;">
      <div>
        <h5>AppVersion Management</h5>
      </div>
      <div>
        <el-input
          placeholder="请输入用户名"
          prefix-icon="el-icon-user"
          v-model="userName">
        </el-input>
      </div>
      <div style="margin-top: 10px;">
        <el-input
          type="password"
          placeholder="请输入密码"
          prefix-icon="el-icon-lock"
          v-model="userPwd">
        </el-input>
      </div>
      <div style="margin-top: 10px;">
        <el-button @click="onLogin" style="width: 100%;" type="primary">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      userName:'',
      userPwd:''
    }
  },
  methods:{
    onLogin(){
      let that = this;
      that.$http.post('/api/dash/login',{userName:that.userName,userPwd:that.userPwd})
      .then(res=>{
        console.log(that.userName,that.userPwd,res.data.data);
        if(res.data.code === 0){
          that.$user.token = res.data.data;
          that.$router.push('/dash')
        }
      });
    }
  }
}
</script>
