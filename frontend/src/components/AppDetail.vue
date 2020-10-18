<template>
  <div v-if="app" style="display: flex;height: 100%;">
    <div
      style="max-width: 275px;min-width: 275px;background-color: #f5f7fa;border-top-left-radius: 20px;border-bottom-left-radius: 20px;">
      <div style="height: 100%;display: flex;flex-direction: column;justify-content: center;align-items: center;padding: 0 25px;">
        <el-upload
          :action="$api+'api/dash/app/icon'"
          list-type="picture"
          :show-file-list="false"
          :data="{token:$user.token}"
          :on-success="handleAppIconSuccess"
          :auto-upload="true">

          <el-image slot="default"  class="el-upload-list__item-thumbnail" style="width: 80px;height: 80px;border-radius: 50%;border: 3px solid #dedede;box-shadow: 0px 0px 20px 0px #bdbdbd;" fit="cover" :src="$api+app.icon">
            <div slot="placeholder" class="image-slot">
              加载中<span class="dot">...</span>
            </div>
          </el-image>
        </el-upload>
        <div style="margin-top: 10px;font-size: 18px;" @click="editAppName = true" v-if="!editAppName">{{ app.appName }}</div>
        <div style="margin-top: 10px;" v-else><el-input :autofocus="true" @blur="updateAppName" style="font-size: 18px;text-align: center;" v-model="app.appName"/></div>

        <div style="margin-top: 10px;font-size: 14px;color: #777777;"  @click="editPlatform = true" v-if="!editPlatform">{{ app.platform }}</div>
        <div style="margin-top: 10px;font-size: 14px;color: #777777;" v-else>
          <el-select @change="updatePlatform" v-model="app.platform" placeholder="请选择">
            <el-option
              label="Android"
              value="Android">
            </el-option>
            <el-option
              label="iOS"
              value="iOS">
            </el-option>
          </el-select></div>
        <div style="margin-top: 10px;font-size: 14px;color: #777777;"  @click="editAppDesc = true" v-if="!editAppDesc">{{ app.appDesc }}</div>
        <div style="margin-top: 10px;width: 100%;" v-else><el-input resize="none" :autofocus="true" type="textarea" @blur="updateAppDesc" style="font-size: 12px;text-align: center;" v-model="app.appDesc"/></div>

        <el-popover
          placement="bottom"
          trigger="hover">
          <div style="font-size: 12px;color: #aaa;text-align: center;">
            上传一个新版本
          </div>
          <el-button @click="loadVersions" circle icon="el-icon-plus" type="primary" slot="reference" style="margin-top: 50px;"></el-button>
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
            label="升级类型"
            width="120">
            <template slot-scope="scope">
              <div v-if="scope.row.type === 1">非强制升级</div>
              <div v-if="scope.row.type === 2">强制升级</div>
              <div v-if="scope.row.type === 3">非强制升级-跳转浏览器</div>
              <div v-if="scope.row.type === 4">强制升级-跳转浏览器</div>
              <div v-if="scope.row.type === 5">非强制升级-跳转市场</div>
              <div v-if="scope.row.type === 6">强制升级-跳转市场</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="versionName"
            label="版本号"
            width="120">
          </el-table-column>
          <el-table-column
            prop="nextId"
            label="更新到">
          </el-table-column>
          <el-table-column
            label="版本链接">
            <template slot-scope="scope">
              <a style="color: #0d7f56;border: none;text-decoration: none;" target="_blank" :href="scope.row.url">下载</a>
            </template>
          </el-table-column>
          <el-table-column
            label="对外分发">
            <template slot-scope="scope">
<!--              <el-button type="primary">对外分发</el-button>-->
              <a style="cursor: pointer;color: #0d7f56;border: none;text-decoration: none;" v-if="scope.row.appOpen !== 1" target="_blank" @click="shareApp(scope.row)">设置</a>
              <div v-else><a style="text-decoration: none;color: #777777;" :href="$apk+'/s/'+$route.params.id">已分发</a></div>
            </template>
          </el-table-column>
          <el-table-column
            label="删除">
            <template slot-scope="scope">
