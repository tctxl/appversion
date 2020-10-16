<template>
  <div style="display: flex;height: 100%;">
    <div
      style="max-width: 275px;min-width: 275px;background-color: #f5f7fa;border-top-left-radius: 20px;border-bottom-left-radius: 20px;">
      <div style="height: 100%;display: flex;flex-direction: column;justify-content: center;align-items: center;padding: 0 25px;">
        <div
          style="width: 80px;height: 80px;background-color: #0d7f56;border-radius: 50%;display: inline-block;border: 3px dashed #000"></div>
        <div style="margin-top: 10px;font-size: 18px;">{{ app.appName }}</div>
        <div style="margin-top: 10px;font-size: 14px;color: #777777;">{{ app.platform }}</div>
        <div style="margin-top: 10px;font-size: 14px;color: #777777;">{{ app.desc }}</div>
        <el-popover
          placement="bottom"
          trigger="hover">
          <div style="font-size: 12px;color: #aaa;text-align: center;">
            上传一个新版本
          </div>
          <el-button @click="()=>{window.alert(1)}" circle icon="el-icon-plus" type="primary" slot="reference" style="margin-top: 50px;"></el-button>
        </el-popover>
      </div>
    </div>
    <div style="width: 99%;position: relative;">
      <div class="btn-close" @click="$router.back();">
        <i class="el-icon-close"
           style="font-weight: bold;margin-right: 10px;margin-top: 10px;font-size: 22px;color: #fff;"></i>
      </div>
      <div style="padding: 25px;width: 100%;height:calc(100% - 160px);position: relative;" ref="channels">
        <el-table
          style="position: absolute;width: calc(100% - 100px);" height="100%"
          :stripe="true"
          :data="app.channels">
          <el-table-column

            prop="id"
            label="ID"
            width="80">
          </el-table-column>
          <el-table-column
            prop="channel"
            label="渠道"
            width="150">
          </el-table-column>
          <el-table-column
            prop="title"
            label="升级提醒标题"
            width="120">
          </el-table-column>
          <el-table-column
            prop="content"
            label="升级提醒内容"
            width="120">
          </el-table-column>
          <el-table-column
            prop="type"
            label="升级类型"
            width="120">
          </el-table-column>
          <el-table-column
            prop="versionName"
            label="版本号"
            width="120">
          </el-table-column>
          <el-table-column
            width="180"
            prop="nextId"
            label="更新到指定版本（ID）">
          </el-table-column>
          <el-table-column
            label="版本链接">
            <template slot-scope="scope">
              <a style="color: #0d7f56;border: none;text-decoration: none;" target="_blank" :href="scope.row.url">下载</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="padding: 25px;display: flex;justify-content: space-between;margin-top: 20px;align-items: center;">
        <el-button style="width: 50px;" type="primary" @click="onPrev" :disabled="!(pageNo > 1)"><i class="el-icon-arrow-left"></i></el-button>
        <div style="font-size: 12px;color: #777777;">
          {{pageNo}}/{{pageCount}}
        </div>
        <el-button style="width: 50px;" type="primary" @click="onNext" :disabled="!(pageNo < pageCount)"><i class="el-icon-arrow-right"></i>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppDetail",
  data() {
    return {
      app: null,
      pageNo: 1,
      pageCount: 1,
    };
  },
  mounted() {
    this.loadAppDetail();
    console.log(this.$refs.channels)
  },
  methods: {
    onPrev() {
      this.pageNo -= 1;
      this.loadAppDetail();
    },
    onNext() {
      this.pageNo += 1;
      this.loadAppDetail();
    },
    loadAppDetail() {
      let that = this;
      that.$http.post('/api/dash/app/detail', {id: this.$route.params.id, pageNo: this.pageNo}).then((res) => {
        if (res.data.code === 0) {
          that.app = res.data.data.data;
          that.pageNo = res.data.data.pageNo;
          that.pageCount = res.data.data.pageCount;
        }
      })
    }
  }
}
</script>

<style scoped>
.btn-close {
  display: flex;
  justify-content: flex-end;
  position: absolute;
  width: 50px;
  height: 50px;
  border-bottom-left-radius: 100%;
  border-top-right-radius: 20px;
  right: 0;
  z-index: 1;
  background-color: #0e7f56;
  cursor: pointer;
}
.btn-close:hover{
  opacity: 0.7;
  transition: all 0.2s ease-in-out;
}
</style>
