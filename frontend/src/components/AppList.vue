<template>
<div>
  <div style="padding: 25px;">
    <!--菜单-->
    <div
      class="menu-btn">
      <i class="el-icon-circle-plus-outline"></i>
      <div style="margin-left: 5px;" @click="createApp">新建应用</div>
    </div>
  </div>
  <div style="position: absolute;display: flex;height: calc(100% - 154px);width: 100%;">
    <div v-if="apps.length === 0" style="font-size: 24px;color: #777777;text-align: center;width: 100%;">什么都没有...
    </div>

    <div style="padding: 0 25px 25px 25px;width: 100%;overflow: auto">
      <el-row :gutter="20" justify="space-between">
        <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="2" :key="app.id" v-for="app in apps">
          <div @click="$router.push('/dash/app/'+app.id)" class="app-card">
            <el-image slot="default" fit="cover" class="el-upload-list__item-thumbnail" style="width: 50px;height: 50px;border-radius: 50%;border: 3px solid #dedede;box-shadow: 0px 0px 20px 0px #bdbdbd;" :src="$api+app.icon">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
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
    createApp(){
      let that = this;
      that.$http.post('/api/dash/app/create').then((res) => {
        if (res.data.code === 0) {
          that.$router.push('/dash/app/'+res.data.data.id)
        }
      })
    },
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