<!--              <el-button type="primary">对外分发</el-button>-->
              <a style="cursor: pointer;color: #0d7f56;border: none;text-decoration: none;" target="_blank" @click="delChannel(scope.row)">删除</a>
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
    <el-dialog title="新建一个分发版本" :visible.sync="dialogCreateVersionVisible">

      <el-dialog
        width="30%"
        title="新建版本号"
        :visible.sync="dialogCreateVersionCodeVisible"
        append-to-body>
        <div >
          <h4>版本号设置</h4>
          <div><el-input v-model="newVersionName"  placeholder="版本号名称，如：1.0.1,1.0.2..."/></div>
        </div>

        <div>
          <el-button style="width: 100%;margin-top: 30px;" type="primary" @click="createVersion">完成</el-button>
        </div>
      </el-dialog>
      <div v-if="app.platform === 'Android'">
        <el-upload
          :action="$api + 'api/dash/app/version'"
          list-type="picture-card"
          :show-file-list="false"
          :data="{appId:app.id,token:$user.token}"
          accept=".apk"
          :on-success="handleAppVersionSuccess"
          :auto-upload="true">
          <i slot="default" v-if="newVersion.url.length === 0" class="el-icon-plus"></i>
          <i class="el-icon-check" slot="default" v-else></i>
        </el-upload>
        <a style="display: none;" ref="newVersionUrl" v-if="newVersion.url.length > 0" :href="$apk+newVersion.url"></a>
      </div>
      <div v-else>
        <h4>AppStore设置</h4>
        <div><el-input v-model="newVersion.url"  placeholder="应用在AppStore内的地址"/></div>
      </div>
      <div v-show="app.platform === 'Android'">
        <h4>渠道设置</h4>
        <div><el-input v-model="newVersion.channel"  placeholder="渠道名称，用于不同渠道版本升级"/></div>
      </div>
      <div>
        <h4>升级提醒设置</h4>
        <div><el-input v-model="newVersion.title"  placeholder="标题"/></div>
        <div style="margin-top: 10px;"><el-input v-model="newVersion.content"  type="textarea" resize="none" placeholder="内容"/></div>
      </div>
      <div>
        <h4>升级方式</h4>
        <template v-if="app.platform === 'Android'">

          <el-select v-model="newVersion.type" placeholder="请选择">
            <el-option label="非强制升级" :value="1"></el-option>
            <el-option label="强制升级" :value="2"></el-option>
            <el-option label="非强制升级-跳转浏览器" :value="3"></el-option>
            <el-option label="强制升级-跳转浏览器" :value="4"></el-option>
            <el-option label="非强制升级-跳转市场" :value="5"></el-option>
            <el-option label="强制升级-跳转市场" :value="6"></el-option>
          </el-select>
        </template>
        <template v-else>
          <el-select v-model="newVersion.type" placeholder="请选择">
            <el-option label="非强制升级" :value="1"></el-option>
            <el-option label="强制升级" :value="2"></el-option>
          </el-select>
        </template>
      </div>
      <div>
        <div><h4>版本号设置 <span style="font-weight: normal;font-size: 12px;color: #0d7f56;text-underline: #0d7f56;cursor: pointer;" @click="dialogCreateVersionCodeVisible=true">(新建版本号)</span></h4></div>
        <div>
          <el-select v-model="newVersion.versionId" placeholder="如：1.0.0.20201018">

            <el-option
              v-for="version in newVersion.versions"
              :key="version.id"
              :label="version.versionName"
              :value="version.id">
              <span style="float: left">{{ version.versionName }}</span>
              <el-button size="mini" style="float: right; color: #8492a6; font-size: 13px" @click.stop="delVersion(version)">删除</el-button>
            </el-option>
          </el-select>
        </div>
      </div>
      <div>
        <div>
          <el-button style="width: 100%;margin-top: 30px;" type="primary" @click="createChannel">完成</el-button>
        </div>
      </div>
    </el-dialog>
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
      editAppName:false,
      editPlatform:false,
      editAppDesc:false,
      dialogCreateVersionVisible:false,
      dialogCreateVersionCodeVisible:false,
      newVersion:{
        channel:'default',
        title:'',
        url:'',
        content:'',
        type:1,
        versionId:'',
        versions:[]
      },
      newVersionName:''
    };
  },
  mounted() {
    this.loadAppDetail();
    console.log(this.$refs.channels)
  },
  methods: {
    shareApp(channel){
      let that = this;
      that.$http.post('/api/dash/app/share', {appId: this.$route.params.id,id:channel.id}).then((res) => {
        if (res.data.code === 0) {
          for (let i = 0; i < that.app.channels.length; i++) {
            let c = that.app.channels[i];
            if(c.id !== channel.id){
              c.appOpen = 0;
            }else{
              c.appOpen = 1;
            }
          }
        }
      })
    },
    delChannel(channel){
      let that = this;
      that.$http.post('/api/dash/app/channel/delete', {appId: this.$route.params.id,id:channel.id}).then((res) => {
        if (res.data.code === 0) {
          for (let i = 0; i < that.app.channels.length; i++) {
            let c = that.app.channels[i];
            if(c.id === channel.id){
              that.app.channels.splice(i,1);
              break;
            }
          }
        }
      })
    },
    delVersion(version){
      let that = this;
      that.$http.post('/api/dash/app/version/delete', {appId: this.$route.params.id,id:version.id}).then((res) => {
        if (res.data.code === 0) {
          for (let i = 0; i < that.newVersion.versions.length; i++) {
            let c = that.newVersion.versions[i];
            if(c.id === version.id){
              if(that.newVersion.versionId === version.id){
                that.newVersion.versionId = '';
              }
              that.newVersion.versions.splice(i,1);
              break;
            }
          }
        }
      })
    },
    createVersion(){
      let that = this;
      that.$http.post('/api/dash/app/version/create', {appId: this.$route.params.id,versionName:that.newVersionName}).then((res) => {
        if (res.data.code === 0) {
          that.newVersionName = '';
          that.newVersion.versions.unshift(res.data.data);
        }
        that.dialogCreateVersionCodeVisible = false;
      })
    },
    loadVersions(){
      let that = this;
      that.dialogCreateVersionVisible = true;
      that.$http.post('/api/dash/app/version/find', {appId: this.$route.params.id}).then((res) => {
        if (res.data.code === 0) {
          that.$nextTick(function (){
            that.newVersion.versions = res.data.data;
            if(that.newVersion.versions.length > 0){
              that.newVersion.versionId = that.newVersion.versions[0].id;
            }
          });
        }
      })
    },
    handleAppVersionSuccess(res, file){
      let that = this;
      if(res.code===305){
        this.$router.push('/');
        return;
      }
      if(res.code===0){
        that.newVersion.url = res.data;
        that.$nextTick(() => {
          that.newVersion.url = that.$refs.newVersionUrl.href;
        })
      }
    },
    handleAppIconSuccess(res, file){
      let that = this;
      if(res.code===305){
        this.$router.push('/');
        return;
      }
      if(res.code===0){
        that.$http.post('/api/dash/app/update', {id: this.$route.params.id, icon: res.data}).then((res) => {
          if (res.data.code === 0) {
            that.$nextTick(function (){
              that.app.icon = res.data.data.icon;
            });
          }
        })
      }
    },
    createChannel(){
      let that = this;
      let req = {
        appId:this.$route.params.id,
        channel:that.newVersion.channel,
        title:that.newVersion.title,
        url:that.newVersion.url,
        content:that.newVersion.content,
        type:that.newVersion.type,
        versionId:that.newVersion.versionId,
      }
      that.$http.post('/api/dash/app/channel/create', req).then((res) => {
        if (res.data.code === 0) {
          that.newVersion = {
            channel:'default',
            title:'',
            url:'',
            content:'',
            type:1,
            version: {
              versionName:'',
              versionCode:0
            },
            versions:[]
          };
          res.data.data.appOpen = 0;
          that.app.channels.unshift(res.data.data);
          that.dialogCreateVersionVisible = false;
        }
        that.editAppName = false;
      })
    },
    updateAppName(){
      let that = this;
      that.$http.post('/api/dash/app/update', {id: this.$route.params.id, appName: this.app.appName}).then((res) => {
        if (res.data.code === 0) {
          that.app.appName = res.data.data.appName;
        }
        that.editAppName = false;
      })
    },
    updateAppDesc(){
      let that = this;
      that.$http.post('/api/dash/app/update', {id: this.$route.params.id, appDesc: this.app.appDesc}).then((res) => {
        if (res.data.code === 0) {
          that.app.appDesc = res.data.data.appDesc;
        }
        that.editAppDesc = false;
      })
    },
    updatePlatform(){
      let that = this;
      that.$http.post('/api/dash/app/update', {id: this.$route.params.id, platform: this.app.platform}).then((res) => {
        if (res.data.code === 0) {
          that.app.platform = res.data.data.platform;
        }
        that.editPlatform = false;
      })
    },
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
