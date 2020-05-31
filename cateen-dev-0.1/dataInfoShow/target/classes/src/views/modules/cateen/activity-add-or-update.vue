<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="活动的标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="活动的标题"></el-input>
    </el-form-item>
    <el-form-item label="活动的宣传图" prop="imgsrc">
      <el-input v-model="dataForm.imgsrc" placeholder="活动的宣传图"></el-input>
    </el-form-item>
    <el-form-item label="活动的状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="活动的状态"></el-input>
    </el-form-item>
    <el-form-item label="活动开始的时间" prop="starttime">
      <el-input v-model="dataForm.starttime" placeholder="活动开始的时间"></el-input>
    </el-form-item>
    <el-form-item label="活动的结束时间" prop="endtime">
      <el-input v-model="dataForm.endtime" placeholder="活动的结束时间"></el-input>
    </el-form-item>
    <el-form-item label="活动的发起者名字" prop="initiator">
      <el-input v-model="dataForm.initiator" placeholder="活动的发起者名字"></el-input>
    </el-form-item>
    <el-form-item label="活动的发起者id" prop="initiatorid">
      <el-input v-model="dataForm.initiatorid" placeholder="活动的发起者id"></el-input>
    </el-form-item>
    <el-form-item label="简述" prop="simpledesc">
      <el-input v-model="dataForm.simpledesc" placeholder="简述"></el-input>
    </el-form-item>
    <el-form-item label="详细描述" prop="descr">
      <el-input v-model="dataForm.descr" placeholder="详细描述"></el-input>
    </el-form-item>
    <el-form-item label="分数的限制" prop="scorelimit">
      <el-input v-model="dataForm.scorelimit" placeholder="分数的限制"></el-input>
    </el-form-item>
    <el-form-item label="限制描述" prop="limitdesc">
      <el-input v-model="dataForm.limitdesc" placeholder="限制描述"></el-input>
    </el-form-item>
    <el-form-item label="等级限制" prop="levellimit">
      <el-input v-model="dataForm.levellimit" placeholder="等级限制"></el-input>
    </el-form-item>
    <el-form-item label="活动状态码" prop="statuscode">
      <el-input v-model="dataForm.statuscode" placeholder="活动状态码"></el-input>
    </el-form-item>
    <el-form-item label="活动的类型" prop="type">
      <el-input v-model="dataForm.type" placeholder="活动的类型"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          title: '',
          imgsrc: '',
          status: '',
          starttime: '',
          endtime: '',
          initiator: '',
          initiatorid: '',
          simpledesc: '',
          descr: '',
          scorelimit: '',
          limitdesc: '',
          levellimit: '',
          statuscode: '',
          type: ''
        },
        dataRule: {
          title: [
            { required: true, message: '活动的标题不能为空', trigger: 'blur' }
          ],
          imgsrc: [
            { required: true, message: '活动的宣传图不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '活动的状态不能为空', trigger: 'blur' }
          ],
          starttime: [
            { required: true, message: '活动开始的时间不能为空', trigger: 'blur' }
          ],
          endtime: [
            { required: true, message: '活动的结束时间不能为空', trigger: 'blur' }
          ],
          initiator: [
            { required: true, message: '活动的发起者名字不能为空', trigger: 'blur' }
          ],
          initiatorid: [
            { required: true, message: '活动的发起者id不能为空', trigger: 'blur' }
          ],
          simpledesc: [
            { required: true, message: '简述不能为空', trigger: 'blur' }
          ],
          descr: [
            { required: true, message: '详细描述不能为空', trigger: 'blur' }
          ],
          scorelimit: [
            { required: true, message: '分数的限制不能为空', trigger: 'blur' }
          ],
          limitdesc: [
            { required: true, message: '限制描述不能为空', trigger: 'blur' }
          ],
          levellimit: [
            { required: true, message: '等级限制不能为空', trigger: 'blur' }
          ],
          statuscode: [
            { required: true, message: '活动状态码不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '活动的类型不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/cateen/activity/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.activity.title
                this.dataForm.imgsrc = data.activity.imgsrc
                this.dataForm.status = data.activity.status
                this.dataForm.starttime = data.activity.starttime
                this.dataForm.endtime = data.activity.endtime
                this.dataForm.initiator = data.activity.initiator
                this.dataForm.initiatorid = data.activity.initiatorid
                this.dataForm.simpledesc = data.activity.simpledesc
                this.dataForm.descr = data.activity.descr
                this.dataForm.scorelimit = data.activity.scorelimit
                this.dataForm.limitdesc = data.activity.limitdesc
                this.dataForm.levellimit = data.activity.levellimit
                this.dataForm.statuscode = data.activity.statuscode
                this.dataForm.type = data.activity.type
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/cateen/activity/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'title': this.dataForm.title,
                'imgsrc': this.dataForm.imgsrc,
                'status': this.dataForm.status,
                'starttime': this.dataForm.starttime,
                'endtime': this.dataForm.endtime,
                'initiator': this.dataForm.initiator,
                'initiatorid': this.dataForm.initiatorid,
                'simpledesc': this.dataForm.simpledesc,
                'descr': this.dataForm.descr,
                'scorelimit': this.dataForm.scorelimit,
                'limitdesc': this.dataForm.limitdesc,
                'levellimit': this.dataForm.levellimit,
                'statuscode': this.dataForm.statuscode,
                'type': this.dataForm.type
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
