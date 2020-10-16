<template>
<div>
  <div style="padding: 25px;">
    <!--菜单-->
    <div
      class="menu-btn">
      <i class="el-icon-circle-plus-outline"></i>
      <div style="margin-left: 5px;">新建项目</div>
    </div>
  </div>
  <div style="position: absolute;display: flex;height: calc(100% - 134px);width: 100%;">
    <div v-if="apps.length === 0" style="font-size: 24px;color: #777777;text-align: center;width: 100%;">什么都没有...
    </div>

    <div style="padding: 0 25px 25px;width: 100%;">
      <el-row :gutter="20" justify="space-between">
        <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="2" v-for="app in apps">
          <div @click="$router.push('/dash/app/'+app.id)" class="app-card">
            <div style="width: 50px;height: 50px;background-color: #0d7f56;border-radius: 50%;display: inline-block;border: 3px dashed #000"></div>
            <div style="margin-top: 10px;">{{ app.appName }}</div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
<!--  <div-->
<!--    style="position: absolute;line-height: 40px;width: 100%;bottom: 0;border-bottom-left-radius: 20px;border-bottom-right-radius: 20px;cursor:pointer;background: #3a8ee6;color: white;text-align: center;font-size: 14px;">-->
<!--    注销登录-->
<!--  </div>-->
</div>
</template>

<script>
export default {
  name: "AppList",
  data() {
    return {
      apps: []
    }
  },
  mounted() {
    this.loadAppList();
  },
  methods: {
    loadAppList() {
      let that = this;
      that.$http.post('/api/dash/app/list').then((res) => {
        if (res.data.code === 0) {
          that.apps = res.data.data;
        }
      })
    }
  }
}
</script>

<style scoped>

.menu-btn {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 40px;
  font-size: 14px;
  color: #666;
  box-shadow: rgba(0, 0, 0, .12) 0 0 10px;
  border: 2px dashed #d7d7d7;
  cursor: pointer;
  border-radius: 10px;
  transition: all 0.2s ease-in-out;
}

.menu-btn:hover {
  opacity: 0.7;
  background-color: #0d7f56;
  border: 2px dashed #549a81;
  color: #fff;
  transition: all 0.2s ease-in-out;
}

.app-card {
  cursor: pointer;
  border: 2px solid transparent;
  box-shadow: rgba(0, 0, 0, .12) 0 0 10px;
  text-align: center;
  height: 100px;
  margin-top: 20px;
  font-size: 14px;
  padding-top: 10px;
  border-radius: 10px;
}

.app-card:hover {
  opacity: 0.8;
  transition: all 0.2s ease-in-out;
  background-color: #f3f3f3;
  border: 2px solid white;
}
</style>
