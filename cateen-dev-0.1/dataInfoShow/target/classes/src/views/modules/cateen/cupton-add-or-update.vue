<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="优惠卷的名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="优惠卷的名称"></el-input>
    </el-form-item>
    <el-form-item label="使用限制描述" prop="limitdesc">
      <el-input v-model="dataForm.limitdesc" placeholder="使用限制描述"></el-input>
    </el-form-item>
    <el-form-item label="使用的积分限制" prop="scorelimit">
      <el-input v-model="dataForm.scorelimit" placeholder="使用的积分限制"></el-input>
    </el-form-item>
    <el-form-item label="使用的级别限制" prop="levellimit">
      <el-input v-model="dataForm.levellimit" placeholder="使用的级别限制"></el-input>
    </el-form-item>
    <el-form-item label="使用的范围限制" prop="scopelimit">
      <el-input v-model="dataForm.scopelimit" placeholder="使用的范围限制"></el-input>
    </el-form-item>
    <el-form-item label="每个人使用的数量限制" prop="singlelimit">
      <el-input v-model="dataForm.singlelimit" placeholder="每个人使用的数量限制"></el-input>
    </el-form-item>
    <el-form-item label="每次使用的数量限制" prop="uselimit">
      <el-input v-model="dataForm.uselimit" placeholder="每次使用的数量限制"></el-input>
    </el-form-item>
    <el-form-item label="发布者" prop="publisherid">
      <el-input v-model="dataForm.publisherid" placeholder="发布者"></el-input>
    </el-form-item>
    <el-form-item label="活动发布者名字" prop="publishername">
      <el-input v-model="dataForm.publishername" placeholder="活动发布者名字"></el-input>
    </el-form-item>
    <el-form-item label="所属的活动" prop="activityid">
      <el-input v-model="dataForm.activityid" placeholder="所属的活动"></el-input>
    </el-form-item>
    <el-form-item label="优惠劵的类别如果是1则表示的是一个吧普通的优惠卷如果是2表示的是一个活动特定发放的优惠劵" prop="type">
      <el-input v-model="dataForm.type" placeholder="优惠劵的类别如果是1则表示的是一个吧普通的优惠卷如果是2表示的是一个活动特定发放的优惠劵"></el-input>
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
          name: '',
          limitdesc: '',
          scorelimit: '',
          levellimit: '',
          scopelimit: '',
          singlelimit: '',
          uselimit: '',
          publisherid: '',
          publishername: '',
          activityid: '',
          type: ''
        },
        dataRule: {
          name: [
            { required: true, message: '优惠卷的名称不能为空', trigger: 'blur' }
          ],
          limitdesc: [
            { required: true, message: '使用限制描述不能为空', trigger: 'blur' }
          ],
          scorelimit: [
            { required: true, message: '使用的积分限制不能为空', trigger: 'blur' }
          ],
          levellimit: [
            { required: true, message: '使用的级别限制不能为空', trigger: 'blur' }
          ],
          scopelimit: [
            { required: true, message: '使用的范围限制不能为空', trigger: 'blur' }
          ],
          singlelimit: [
            { required: true, message: '每个人使用的数量限制不能为空', trigger: 'blur' }
          ],
          uselimit: [
            { required: true, message: '每次使用的数量限制不能为空', trigger: 'blur' }
          ],
          publisherid: [
            { required: true, message: '发布者不能为空', trigger: 'blur' }
          ],
          publishername: [
            { required: true, message: '活动发布者名字不能为空', trigger: 'blur' }
          ],
          activityid: [
            { required: true, message: '所属的活动不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '优惠劵的类别如果是1则表示的是一个吧普通的优惠卷如果是2表示的是一个活动特定发放的优惠劵不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/cateen/cupton/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.cupton.name
                this.dataForm.limitdesc = data.cupton.limitdesc
                this.dataForm.scorelimit = data.cupton.scorelimit
                this.dataForm.levellimit = data.cupton.levellimit
                this.dataForm.scopelimit = data.cupton.scopelimit
                this.dataForm.singlelimit = data.cupton.singlelimit
                this.dataForm.uselimit = data.cupton.uselimit
                this.dataForm.publisherid = data.cupton.publisherid
                this.dataForm.publishername = data.cupton.publishername
                this.dataForm.activityid = data.cupton.activityid
                this.dataForm.type = data.cupton.type
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
              url: this.$http.adornUrl(`/cateen/cupton/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'limitdesc': this.dataForm.limitdesc,
                'scorelimit': this.dataForm.scorelimit,
                'levellimit': this.dataForm.levellimit,
                'scopelimit': this.dataForm.scopelimit,
                'singlelimit': this.dataForm.singlelimit,
                'uselimit': this.dataForm.uselimit,
                'publisherid': this.dataForm.publisherid,
                'publishername': this.dataForm.publishername,
                'activityid': this.dataForm.activityid,
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
